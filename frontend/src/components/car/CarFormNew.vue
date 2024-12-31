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

const makes = {
    "Toyota": [
  "Yaris",
  "Corolla",
  "Camry",
  "Avalon",
  "RAV4",
  "Highlander",
  "4Runner",
  "Sequoia",
  "Tacoma",
  "Tundra",
  "Prius",
  "C-HR",
  "GR86",
  "Supra"
],
    "Honda": [
  "Civic",
  "Accord",
  "Insight",
  "CR-V",
  "HR-V",
  "Passport",
  "Pilot",
  "Ridgeline",
  "Odyssey"
],
    "Mitsubishi": [
  "Mirage",
  "Lancer",
  "Outlander",
  "Eclipse Cross",
  "Pajero",
  "Triton",
  "ASX",
  "Delica"
],
    "Daewoo": [
  "Matiz",
  "Lanos",
  "Nubira",
  "Leganza",
  "Tacuma",
  "Magnus",
  "Evanda"
],
    "Daihatsu": [
  "Mira",
  "Charade",
  "Terios",
  "Move",
  "Sirion",
  "Copen",
  "Rocky"
],
    "Ford": [
  "Fiesta",
  "Focus",
  "Fusion",
  "Taurus",
  "Escape",
  "Edge",
  "Explorer",
  "Expedition",
  "F-150",
  "Ranger",
  "Maverick",
  "Bronco",
  "Mustang"
],
    "Chevrolet": [
  "Spark",
  "Malibu",
  "Impala",
  "Equinox",
  "Trailblazer",
  "Traverse",
  "Tahoe",
  "Suburban",
  "Colorado",
  "Silverado",
  "Corvette",
  "Camaro",
  "Bolt EV"
],
    "Hyundai": [
  "Accent",
  "Elantra",
  "Sonata",
  "Ioniq",
  "Kona",
  "Tucson",
  "Santa Fe",
  "Palisade",
  "Veloster"
],
    "Kia": [
  "Rio",
  "Forte",
  "Optima",
  "Stinger",
  "Seltos",
  "Sportage",
  "Sorento",
  "Telluride",
  "Soul",
  "Niro"
],
    "Nissan": [
  "Versa",
  "Sentra",
  "Altima",
  "Maxima",
  "Rogue",
  "Murano",
  "Pathfinder",
  "Armada",
  "Frontier",
  "Titan",
  "Leaf",
  "GT-R",
  "Z"
],
    "Subaru": [
  "Impreza",
  "Legacy",
  "Outback",
  "Forester",
  "Ascent",
  "WRX",
  "BRZ"
],
    "Volkswagen": [
  "Jetta",
  "Passat",
  "Arteon",
  "Golf",
  "Tiguan",
  "Atlas",
  "Beetle",
  "ID.4"
],
    "Mazda": [
  "Mazda3",
  "Mazda6",
  "CX-3",
  "CX-30",
  "CX-5",
  "CX-50",
  "CX-9",
  "MX-5 Miata"
],
    "Suzuki": [
  "Alto",
  "Swift",
  "Ciaz",
  "Ertiga",
  "Vitara",
  "S-Cross",
  "Jimny",
  "Baleno"
],
    "Isuzu": [
  "D-Max",
  "MU-X",
  "N-Series",
  "F-Series",
  "Elf",
  "Trooper",
  "Rodeo"
],
    "Lexus": [
  "IS",
  "ES",
  "GS",
  "LS",
  "UX",
  "NX",
  "RX",
  "GX",
  "LX",
  "RC",
  "LC"
],
    "BMW": [
  "1 Series",
  "2 Series",
  "3 Series",
  "4 Series",
  "5 Series",
  "7 Series",
  "X1",
  "X3",
  "X5",
  "X7",
  "Z4",
  "i3",
  "i4",
  "i8"
],
    "Mercedes-Benz": [
  "A-Class",
  "C-Class",
  "E-Class",
  "S-Class",
  "GLA",
  "GLC",
  "GLE",
  "GLS",
  "G-Class",
  "AMG GT",
  "EQB",
  "EQC",
  "EQS"
],
    "Audi": [
  "A3",
  "A4",
  "A5",
  "A6",
  "A7",
  "A8",
  "Q3",
  "Q5",
  "Q7",
  "Q8",
  "e-tron",
  "R8",
  "TT"
],
    "Tesla": [
  "Model S",
  "Model 3",
  "Model X",
  "Model Y",
  "Cybertruck"
],
    "Land Rover": [
  "Discovery",
  "Range Rover",
  "Range Rover Sport",
  "Range Rover Velar",
  "Defender",
  "Evoque"
],
    "Porsche": [
  "911",
  "718 Cayman",
  "718 Boxster",
  "Panamera",
  "Macan",
  "Cayenne",
  "Taycan"
],
    "Jaguar": [
  "XE",
  "XF",
  "XJ",
  "E-PACE",
  "F-PACE",
  "I-PACE",
  "F-TYPE"
]
}
type CarMake = keyof typeof makes;

var classes: string[];


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
const luxury  = ref<string[] | null>([])

const setPrice = (value: number) => {
  price.value = value;
  togglePrice()
};

const togglePrice = () => {
  pricePopUp.value = !pricePopUp.value;
}

const setMake = (value: Make) =>{
  make.value = value;
  const mk: CarMake = value.name as CarMake; // Ensure valid keys
  classes = makes[mk]
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
        <div class="flex-box bg-pink-700 text-white font-extrabold text-xl px-2 h-[30px]" @click="toggleClass">{{class_}}</div>
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

        <button class="flex flex-col items-center justify-center"  @click="toggleGear">
          <i class="w-8 h-8 rounded-full fas fa-brush bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <span class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">
            {{ gear }}</span>
        </button>

        <button class="flex flex-col items-center justify-center"  @click="toggleLuxury">
          <i class="w-8 h-8 rounded-full fas fa-leaf bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <p class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">Luxury</p>
        </button>

        <button class="flex flex-col items-center justify-center" @click="toggleEngine">
          <i class="w-8 h-8 rounded-full fas fa-life-ring bg-pink-300 text-white text-sm p-2  flex items-center justify-center"></i>
          <p class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">Safety</p>
        </button>

      </div>


      </div>

    </div>

    <MakePopUp
        v-if="makePopUp"
        @choice="setMake"
        position="bottom"></MakePopUp>


    <PricePopUp
        v-if="pricePopUp"
        @close="pricePopUp = false"
        @save="setPrice"
        position="top"></PricePopUp>

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


    <ChipsPopUp
        v-if="luxuryPopUp"
        @choice="setLuxury"
        @close="luxuryPopUp = false"
        :options="shapes"></ChipsPopUp>

  </div>










</template>

<style scoped>

</style>