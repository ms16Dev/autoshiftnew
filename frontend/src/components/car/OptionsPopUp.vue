<script setup lang="ts">

import OptionItem from "./OptionItem.vue";
import {RefOption} from "./CarFormNew.vue";
import {PropType} from "vue";

const props = defineProps({

  position: {
    type: String,
    default: 'bottom', // Can be 'top', 'bottom', 'left', 'right'
  },
  title: {
    type: String,
    required: false,

  },
  options: {
    type: Array as PropType<RefOption[]>,
    required: true,
  },

});

const emit = defineEmits(['choice', 'close']);


// Emits close and save events

const saveOption = (index: number) => {
    emit('choice', props.options[index].name_en);
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

    <div class="flex items-center h-24">
      <button @click="emit('close')" class="w-24 h-24 hover:bg-pink-500 flex items-center justify-center">
        <span class="fas fa-arrow-left text-white text-2xl"></span>
      </button>
      <h1 class="text-white text-2xl text-center">Select {{props.title}}</h1>
    </div>

    <div class=" flex-col grid grid-cols-1 overflow-y-scroll scrollbar-none ">
      <div  v-for="(opt, index) in options" :key="index">
        <OptionItem :option="opt.name_en" class="w-full p-2 text-white hover:bg-pink-500" @click="saveOption(index)"></OptionItem>
      </div>
    </div>




  </div>
</template>

<style scoped>
</style>
