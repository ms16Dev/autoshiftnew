<script setup lang="ts" xmlns="http://www.w3.org/1999/html">

import {ref} from "vue";

defineProps({

  position: {
    type: String,
    default: 'bottom', // Can be 'top', 'bottom', 'left', 'right'
  },
});


const emit = defineEmits(['close', 'save']);

// Local state for the price input
const year = ref<number | null>(2024);

const decYear = () => {
  if (year.value !== null) {
    year.value = Math.max(year.value - 1, 1980); // Ensure price doesn't go below 500
  }
};

const incYear = () => {
  if (year.value !== null) {
    year.value = Math.min(year.value + 1, 2025); // Ensure price doesn't exceed 45000
  }
};
// Emits close and save events

const saveYear = () => {
  if (year.value !== null) {
    // Emit the entered price to the parent
    emit('save', year.value);
  }
};
</script>

<template>
  <div
      :class="[
      'absolute  bg-pink-700 z-100  w-full h-full  ',
      position === 'bottom' ? 'top-0 end-0' : '',
      position === 'top' ? 'bottom-0 end-0' : '',
      position === 'left' ? 'mr-2 right-full top-0' : '',
      position === 'right' ? 'ml-2 left-full top-0' : '',
    ]"
  >


    <div class="flex flex-col">
      <p class="text-white text-3xl my-12"> Set year</p>

      <div class="flex flex-row items-center justify-between">
        <!-- decrease button -->
        <button
            @click="decYear"
            class="w-32 h-32 text-3xl text-white bg-pink-700  hover:bg-pink-500"
        >
          <i class="fas fa-minus"></i>
        </button>

        <h1 class="text-white text-3xl font-bold">{{year ?? 0}}</h1>

        <!-- increase button -->
        <button
            @click="incYear"
            class="w-32 h-32 text-3xl text-white bg-pink-700  hover:bg-pink-500"
        >
          <i class="fas fa-plus"></i>
        </button>

      </div>


      <div class="flex  justify-center ">
        <!-- Save button -->
        <button
            @click="saveYear"
            class="w-32 h-32 text-3xl text-white bg-pink-700  hover:bg-pink-500"
        >
          <i class="fas fa-check"></i>
        </button>
        <!-- Close button -->
        <button
            @click="$emit('close')"
            class="w-32 h-32 text-3xl text-white bg-pink-700  hover:bg-pink-500"
        >
          <i class="fas fa-x"></i>
        </button>
      </div>

    </div>




  </div>

</template>

<style scoped>
</style>
