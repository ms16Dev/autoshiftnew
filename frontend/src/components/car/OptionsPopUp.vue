<script setup lang="ts">

import {PropType} from "vue";
import OptionItem from "./OptionItem.vue";

const props = defineProps({

  position: {
    type: String,
    default: 'bottom', // Can be 'top', 'bottom', 'left', 'right'
  },
  options: {
    type: Array as PropType<string[]>,
    required: true,

  },
});

const emit = defineEmits(['choice', 'close']);


// Emits close and save events

const saveOption = (index: number) => {
    emit('choice', props.options[index]);
};
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

    <div class="h-full flex-col grid grid-cols-1 overflow-y-scroll scrollbar-none ">
      <div  v-for="(opt, index) in options" :key="index">
        <OptionItem :option="opt" class="w-full p-2 text-white hover:bg-pink-500" @click="saveOption(index)"></OptionItem>
      </div>
    </div>




  </div>
</template>

<style scoped>
</style>
