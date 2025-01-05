<script setup lang="ts" xmlns="http://www.w3.org/1999/html">

import {ref} from "vue";

const props = defineProps({

  position: {
    type: String,
    default: 'bottom', // Can be 'top', 'bottom', 'left', 'right'
  },
  value: {
    type: Number,
    default: 0, // Can be 'top', 'bottom', 'left', 'right'
  },
});

const formatNumber = (number: number): string => {
  return new Intl.NumberFormat('en-US').format(number);
};

const emit = defineEmits(['close', 'save']);

// Local state for the price input
const price = ref<number | null>(props.value);

const decPrice = () => {
  if (price.value !== null) {
    price.value = Math.max(price.value - 500, 500); // Ensure price doesn't go below 500
  }
};

const incPrice = () => {
  if (price.value !== null) {
    price.value = Math.min(price.value + 500, 45000); // Ensure price doesn't exceed 45000
  }
};
// Emits close and save events

const savePrice = () => {
  if (price.value !== null) {
    // Emit the entered price to the parent
    emit('save', price.value);
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
      <p class="text-white text-3xl my-12"> Set price</p>

    <div class="flex flex-row items-center justify-between">
      <!-- decrease button -->
      <button
          @click="decPrice"
          class="w-32 h-32 text-3xl text-white bg-pink-700  hover:bg-pink-500"
      >
        <i class="fas fa-minus"></i>
      </button>

      <h1 class="text-white text-3xl font-bold">{{formatNumber(price ?? 0)}}</h1>

      <!-- increase button -->
      <button
          @click="incPrice"
          class="w-32 h-32 text-3xl text-white bg-pink-700  hover:bg-pink-500"
      >
        <i class="fas fa-plus"></i>
      </button>

    </div>


      <div class="flex  justify-center ">
        <!-- Save button -->
        <button
            @click="savePrice"
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
