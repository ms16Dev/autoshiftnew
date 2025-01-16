<script setup lang="ts">

import ImageLoader from '../ImageLoader.vue';
import {reactive, ref} from "vue";

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
import MultiImageLoader from "../MultiImageLoader.vue";


let classes: string[];
const make = ref<Make | null>(null);

const state = reactive({
  price: 1500,
  mileage: 121000,
  year: 2018,
  origin: 'Korean',
  gear: 'Auto',
  class: 'Yaris',
  type: 'Hybrid',
  engine: '4 piston',
  shape: 'Sedan',
  color: 'Red',
  luxury: [] as string[],
  safety: [] as string[],
  location: [] as string[],
  makePopUp: false,
  pricePopUp: false,
  mileagePopUp: false,
  yearPopUp: false,
  originPopUp: false,
  gearPopUp: false,
  classPopUp: false,
  typePopUp: false,
  enginePopUp: false,
  shapePopUp: false,
  luxuryPopUp: false,
  safetyPopUp: false,
  colorPopUp: false,
  locationPopUp: false,
});

// Type that picks only boolean keys from the state object
type BooleanKeys<T> = {
  [K in keyof T]: T[K] extends boolean ? K : never;
}[keyof T];

// Only allow keys in `state` with boolean values
const togglePopUp = (key: BooleanKeys<typeof state>) => {
  state[key] = !state[key];
};

// Save handler function for any field
const handleSave = <T extends keyof typeof state>(field: T) => {

  return (value: typeof state[T]) => {
    state[field] = value; // Dynamically update the field
    // Close the corresponding popup by passing to togglePopUp
    const popupKey = `${field}PopUp` as BooleanKeys<typeof state>;
    togglePopUp(popupKey); // This will toggle the popup for the corresponding field
  };
};

const setMake = (value: Make) =>{
  make.value = value;
  const item = makedata.value.find((obj) => obj.name_en === value.name);
  classes = item ? item.class_en : [];
  state.class = classes[0]
  toggleMake();
}

const toggleMake = () => {
  state.makePopUp = !state.makePopUp
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

  } catch (err) {
    // Handle error response
    const error = err as any; // Explicitly type the error
    console.error(error.response?.data.errors || error);  // You can handle error messages here
  }
};

// Call the fetchData function
fetchData();
const makes = ref<Make[]>([]); // Declare `makes` as a ref to hold the array of makes


</script>

