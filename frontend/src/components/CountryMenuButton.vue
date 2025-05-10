<script setup lang="ts">
import { ref } from 'vue';
import CountryMenu from "./CountryMenu.vue";
import {useStaticDataStore} from "../stores/staticDataStore.ts"; // Import the pop-up menu component

const staticData = useStaticDataStore();

const showMenu = ref(false);


// Function to toggle the visibility of the menu
const toggleMenu = (event: MouseEvent) => {
  event.stopPropagation() // Prevent event from bubbling to document

  showMenu.value = !showMenu.value;
};

console.log(staticData.getCurrentCountry())
</script>

<template>
  <div class="absolute md:w-full lg:w-fit ">
    <!-- Action button -->
    <button
        @click="toggleMenu"
        class="flex md:w-full lg:w-fit items-center text-xs space-x-2 px-3 py-2 md:bg-pink-400 lg:ltr:bg-purple-400 lg:rtl:bg-pink-400 text-white hover:transform
               transition-transform duration-900 ease-in-out ltr:hover:bg-purple-600 rtl:hover:bg-pink-600 hover:scale-110 rounded-full shadow-lg"
    >
      {{staticData.getLocalizedName(staticData.getCurrentCountry())}}
      <i class="fas fa-chevron-down text-xs ms-2"></i>
    </button>

    <!-- Popup menu -->
    <country-menu
        v-if="showMenu"
        @close="showMenu = false"
    />
  </div>
</template>

<style scoped>
</style>
