<script setup lang="ts">

import IconButtonLink, { IconButtonLinkProps } from './IconButtonLink.vue';

defineProps({
  items: {
    type: Array as () => IconButtonLinkProps[],
    default: () => [
      { text: 'Profile', icon: 'fas fa-user', target: '/add-car-new' },
      { text: 'Settings', icon: 'fas fa-cogs', target: '/add-part' },
      { text: 'Logout', icon: 'fas fa-sign-out', target: '/add-service' },
    ],
  },
  position: {
    type: String,
    default: 'bottom', // Can be 'top', 'bottom', 'left', 'right'
  },
});

defineEmits(['close']);



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


</script>

<template>
  <div
      class="absolute  bg-gradient-to-r from-[#ff80b5] to-[#9089fc]  rounded-lg shadow-lg ring-1 ring-gray-300 z-50 bottom-full  end-0 md:block xl:hidden "
  >
    <ul class="divide-y divide-gray-200">
      <!-- Render each IconButtonLink as a menu item -->
      <li v-for="(item, index) in items" :key="index" class="p-2">
        <IconButtonLink v-bind="item" class="text-pink-500" @click="$emit('close')"/>
      </li>
      <li>
        <div class="flex flex-row justify-between">
          <button :disabled="currentLang === 'en'" @click="() => { toggleLanguage('en'); $emit('close'); }" class="rounded-full bg-pink-400 hover:bg-pink-700 text-white w-full m-1 disabled:bg-gray-300 disabled:cursor-not-allowed">
            En
          </button>
          <button :disabled="currentLang === 'ar'" @click="() => { toggleLanguage('ar'); $emit('close'); }" class="rounded-full bg-pink-400 hover:bg-pink-700 text-white w-full m-1 disabled:bg-gray-300 disabled:cursor-not-allowed">
            ع
          </button>
        </div>

      </li>
    </ul>

  </div>
  <div
      class="absolute  bg-gradient-to-r from-[#ff80b5] to-[#9089fc]  rounded-lg shadow-lg ring-1 ring-gray-300 z-50 top-full md:hidden xl:block"
  >
    <ul class="divide-y divide-gray-200">
      <!-- Render each IconButtonLink as a menu item -->
      <li v-for="(item, index) in items" :key="index" class="p-2">
        <IconButtonLink v-bind="item" class="text-pink-500" @click="$emit('close')"/>
      </li>
      <li>
        <div class="flex flex-row justify-between">
          <button :disabled="currentLang === 'en'" @click="() => { toggleLanguage('en'); $emit('close'); }" class="rounded-full bg-pink-400 hover:bg-pink-700 text-white w-full m-1 disabled:bg-gray-300 disabled:cursor-not-allowed">
            En
          </button>
          <button :disabled="currentLang === 'ar'" @click="() => { toggleLanguage('ar'); $emit('close'); }" class="rounded-full bg-pink-400 hover:bg-pink-700 text-white w-full m-1 disabled:bg-gray-300 disabled:cursor-not-allowed">
            ع
          </button>
        </div>

      </li>
    </ul>

  </div>
</template>

<style scoped>
</style>