<template>
  <div class="relative group w-[400px] ">
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
          <button class="flex-box bg-pink-700 text-white font-extrabold text-xl px-2 h-[30px]" @click="togglePopUp('pricePopUp')">$ {{formatNumber(state.price ?? 0)}}</button>
          <button class="w-fit bg-pink-400 text-white font-bold px-2 h-[30px]" @click="togglePopUp('originPopUp')">{{ state.origin }}</button>
        </div>



        <div class="flex flex-col">
        <button class="flex-box bg-pink-700 text-white font-extrabold text-xl px-2 h-[30px]" @click="togglePopUp('classPopUp')">{{state.class}}</button>
        <button @click="togglePopUp('yearPopUp')" class="w-fit bg-pink-400 text-white font-bold px-2 h-[30px] self-end">{{state.year}}</button>
        </div>

      </div>

      <div class="grid grid-cols-4 w-full justify-between px-3">



        <button class="flex flex-col items-center justify-center" @click="togglePopUp('mileagePopUp')">
          <i class="w-8 h-8 rounded-full fas fa-tachometer bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <span class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">
            {{formatNumber(state.mileage ?? 0) }}</span>
        </button>

        <button class="flex flex-col items-center justify-center"  @click="togglePopUp('gearPopUp')">
          <i class="w-8 h-8 rounded-full fas fa-gears bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <span class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">
            {{ state.gear }}</span>
        </button>

        <button class="flex flex-col items-center justify-center"  @click="togglePopUp('typePopUp')">
          <i class="w-8 h-8 rounded-full fas fa-gas-pump bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <p class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">{{ state.type }}</p>
        </button>

        <button class="flex flex-col items-center justify-center" @click="togglePopUp('enginePopUp')">
          <i class="w-8 h-8 rounded-full fas fa-bolt bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <p class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">{{state.engine}}</p>
        </button>

        <button class="flex flex-col items-center justify-center" @click="togglePopUp('shapePopUp')">
          <i class="w-8 h-8 rounded-full fas fa-car bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <span class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">
            {{state.shape}}</span>
        </button>

        <button class="flex flex-col items-center justify-center"  @click="togglePopUp('colorPopUp')">
          <i class="w-8 h-8 rounded-full fas fa-brush bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <span class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">
            {{ state.color }}</span>
        </button>

        <button class="flex flex-col items-center justify-center"  @click="togglePopUp('luxuryPopUp')">
          <i class="w-8 h-8 rounded-full fas fa-leaf bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <p class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">Luxury</p>
        </button>

        <button class="flex flex-col items-center justify-center" @click="togglePopUp('safetyPopUp')">
          <i class="w-8 h-8 rounded-full fas fa-life-ring bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <p class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">Safety</p>
        </button>




      </div>

        <div class="w-full border-b-2 border-b-pink-700 mt-2 "></div>


        <MultiImageLoader />

        <div class="w-full border-b-2 border-b-pink-700 mt-2 "></div>


        <div class="flex items-start justify-between  m-2">
          <!-- Location Button -->
          <button class="flex flex-row items-center " @click="togglePopUp('locationPopUp')">
            <i class="w-8 h-8 rounded-full fas fa-map bg-pink-300 text-white text-sm p-2 flex items-center justify-center"></i>
            <span class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">Location</span>
          </button>

          <!-- Save Post Button -->
          <button
              class="py-1 px-3 text-lg font-medium bg-pink-600 text-white rounded-md shadow hover:bg-pink-700 focus:outline-none"
          >
            Save Post
          </button>
        </div>


      </div>

    </div>

    <MakePopUp
        v-if="state.makePopUp"
        @choice="setMake"
        :makes="makes"
        position="bottom"></MakePopUp>


    <PricePopUp
        v-if="state.pricePopUp"
        @close="state.pricePopUp = false"
        @save="(value) => handleSave('price')(value)"
        position="top"
        :value="state.price"></PricePopUp>

    <MileagePopUp
        v-if="state.mileagePopUp"
        @close="state.mileagePopUp = false"
        @save="(value) => handleSave('mileage')(value)"
        position="bottom"
        :value="state.mileage"></MileagePopUp>

    <YearPopUp
        v-if="state.yearPopUp"
        @close="state.yearPopUp = false"
        @save="(value) => handleSave('year')(value)"
        position="bottom"
        :value="state.year"></YearPopUp>

    <OptionsPopUp
        v-if="state.originPopUp"
        @choice="(value) => handleSave('origin')(value)"
        @close="state.originPopUp = false"
        :options="origins"></OptionsPopUp>

    <OptionsPopUp
        v-if="state.gearPopUp"
        @choice="(value) => handleSave('gear')(value)"
        @close="state.gearPopUp = false"
        :options="gears"></OptionsPopUp>

    <OptionsPopUp
        v-if="state.classPopUp"
        @choice="(value) => handleSave('class')(value)"
        @close="state.classPopUp = false"
        :options="classes"></OptionsPopUp>

    <OptionsPopUp
        v-if="state.typePopUp"
        @choice="(value) => handleSave('type')(value)"
        @close="state.typePopUp = false"
        :options="car_type"></OptionsPopUp>

    <OptionsPopUp
        v-if="state.enginePopUp"
        @choice="(value) => handleSave('engine')(value)"
        @close="state.enginePopUp = false"
        :options="engine_type"></OptionsPopUp>


    <OptionsPopUp
        v-if="state.shapePopUp"
        @choice="(value) => handleSave('shape')(value)"
        @close="state.shapePopUp = false"
        :options="shapes"></OptionsPopUp>


    <OptionsPopUp
        v-if="state.colorPopUp"
        @choice="(value) => handleSave('color')(value)"
        @close="state.colorPopUp = false"
        :options="colors"></OptionsPopUp>


    <ChipsPopUp
        v-if="state.luxuryPopUp"
        @choice="(value) => handleSave('luxury')(value)"
        @close="state.luxuryPopUp = false"
        :options="shapes"
        :checked="state.luxury"></ChipsPopUp>

    <ChipsPopUp
        v-if="state.safetyPopUp"
        @choice="(value) => handleSave('safety')(value)"
        @close="state.safetyPopUp = false"
        :options="shapes"
        :checked="state.safety"></ChipsPopUp>

    <ChipsPopUp
        v-if="state.locationPopUp"
        @choice="(value) => handleSave('location')(value)"
        @close="state.locationPopUp = false"
        :options="shapes"
        :checked="state.location"></ChipsPopUp>

  </div>
</template>

<style scoped>

</style>