<script setup lang="ts">

import {ref} from "vue";
import MakeItem from "./MakeItem.vue";

defineProps({

  position: {
    type: String,
    default: 'bottom', // Can be 'top', 'bottom', 'left', 'right'
  },
});
export interface Make  {
  name: string,
  name_ar: string,
  url: string,
}

const makes = [
  {
    name: "Toyota",
    name_ar: "تويوتا",
    url: "2.png"

  },
  {
    name: "Hyundai",
    name_ar: "هيونداي",
    url: "6.png"

  },
  {
    name: "Kia",
    name_ar: "كيا",
    url: "9.png"

  },
  {
    name: "Mercides",
    name_ar: "مرسيديس",
    url: "8.png"

  },
  {
    name: "Nissan",
    name_ar: "نيسان",
    url: "7.png"

  },
  {
    name: "Jeep",
    name_ar: "جيب",
    url: "4.png"

  },
  {
    name: "Dodge",
    name_ar: "دودج",
    url: "3.png"

  },
  {
    name: "Suzuki",
    name_ar: "سوزوكي",
    url: "13.png"

  },
  {
    name: "Daewoo",
    name_ar: "دايو",
    url: "11.png"

  },
  {
    name: "Mazda",
    name_ar: "مازدا",
    url: "12.png"

  },
  {
    name: "Range",
    name_ar: "رنج روفر",
    url: "10.png"

  },
  {
    name: "Mitsubishi",
    name_ar: "ميتسوبيشي",
    url: "10.png"

  },
]

const emit = defineEmits(['close', 'choice']);

// Local state for the price input
const make = ref<Make | null>(null);

// Emits close and save events

const saveMake = (value: number) => {
  make.value = makes[value]
    // Emit the entered price to the parent
    emit('choice', make.value);

};
</script>

<template>
  <div
      :class="[
      'absolute  bg-pink-700 z-100 w-full h-full ',
      position === 'bottom' ? 'top-0 end-0' : '',
      position === 'top' ? 'bottom-full end-0' : '',
      position === 'left' ? 'mr-2 right-full top-0' : '',
      position === 'right' ? 'ml-2 left-full top-0' : '',
    ]"
  >
    <div class="h-full flex-col grid grid-cols-2 gap-4 overflow-y-scroll scrollbar-none ">
        <MakeItem  v-for="(make, index) in makes" :key="index" :url="`/public/carmakes/${make.url}`" class="h-full p-2 hover:bg-white" @click="saveMake(index)"></MakeItem>
    </div>




  </div>
</template>

<style scoped>
</style>
