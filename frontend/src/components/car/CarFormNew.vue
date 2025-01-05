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
import ChipsPopUp from "./ChipsPopUp.vue";
import ApiService from "../../services/ApiService.ts";


let classes: string[];


const pricePopUp = ref(false)
const makePopUp = ref(false)
const mileagePopUp = ref(false)
const yearPopUp = ref(false)
const originPopUp = ref(false)
const gearPopUp = ref(false)
const classPopUp = ref(false)
const typePopUp = ref(false)
const enginePopUp = ref(false)
const shapePopUp = ref(false)
const luxuryPopUp = ref(false)
const safetyPopUp = ref(false)
const colorPopUp = ref(false)


const price = ref<number | null>(1500);
const make = ref<Make | null>(null);
const mileage = ref<number | null>(121000);
const year = ref<number | null>(2018);
const origin = ref<string | null>('Korean');
const gear = ref<string | null>('Auto')
const class_ = ref<string | null>('Yaris')
const type_  = ref<string | null>('Hybrid')
const engine  = ref<string | null>('4 piston')
const shape  = ref<string | null>('Sedan')
const color = ref<string | null>('Red')
const luxury = ref<string[]>([]);
const safety  =  ref<string[]>([]);

const setPrice = (value: number) => {
  price.value = value;
  togglePrice()
};

const togglePrice = () => {
  pricePopUp.value = !pricePopUp.value;
}

