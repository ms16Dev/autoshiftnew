<script setup lang="ts">

import IconButtonLink from './IconButtonLink.vue';
import {useAuthStore} from "../stores/auth.ts";

const staticData = useStaticDataStore();
const store =  useAuthStore();

defineProps({
  position: {
    type: String,
    default: 'bottom', // Can be 'top', 'bottom', 'left', 'right'
  },
});

const emit = defineEmits(['close']);


import {computed, ref, watch} from "vue";
import {useStaticDataStore} from "../stores/staticDataStore.ts";
import i18n from "../plugins/i18n.ts";

const currentLang = ref(localStorage.getItem("lang") || "en");

const toggleLanguage = (lang: string) => {
  if (currentLang.value === lang) {
    return; // Do nothing if the selected language is already active
  }

  currentLang.value = lang; // Set the selected language
  i18n.global.locale.value = lang as 'en' | 'ar';

  // Update `html` direction
  document.documentElement.lang = currentLang.value;
  document.documentElement.dir = currentLang.value === "ar" ? "rtl" : "ltr";


  // Save preference
  localStorage.setItem("lang", currentLang.value);

  staticData.toggleLanguage(lang)

};

// Apply saved language on load
watch(currentLang, (newLang) => {
  document.documentElement.lang = newLang;
  document.documentElement.dir = newLang === "ar" ? "rtl" : "ltr";
}, { immediate: true });

const userRole = computed(() => {
  if (store?.userInfo?.roles?.includes("ROLE_ADMIN")) {
    return "Admin";
  }
  return "User"; // Default to "User" if "ROLE_ADMIN" is not found
});


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
      <li v-if="store.isAuthenticated" class="p-2">
        <IconButtonLink
            text="Profile"
            icon="fas fa-user"
            :target="`/users/${store.userInfo?.name ?? ''}`"
            class="text-pink-500" @click="$emit('close')"/>
      </li>
      <li v-if="store.isAuthenticated" class="p-2">
        <IconButtonLink
            text="Admin"
            icon="fas fa-cogs"
            :target="`/admin`"
            class="text-pink-500" @click="$emit('close')"/>
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
      <h1 class="text-white rounded-full bg-purple-600 w-fit px-2">{{userRole}}</h1>
    </div>

    <ul class="divide-y divide-gray-200">
      <li v-if="store.isAuthenticated" class="p-2">
        <IconButtonLink
            text="Profile"
            icon="fas fa-user"
            :target="`/users/${store.userInfo?.name ?? ''}`"
            class="text-pink-500" @click="$emit('close')"/>
      </li>
      <li v-if="store.isAuthenticated" class="p-2">
        <IconButtonLink
            text="Admin"
            icon="fas fa-cogs"
            :target="`/admin`"
            class="text-pink-500" @click="$emit('close')"/>
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
