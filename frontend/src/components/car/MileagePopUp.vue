<script setup lang="ts">

import {ref} from "vue";

defineProps({

  position: {
    type: String,
    default: 'bottom', // Can be 'top', 'bottom', 'left', 'right'
  },
});

const emit = defineEmits(['close', 'save']);

// Local state for the price input
const mileage = ref<number | null>(20000);



const decMileage = () => {
  if (mileage.value !== null) {
    mileage.value = Math.max(mileage.value - 500, 500); // Ensure price doesn't go below 500
  }
};

const incMileage = () => {
  if (mileage.value !== null) {
    mileage.value = Math.min(mileage.value + 500, 200000); // Ensure price doesn't exceed 45000
  }
};

const saveMileage = () => {
  if (mileage.value !== null) {
    // Emit the entered price to the parent
    emit('save', mileage.value);
  }
};
</script>

<template>
  <div
      :class="[
      'absolute  bg-pink-700 z-100 p-12 w-full h-full  ',
      position === 'bottom' ? 'top-0 end-0 ' : '',
      position === 'top' ? 'bottom-full end-0' : '',
      position === 'left' ? 'mr-2 right-full top-0' : '',
      position === 'right' ? 'ml-2 left-full top-0' : '',
    ]"
  >

    <div class="flex flex-col">
      <p class="text-white text-3xl my-12"> Set mileage</p>

      <div class="flex flex-row items-center justify-between">
        <!-- decrease button -->
        <button
            @click="decMileage"
            class="w-32 h-32 text-3xl text-white text-center bg-pink-700  hover:bg-pink-500"
        >
          <i class="fas fa-minus"></i>
        </button>


        <form class="w-36">
          <input
              type="number"
              class="w-36 bg-pink-700 text-white text-3xl font-bold appearance-none border-none focus:outline-none focus:ring-0"
              v-model="mileage">

        </form>

        <!-- increase button -->
        <button
            @click="incMileage"
            class="w-32 h-32 text-3xl text-white bg-pink-700  hover:bg-pink-500"
        >
          <i class="fas fa-plus"></i>
        </button>

      </div>


      <div class="flex  justify-center ">
        <!-- Save button -->
        <button
            @click="saveMileage"
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