const setMake = (value: Make) =>{
  make.value = value;
  const item = makedata.value.find((obj) => obj.name_en === value.name);
  classes = item ? item.class_en : [];
  class_.value = classes[0]
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

const setClass = (value: string ) => {
  class_.value = value
  toggleClass()
}
const toggleClass = () => {
  classPopUp.value = !classPopUp.value
}

const setType = (value: string ) => {
  type_.value = value
  toggleType()
}
const toggleType = () => {
  typePopUp.value = !typePopUp.value
}

const setEngine = (value: string ) => {
  engine.value = value
  toggleEngine()
}
const toggleEngine = () => {
  enginePopUp.value = !enginePopUp.value
}



const setShape = (value: string ) => {
  shape.value = value
  toggleShape()
}
const toggleShape = () => {
  shapePopUp.value = !shapePopUp.value
}


const setLuxury = (value: string[] ) => {
  luxury.value = value
  toggleLuxury()
}

const toggleLuxury = () => {
  luxuryPopUp.value = !luxuryPopUp.value
}

const setSafety = (value: string[] ) => {
  safety.value = value
  toggleSafety()
}

const toggleSafety = () => {
  safetyPopUp.value = !safetyPopUp.value
}

const setColor = (value: string ) => {
  color.value = value
  toggleColor()
}

const toggleColor = () => {
  colorPopUp.value = !colorPopUp.value
}


const origins = ['American','Korean', 'Gulf', 'Local']
const gears = ['Auto','Manual']
const car_type = [
  "Petrol",
  "Diesel",
  "Hybrid",
  "Electric",
]
const engine_type= [
  "2 piston",
  "3 piston",
  "4 piston",
  "6 piston",
  "8 piston",
  "12 piston",
]
const shapes = [
  "Sedan",
  "SUV",
  "Hatchback",
  "Coupe",
  "Convertible",
  "Pickup",
  "Van",
  "Minivan",
  "Crossover",
  "Off-Road",
  "Compact",
  "Roadster",
  "Supercar",
  "Microcar",
  "Limousine",
];


const colors = [
  "White",
  "Black",
  "Gray",
  "Silver",
  "Blue",
  "Red",
  "Brown",
  "Beige",
  "Green",
  "Yellow",
  "Gold",
  "Orange"
]

const makedata = ref<any[]>([]); // Initialize refdata as an empty array

const fetchData = async () => {
  try {
    const { data } = await ApiService.get("/ref-data"); // Use await with the async function
    makedata.value = data;  // Update the refdata without blocking the UI

    makes.value = makedata.value.map((item: any) => ({
      name: item.name_en,
      name_ar: item.name_ar,
      url: item.url,
    }));

  } catch (error) {
    // Handle error response
    console.error(error.response?.data.errors || error);  // You can handle error messages here
  }
};

// Call the fetchData function
fetchData();
const makes = ref<Make[]>([]); // Declare `makes` as a ref to hold the array of makes


</script>

<template>
  <div class="relative group w-[400px] h-[600px]">
    <div class="absolute inset-px shadow-lg bg-white dark:bg-gray-900  "></div>
    <div class="relative flex flex-col overflow-hidden rounded-lg">
      <div class="group flex-col inset-px shadow-md">

<!-- Cover image-->
        <div class="overflow-hidden">
          <image-loader class="w-full"/>

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
        <button class="flex-box bg-pink-700 text-white font-extrabold text-xl px-2 h-[30px]" @click="toggleClass">{{class_}}</button>
        <button @click="toggleYear" class="w-fit bg-pink-400 text-white font-bold px-2 h-[30px] self-end">{{year}}</button>
        </div>

      </div>

      <div class="grid grid-cols-4 w-full justify-between px-3">



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

        <button class="flex flex-col items-center justify-center"  @click="toggleType">
          <i class="w-8 h-8 rounded-full fas fa-gas-pump bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <p class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">{{ type_ }}</p>
        </button>

        <button class="flex flex-col items-center justify-center" @click="toggleEngine">
          <i class="w-8 h-8 rounded-full fas fa-bolt bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <p class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">{{engine}}</p>
        </button>

        <button class="flex flex-col items-center justify-center" @click="toggleShape">
          <i class="w-8 h-8 rounded-full fas fa-car bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <span class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">
            {{shape}}</span>
        </button>

        <button class="flex flex-col items-center justify-center"  @click="toggleColor">
          <i class="w-8 h-8 rounded-full fas fa-brush bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <span class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">
            {{ color }}</span>
        </button>

        <button class="flex flex-col items-center justify-center"  @click="toggleLuxury">
          <i class="w-8 h-8 rounded-full fas fa-leaf bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <p class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">Luxury</p>
        </button>

        <button class="flex flex-col items-center justify-center" @click="toggleSafety">
          <i class="w-8 h-8 rounded-full fas fa-life-ring bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <p class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">Safety</p>
        </button>

      </div>


      </div>

    </div>

    <MakePopUp
        v-if="makePopUp"
        @choice="setMake"
        :makes="makes"
        position="bottom"></MakePopUp>


    <PricePopUp
        v-if="pricePopUp"
        @close="pricePopUp = false"
        @save="setPrice"
        position="top"
        :value="price"></PricePopUp>

    <MileagePopUp
        v-if="mileagePopUp"
        @close="mileagePopUp = false"
        @save="setMileage"
        position="bottom"
        :value="mileage"></MileagePopUp>

    <YearPopUp
        v-if="yearPopUp"
        @close="yearPopUp = false"
        @save="setYear"
        position="bottom"
        :value="year"></YearPopUp>

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

    <OptionsPopUp
        v-if="classPopUp"
        @choice="setClass"
        @close="classPopUp = false"
        :options="classes"></OptionsPopUp>

    <OptionsPopUp
        v-if="typePopUp"
        @choice="setType"
        @close="typePopUp = false"
        :options="car_type"></OptionsPopUp>

    <OptionsPopUp
        v-if="enginePopUp"
        @choice="setEngine"
        @close="enginePopUp = false"
        :options="engine_type"></OptionsPopUp>


    <OptionsPopUp
        v-if="shapePopUp"
        @choice="setShape"
        @close="shapePopUp = false"
        :options="shapes"></OptionsPopUp>


    <OptionsPopUp
        v-if="colorPopUp"
        @choice="setColor"
        @close="colorPopUp = false"
        :options="colors"></OptionsPopUp>


    <ChipsPopUp
        v-if="luxuryPopUp"
        @choice="setLuxury"
        @close="luxuryPopUp = false"
        :options="shapes"
        :checked="luxury"></ChipsPopUp>

    <ChipsPopUp
        v-if="safetyPopUp"
        @choice="setSafety"
        @close="safetyPopUp = false"
        :options="shapes"
        :checked="safety"></ChipsPopUp>

  </div>



</template>

<style scoped>

</style>