<script setup lang="ts">

import IconButtonLink, { IconButtonLinkProps } from './IconButtonLink.vue';
import {useAuthStore} from "../stores/auth.ts";

const store =  useAuthStore();

defineProps({
  items: {
    type: Array as () => IconButtonLinkProps[],
    default: () => [
      { text: 'Profile', icon: 'fas fa-user', target: '/dealers/1' },
      { text: 'Settings', icon: 'fas fa-cogs', target: '/settings' },
    ],
  },
  position: {
    type: String,
    default: 'bottom', // Can be 'top', 'bottom', 'left', 'right'
  },
});

const emit = defineEmits(['close']);



import { ref, watch } from "vue";

const currentLang = ref(localStorage.getItem("lang") || "en");

const toggleLanguage = (lang: string) => {
  if (currentLang.value === lang) {
    return; // Do nothing if the selected language is already active
  }

  currentLang.value = lang; // Set the selected language

  // Update `html` direction
  document.documentElement.lang = currentLang.value;
  document.documentElement.dir = currentLang.value === "ar" ? "rtl" : "ltr";

  // Save preference
  localStorage.setItem("lang", currentLang.value);
};

// Apply saved language on load
watch(currentLang, (newLang) => {
  document.documentElement.lang = newLang;
  document.documentElement.dir = newLang === "ar" ? "rtl" : "ltr";
}, { immediate: true });


const logout = () => {
  store.logout();
  emit('close');
}

</script>

<template>
  <div
      class="absolute rtl:-translate-x-3 ltr:translate-x-3  bg-gradient-to-r from-[#ff80b5] to-[#9089fc]  rounded-lg shadow-lg ring-1 ring-gray-300 z-50 bottom-full  end-0 md:block xl:hidden "
  >

    <div class="flex-col p-2">
      <h1 class="text-white text-center">{{store?.userInfo?.name}}</h1>
    </div>

    <ul class="divide-y divide-gray-200">
      <li v-if="store.isAuthenticated" v-for="(item, index) in items" :key="index" class="p-2">
        <IconButtonLink  v-bind="item" class="text-pink-500" @click="$emit('close')"/>
      </li>
      <li v-if="store.isAuthenticated">
        <IconButtonLink  class="text-pink-500" @click="logout" target="#" text="Logout" icon="fas fa-sign-out"/>
      </li>
      <li v-if="!store.isAuthenticated">
        <IconButtonLink  class="text-pink-500" @click="store.logout()" target="/auth/sign-in" text="Login" icon="fas fa-sign-in"/>
      </li>
      <li>
        <div class="flex flex-row justify-between p-2">
          <button :disabled="currentLang === 'en'" @click="() => { toggleLanguage('en'); $emit('close'); }" class="rounded-full bg-pink-400 hover:bg-pink-700 text-white w-full m-1 disabled:bg-gray-300 disabled:cursor-not-allowed p-1">
            En
          </button>
          <button :disabled="currentLang === 'ar'" @click="() => { toggleLanguage('ar'); $emit('close'); }" class="rounded-full bg-pink-400 hover:bg-pink-700 text-white w-full m-1 disabled:bg-gray-300 disabled:cursor-not-allowed p-1">
            ع
          </button>
        </div>

      </li>
    </ul>

  </div>
  <div
      class="absolute  bg-gradient-to-r from-[#ff80b5] to-[#9089fc]  rounded-lg shadow-lg ring-1 ring-gray-300 z-50 top-full md:hidden xl:block"
  >
    <div class="flex-col p-2">
      <h1 class="text-white text-lg">{{store?.userInfo?.name}}</h1>
      <h1 class="text-white text-lg">{{store?.userInfo?.roles}}</h1>
    </div>

    <ul class="divide-y divide-gray-200">
      <li v-if="store.isAuthenticated" v-for="(item, index) in items" :key="index" class="p-2">
        <IconButtonLink  v-bind="item" class="text-pink-500" @click="$emit('close')"/>
      </li>
      <li v-if="store.isAuthenticated">
        <IconButtonLink  class="text-pink-500" @click="logout" target="#" text="Logout" icon="fas fa-sign-out"/>
      </li>
      <li v-if="!store.isAuthenticated">
        <IconButtonLink  class="text-pink-500" @click="store.logout()" target="/auth/sign-in" text="Login" icon="fas fa-sign-in"/>
      </li>
      <li>
        <div class="flex flex-row justify-between p-2">
          <button :disabled="currentLang === 'en'" @click="() => { toggleLanguage('en'); $emit('close'); }" class="rounded-full bg-pink-400 hover:bg-pink-700 text-white w-full m-1 disabled:bg-gray-300 disabled:cursor-not-allowed p-1">
            En
          </button>
          <button :disabled="currentLang === 'ar'" @click="() => { toggleLanguage('ar'); $emit('close'); }" class="rounded-full bg-pink-400 hover:bg-pink-700 text-white w-full m-1 disabled:bg-gray-300 disabled:cursor-not-allowed p-1">
            ع
          </button>
        </div>

      </li>
    </ul>

  </div>
</template>

<style scoped>
</style>
