<script setup lang="ts">

import OptionItem from "./OptionItem.vue";
import {computed, PropType} from "vue";
import {City, DataItem} from "../../types/staticData.ts";
import {useStaticDataStore} from "../../stores/staticDataStore.ts";


const staticData = useStaticDataStore();
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
    type: Array as PropType<DataItem[]>,
    required: true,
  },

});
const isRtl = computed(() => document.documentElement.dir === 'rtl');

const emit = defineEmits(['choice', 'close']);



// Emits close and save events

const saveOption = (index: number) => {
  if (props.title === 'location') {
    emit('choice', props.options[index] as City);

  }else{
    emit('choice', props.options[index]);

  }
};
</script>

<template>
  <div
      :class="[
      'absolute overflow-y-scroll scrollbar-none bg-pink-700 z-100 w-full h-full  ',
      position === 'bottom' ? 'top-0 end-0 ' : '',
      position === 'top' ? 'bottom-full end-0' : '',
      position === 'left' ? 'mr-2 right-full top-0' : '',
      position === 'right' ? 'ml-2 left-full top-0' : '',
    ]"
  >

    <div class="flex items-center h-24">
      <button @click="emit('close')" class="w-24 h-24 hover:bg-pink-500 flex items-center justify-center">
        <span class="text-white text-2xl" :class="{
          'fas fa-arrow-right': isRtl,
          'fas fa-arrow-left': !isRtl
        }"></span>
      </button>
      <h1 class="text-white text-2xl text-center">{{props.title}}</h1>
    </div>

    <div class=" flex-col grid grid-cols-1 overflow-y-scroll scrollbar-none ">
      <div  v-for="(opt, index) in options" :key="index">
        <OptionItem :option="staticData.getLocalizedName(opt)" class="w-full p-2 text-white hover:bg-pink-500" @click="saveOption(index)"></OptionItem>
      </div>
    </div>




  </div>
</template>

<style scoped>
</style>
