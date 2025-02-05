<script setup lang="ts">
import { ref } from 'vue';
import PopupMenu from './UserMenu.vue'; // Import the pop-up menu component

const props = defineProps({
  text: {
    type: String,
    required: true,
    default: 'Click Me!',
  },
  icon: {
    type: [String, Array],
    required: true,
  },
  menuPosition: {
    type: String,
    default: 'up', // Can be 'top', 'bottom', 'left', or 'right'
  },
});

const showMenu = ref(false);
const currentIcon = ref(props.icon);
const currentText = ref(props.text);

// Function to toggle the visibility of the menu
const toggleMenu = () => {
  if(showMenu.value){
    currentIcon.value = props.icon
    currentText.value = props.text

  }else {
  }
  showMenu.value = !showMenu.value;
};
</script>

<template>
  <div class="relative inline-block">
    <!-- Action button -->
    <button
        @click="toggleMenu"
        class="flex items-center space-x-2 px-3 py-2 bg-gray-50 text-white hover:transform
               transition-transform duration-900 ease-in-out hover:bg-pink-600 hover:scale-110 rounded-full shadow-lg"
    >
      <img src="/vite.svg">
    </button>

    <!-- Popup menu -->
    <PopupMenu
        v-if="showMenu"
        @close="showMenu = false"
        :position="menuPosition"
    />
  </div>
</template>

<style scoped>
</style>
