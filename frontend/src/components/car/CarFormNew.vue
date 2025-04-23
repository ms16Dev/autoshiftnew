<script setup lang="ts">

import ImageLoader from '../ImageLoader.vue';
import {onMounted, reactive, ref, toRaw} from "vue";
import PricePopUp from "./PricePopUp.vue";
import MakePopUp, {Make} from "./MakePopUp.vue";
import MileagePopUp from "./MileagePopUp.vue";
import YearPopUp from "./YearPopUp.vue"
import OptionsPopUp from "./OptionsPopUp.vue";
import ChipsPopUp from "./ChipsPopUp.vue";
import ApiService from "../../core/services/ApiService.ts";
import apiService from "../../core/services/ApiService.ts";
import MultiImageLoader from "../MultiImageLoader.vue";
import {Car} from "../../types/Car.ts";
import axios from "axios";
import {useAuthStore} from "../../stores/auth.ts";
import IconButton from "../IconButton.vue";

const formatNumber = (number: number): string => {
  return new Intl.NumberFormat('en-US').format(number);
};


const classes = ref<RefOption[]>([])
const make = ref<Make | null>(null);
const formData1 = ref(new FormData());
const formData = ref(new FormData());


const state = reactive({
  price: 1500,
  mileage: 121000,
  year: 2018,
  origin: 'Status',
  gear: 'Gear',
  class: 'class',
  type: 'Fuel',
  engine: 'Engine',
  shape: 'Shape',
  color: 'Color',
  luxury: [] as string[],
  safety: [] as string[],
  location: [] as string[],
  description: '',
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
  getClasses(make.value.id)
  toggleMake();
}

const getClasses = async (id: string) => {
  try {
    const response = await apiService.get1(`/ref-data/makes/${id}/classes`);

    const rawData = toRaw(response.data || []);

    // Extract only the `name_en` values
    // Save the names to classes.value
    classes.value = rawData


  } catch (error) {
    console.error("Error fetching classes:", error);
    classes.value = [];
  }
};


const toggleMake = () => {
  state.makePopUp = !state.makePopUp
}


const fetchMakes = async () => {
  try {
    const { data } = await ApiService.get1("/ref-data/makes"); // Use await with the async function

    makes.value = data

    // Initialize `make` with the first element of the array
    if (Array.isArray(data) && data.length > 0) {
      make.value = data[0];
      await getClasses(make.value?.id!!)
    }

  } catch (err) {
    // Handle error response
    const error = err as any; // Explicitly type the error
    console.error(error.response?.data.errors || error);  // You can handle error messages here
  }
};

export interface RefOption {
  name_en: string;
  name_ar: string;
}

const fetchRefOptions = async (endpoint: string): Promise<RefOption[]> => {
  try {
    const { data } = await ApiService.get1(`/ref-data/${endpoint}`);
    return data;
  } catch (err) {
    const error = err as any;
    console.error(`Error fetching ${endpoint}:`, error.response?.data.errors || error);
    return [];
  }
};


const gears = ref<RefOption[]>([]);
const fuel = ref<RefOption[]>([]);
const engines = ref<RefOption[]>([]);
const shapes = ref<RefOption[]>([]);
const colors = ref<RefOption[]>([]);
const luxury = ref<RefOption[]>([]);
const safety = ref<RefOption[]>([]);
const cities = ref<RefOption[]>([]);


onMounted(async () => {
  await fetchMakes();
  gears.value = await fetchRefOptions("gears");
  fuel.value = await fetchRefOptions("fuel");
  engines.value = await fetchRefOptions("engines");
  shapes.value = await fetchRefOptions("shapes");
  colors.value = await fetchRefOptions("colors");
  luxury.value = await fetchRefOptions("luxury");
  safety.value = await fetchRefOptions("safety");
  cities.value = await fetchRefOptions("countries/680678eede029f39388c0f7f/cities");
});

// Call the fetchMakes function
const makes = ref<Make[]>([]); // Declare `makes` as a ref to hold the array of makes

const saveCar = async () => {
  console.log("Cover image  = = = = =",formData1.value)
  console.log("other images  = = = = =",formData.value)

  try {
    const response1 = await axios.post('http://localhost:8080/media/image', formData1.value, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    console.log("Upload success:", response1.data);
    const coverUrl = response1.data.urls[0]

    const response = await axios.post('http://localhost:8080/media/image', formData.value, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    console.log("Upload success:", response.data);
    const imageUrls = response.data.urls

    const authStore = useAuthStore();


    const car: Car = {
      coverImage: coverUrl,
      price: state.price,
      origin: state.origin,
      make: make.value?.name_en!!,
      makeUrl: make.value?.url!!,
      model: state.class,
      year: state.year,
      mileage: state.mileage,
      gear: state.gear,
      type: state.type,
      engine: state.engine,
      shape: state.shape,
      color: state.color,
      luxury: state.luxury,
      safety: state.safety,
      location: state.location,
      sold: false,
      stars: 0,
      description: state.description,
      images: imageUrls,
      createdBy: authStore.userInfo?.name!!
    };


    try {
      const response = await ApiService.post("/cars", car);
      console.log(response.data);
    } catch (err) {
      const error = err as any;
      console.error(error.response?.data.errors || error);
    }


  } catch (err) {
    const error = err as any;
    console.error(error.response?.data.errors || error);
  }
}


</script>

<template>
  <div class="relative group w-[400px] ">
    <div class="absolute inset-px shadow-lg bg-white dark:bg-gray-900  "></div>
    <div class="relative flex flex-col overflow-hidden rounded-lg">
      <div class="group flex-col inset-px shadow-md">

<!-- Cover image-->
        <div class="overflow-hidden">
          <image-loader @file-selected="(value) => formData1 = value"  class="w-full"/>

        </div>
<!--Divider-->
      <div class="w-full border-b-2 border-b-pink-700 "></div>
      <div class="flex flex-row w-full justify-between -translate-y-1/2">

        <button  @click="toggleMake" class="absolute rounded-full ring-2 ring-pink-700 h-24 w-24 bg-gray-100 -translate-y-1/4 left-1/2 -translate-x-1/2 overflow-hidden">
          <img :src="`http://localhost:8080${make?.url } `">
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

        <div class="grid grid-cols-4 w-full justify-between px-3 gap-2">
          <IconButton class="hover:bg-pink-100" :label="formatNumber(state.mileage ?? 0)" icon="fas fa-tachometer" @click="togglePopUp('mileagePopUp')" />
          <IconButton class="hover:bg-pink-100" :label="state.gear" icon="fas fa-gears" @click="togglePopUp('gearPopUp')" />
          <IconButton class="hover:bg-pink-100" :label="state.type" icon="fas fa-gas-pump" @click="togglePopUp('typePopUp')" />
          <IconButton class="hover:bg-pink-100" :label="state.engine" icon="fas fa-bolt" @click="togglePopUp('enginePopUp')" />
          <IconButton class="hover:bg-pink-100" :label="state.shape" icon="fas fa-car" @click="togglePopUp('shapePopUp')" />
          <IconButton class="hover:bg-pink-100" :label="state.color" icon="fas fa-brush" @click="togglePopUp('colorPopUp')" />
          <IconButton class="hover:bg-pink-100" label="Luxury" icon="fas fa-leaf" @click="togglePopUp('luxuryPopUp')" />
          <IconButton class="hover:bg-pink-100" label="Safety" icon="fas fa-life-ring" @click="togglePopUp('safetyPopUp')" />
          <IconButton class="hover:bg-pink-100" label="Location" icon="fas fa-map-marker" @click="togglePopUp('locationPopUp')" />
          <IconButton class="hover:bg-pink-100" label="Description" icon="fas fa-info" @click="togglePopUp('locationPopUp')" />
        </div>

        <div class="w-full border-b-2 border-b-pink-700 mt-4 "></div>
        <div class="flex row-auto w-full  justify-center">
          <h4 class="w-fit px-1 text-pink-500 text-sm bg-white rounded-full border-2 border-pink-700 -translate-y-1/2 ">Add more images</h4>


        </div>




        <MultiImageLoader @save="(value) => formData = value"
        />

        <div class="w-full border-b-2 border-b-pink-700 mt-2 "></div>


        <div class="flex items-start justify-end  m-2">
          <!-- Location Button -->


          <!-- Save Post Button -->
          <button
              @click="saveCar"
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
        :options="gears"
        :title="'Status'"></OptionsPopUp>

    <OptionsPopUp
        v-if="state.gearPopUp"
        @choice="(value) => handleSave('gear')(value)"
        @close="state.gearPopUp = false"
        :options="gears"
        :title="'Gear type'"></OptionsPopUp>

    <OptionsPopUp
        v-if="state.classPopUp"
        @choice="(value) => handleSave('class')(value)"
        @close="state.classPopUp = false"
        :options="classes"
        :title="'Class / Model'"></OptionsPopUp>


    <OptionsPopUp
        v-if="state.typePopUp"
        @choice="(value) => handleSave('type')(value)"
        @close="state.typePopUp = false"
        :options="fuel"
        :title="'Fuel type'"></OptionsPopUp>


    <OptionsPopUp
        v-if="state.enginePopUp"
        @choice="(value) => handleSave('engine')(value)"
        @close="state.enginePopUp = false"
        :options="engines"
        :title="'Engine size'"></OptionsPopUp>



    <OptionsPopUp
        v-if="state.shapePopUp"
        @choice="(value) => handleSave('shape')(value)"
        @close="state.shapePopUp = false"
        :options="shapes"
        :title="'Body shape'"></OptionsPopUp>



    <OptionsPopUp
        v-if="state.colorPopUp"
        @choice="(value) => handleSave('color')(value)"
        @close="state.colorPopUp = false"
        :options="colors"
        :title="'Color'"></OptionsPopUp>



    <ChipsPopUp
        v-if="state.luxuryPopUp"
        @choice="(value) => handleSave('luxury')(value)"
        @close="state.luxuryPopUp = false"
        :options="luxury"
        title="List luxury features"
        :checked="state.luxury"></ChipsPopUp>

    <ChipsPopUp
        v-if="state.safetyPopUp"
        @choice="(value) => handleSave('safety')(value)"
        @close="state.safetyPopUp = false"
        :options="safety"
        title="List safety features"
        :checked="state.safety"></ChipsPopUp>

     <OptionsPopUp
        v-if="state.locationPopUp"
        @choice="(value) => handleSave('location')(value)"
        @close="state.locationPopUp = false"
        :options="cities"
        :title="'location'"></OptionsPopUp>


  </div>
</template>

<style scoped>

</style>