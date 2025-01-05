<script setup lang="ts">

import {PropType, ref} from "vue";
import ChipItem from "./ChipItem.vue";
import ChipItemChecked from "./ChipItemChecked.vue";

const props = defineProps({

  position: {
    type: String,
    default: 'bottom', // Can be 'top', 'bottom', 'left', 'right'
  },
  options: {
    type: Array as PropType<string[]>,
    required: true,
  },
  checked: {
    type: Array as PropType<string[]>, // Ensure type matches
    default: () => [], // Default to an empty array
  },
});

const emit = defineEmits(['choice', 'close']);


// Emits close and save events

const saveOptions = () => {
    emit('choice', checked);
};

const checked = ref<string[]>(props.checked);



function toggleOption(index: number, fromChecked: boolean) {
  const source = fromChecked ? checked.value : props.options;
  const target = fromChecked ? props.options : checked.value;

  const option = source[index];
  source.splice(index, 1);
  target.push(option);
}


</script>

<template>
  <div
      :class="[
      'absolute  bg-pink-700 z-100 w-full h-full  ',
      position === 'bottom' ? 'top-0 end-0 ' : '',
      position === 'top' ? 'bottom-full end-0' : '',
      position === 'left' ? 'mr-2 right-full top-0' : '',
      position === 'right' ? 'ml-2 left-full top-0' : '',
    ]"
  >
  <div class="overflow-y-scroll h-full scrollbar-none ">

    <div class=" flex-wrap  ">
      <ChipItemChecked  v-for="(opt, index) in checked" :key="index" :option="opt" class=" p-2 text-white hover:bg-pink-500" @click="toggleOption(index,true )"></ChipItemChecked>
    </div>

    <div class="my-3 mx-1 border-b-2 border-white"></div>

    <div class=" flex-wrap  ">
      <ChipItem  v-for="(opt, index) in options" :key="index" :option="opt" class=" p-2 text-white hover:bg-pink-500" @click="toggleOption(index, false)"></ChipItem>
    </div>

    <div class="flex  justify-center ">
      <!-- Save button -->
      <button
          @click="saveOptions"
          class="w-32 h-32 text-3xl text-white bg-pink-700  hover:bg-pink-500"
      >
        <i class="fas fa-check"></i>
      </button>

    </div>
  </div>



  </div>
</template>

<style scoped>
</style>
