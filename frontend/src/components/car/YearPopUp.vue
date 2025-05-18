<script setup lang="ts" xmlns="http://www.w3.org/1999/html">

import {ref, watch} from "vue";
import {useI18n} from "vue-i18n";
const { t } = useI18n()

const props = defineProps({

  position: {
    type: String,
    default: 'bottom', // Can be 'top', 'bottom', 'left', 'right'
  },
  value: {
    type: Number,
    default: 2024, // Can be 'top', 'bottom', 'left', 'right'
  },
});


const emit = defineEmits(['close', 'save']);

const year = ref<number>(props.value || 2024);

watch(() => props.value, (newVal) => {
  year.value = newVal || 2024;
});

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
      <p class="text-white text-3xl my-12"> {{t('car_year_title') }}</p>

      <div class="flex flex-row items-center justify-between rtl:flex-row-reverse">
        <!-- decrease button -->
        <button
            @click="decYear"
            class="w-32 h-32 text-3xl text-white bg-pink-700  hover:bg-pink-500"
        >
          <i class="fas fa-minus"></i>
        </button>

        <h1 class="text-white text-3xl font-bold">{{year}}</h1>

        <!-- increase button -->
        <button
            @click="incYear"
            class="w-32 h-32 text-3xl text-white bg-pink-700  hover:bg-pink-500"
        >
          <i class="fas fa-plus"></i>
        </button>

      </div>


      <div class="flex flex-row-reverse justify-center rtl:flex-row-reverse">
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
