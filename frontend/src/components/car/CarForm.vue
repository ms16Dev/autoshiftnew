<script setup lang="ts">

import ImageLoader from '../ImageLoader.vue';
import {ref} from "vue";

const formatNumber = (number: number): string => {
  return new Intl.NumberFormat('en-US').format(number);
};

import PricePopUp from "./PricePopUp.vue";
import MakePopUp, {Make} from "./MakePopUp.vue";
import MileagePopUp from "./MileagePopUp.vue";
import YearPopUp from "./YearPopUp.vue"
import OptionsPopUp from "./OptionsPopUp.vue";


const pricePopUp = ref(false)
const makePopUp = ref(false)
const mileagePopUp = ref(false)
const yearPopUp = ref(false)
const originPopUp = ref(false)
const gearPopUp = ref(false)

const price = ref<number | null>(1500);
const make = ref<Make | null>(null);
const mileage = ref<number | null>(121000);
const year = ref<number | null>(2018);
const origin = ref<string | null>('Korean');
const gear = ref<string | null>('Auto')


const setPrice = (value: number) => {
  price.value = value;
  togglePrice()
};

const togglePrice = () => {
  pricePopUp.value = !pricePopUp.value;
}

const setMake = (value: Make) =>{
  make.value = value;
  toggleMake();
}

const toggleMake = () => {
  makePopUp.value = !makePopUp.value
}

const setMileage = (value: number) => {
  mileage.value = value;
  toggleMileage();
}

const toggleMileage = () => {
  mileagePopUp.value = !mileagePopUp.value
}

const setYear = (value: number) => {
  year.value = value;
  toggleYear()
};

const toggleYear = () => {
  yearPopUp.value = !yearPopUp.value;
}

const setOrigin = (value: string) =>{
  origin.value = value;
  toggleOrigin();
}

const toggleOrigin = () => {
  originPopUp.value = !originPopUp.value
}


const setGear = (value: string) =>{
  gear.value = value;
  toggleGear();
}

const toggleGear = () => {
  gearPopUp.value = !gearPopUp.value
}

const origins = ['American','Korean', 'Gulf', 'Local']
const gears = ['Auto','Manual']
</script>

<template>
  <div class="relative group">
    <div class="absolute inset-px shadow-lg bg-white dark:bg-gray-900  "></div>
    <div class="relative flex flex-col overflow-hidden rounded-lg">
      <div class="group flex-col inset-px shadow-md">



<!-- Cover image-->
        <div class="overflow-hidden">
          <image-loader class="w-full  "/>

        </div>
<!--Devider-->
      <div class="w-full border-b-2 border-b-pink-700 "></div>
      <div class="flex flex-row w-full justify-between -translate-y-1/2">

        <button  @click="toggleMake" class="absolute rounded-full ring-2 ring-pink-700 h-24 w-24 bg-gray-100 -translate-y-1/4 start-1/2 -translate-x-1/2 overflow-hidden">
          <img :src="`/public/carmakes/${make?.url ?? '2.png'} `">
        </button>


        <div class="flex flex-col">
          <button class="flex-box bg-pink-700 text-white font-extrabold text-xl px-2 h-[30px]" @click="togglePrice">$ {{formatNumber(price ?? 0)}}</button>
          <button class="w-fit bg-pink-400 text-white font-bold px-2 h-[30px]" @click="toggleOrigin">{{ origin }}</button>
        </div>



        <div class="flex flex-col">
        <div class="flex-box bg-pink-700 text-white font-extrabold text-xl px-2 h-[30px] ">E-320</div>
        <button @click="toggleYear" class="w-fit bg-pink-400 text-white font-bold px-2 h-[30px] self-end">{{year}}</button>
        </div>

      </div>

      <div class="flex flex-row w-full justify-between px-3">



        <button class="flex flex-col items-center justify-center" @click="toggleMileage">
          <i class="w-8 h-8 rounded-full fas fa-tachometer bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <span class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">
            {{formatNumber(mileage ?? 0) }}</span>
        </button>

        <button class="flex flex-col items-center justify-center"  @click="toggleGear">
          <i class="w-8 h-8 rounded-full fas fa-gears bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <span class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">
            {{ gear }}</span>
        </button>

        <div class="flex flex-col items-center justify-center">
          <i class="w-8 h-8 rounded-full fas fa-gas-pump bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <p class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">Hybrid</p>
        </div>

        <div class="flex flex-col items-center justify-center">
          <i class="w-8 h-8 rounded-full fas fa-bolt bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <p class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">4 piston</p>
        </div>
        <PricePopUp
            v-if="pricePopUp"
            @close="pricePopUp = false"
            @save="setPrice"
            position="bottom"></PricePopUp>
      </div>

      </div>

        <div class="flex items-center justify-center py-2 px-6 bg-pink-100 shadow-lg ">
          <div class="flex px-3 w-fit ">
            <i class="fa fa-leaf text-2xl text-gray-400"/>
            <p class="w-fit text-gray-400 font-bold px-2 h-[30px]">Luxury</p>
          </div>
        </div>
        <div class="h-48"></div>
        <div class="flex items-center justify-center py-2 px-6 bg-pink-100 shadow-lg">
          <div class="flex px-3 w-fit">
            <i class="fas fa-life-ring text-2xl text-gray-400"/>
            <p class="w-fit text-gray-400 font-bold px-2 h-[30px]">Safety</p>
          </div>
        </div>
        <div class="h-48"></div>
    </div>
  </div>


        <MakePopUp
          v-if="makePopUp"
          @choice="setMake"
          position="bottom"></MakePopUp>

        <MileagePopUp
            v-if="mileagePopUp"
            @close="mileagePopUp = false"
            @save="setMileage"
            position="bottom"></MileagePopUp>

        <YearPopUp
            v-if="yearPopUp"
            @close="yearPopUp = false"
            @save="setYear"
            position="bottom"></YearPopUp>

        <OptionsPopUp
            v-if="originPopUp"
            @choice="setOrigin"
            @close="originPopUp = false"
            :options="origins"></OptionsPopUp>

        <OptionsPopUp
            v-if="gearPopUp"
            @choice="setGear"
            @close="gearPopUp = false"
            :options="gears"></OptionsPopUp>








</template>

<style scoped>

</style>