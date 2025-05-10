<script setup lang="ts">
import { ref } from 'vue';

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

const currentIcon = ref(props.icon);
const currentText = ref(props.text);


</script>

<template>
  <div class="relative inline-block">
    <!-- Action button -->
    <router-link

        @click=""
        class="flex items-center space-x-2 px-4 py-2 bg-pink-500 text-white hover:transform
               transition-transform duration-900 ease-in-out hover:bg-pink-600 hover:scale-110 rounded-full"
     to="/cars/add-car">
      <i :class="[currentIcon, 'text-2xl text-center w-8 h-8']" />
      <span class="hidden xl:block font-extrabold">{{ currentText }}</span>
    </router-link>


  </div>
</template>

<style scoped>
</style>
