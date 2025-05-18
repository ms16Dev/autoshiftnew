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
import MultiImageLoader from "../MultiImageLoader.vue";
import {Car} from "../../types/Car.ts";
import axios from "axios";
import {useAuthStore} from "../../stores/auth.ts";
import IconButton from "../IconButton.vue";
import {useStaticDataStore} from "../../stores/staticDataStore.ts";
import apiService from "../../core/services/ApiService.ts";
import {City, DataItem} from "../../types/staticData.ts";
import {useI18n} from "vue-i18n";
import DescriptionPopUp from "./DescriptionPopUp.vue";
import router from "../../router";
import {formatNumber} from "../../utils/dateUtils.ts";
import {config} from "../../../config.ts";
const { t } = useI18n()

const staticData = useStaticDataStore()



const make = ref<Make | null>(null);
const formData1 = ref(new FormData());
const formData = ref(new FormData());
const classes = ref<DataItem[]>([])
const loading = ref(false);

const state = reactive({
  price: 0,
  currency: '',
  mileage: 0,
  year: 0,
  origin: {} as DataItem,
  gear: {} as DataItem,
  class: {} as DataItem,
  type: {} as DataItem,
  engine: {} as DataItem,
  shape: {} as DataItem,
  color: {} as DataItem,
  luxury: [] as DataItem[],
  safety: [] as DataItem[],
  location: {} as City,
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
  descriptionPopUp: false,
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

    // Extract only the `name_en` values
    // Save the names to classes.value
    classes.value = toRaw(response.data || [])


  } catch (error) {
    console.error("Error fetching classes:", error);
    classes.value = [];
  }
};


const toggleMake = () => {
  state.makePopUp = !state.makePopUp
}

onMounted(() => {
  make.value = staticData.makes[7];

});

const saveCar = async () => {
  loading.value = true
  console.log("Cover image  = = = = =",formData1.value)
  console.log("other images  = = = = =",formData.value)

  try {
    const response1 = await axios.post(config.endpoints.images, formData1.value, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    console.log("Upload success:", response1.data);
    const coverUrl = response1.data.urls[0]

    const response = await axios.post(config.endpoints.images, formData.value, {
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
      currency: state.currency,
      origin: state.origin.id,
      make: make.value?.id!!,
      makeUrl: make.value?.url!!,
      model: state.class.id,
      year: state.year,
      mileage: state.mileage,
      gear: state.gear.id,
      type: state.type.id,
      engine: state.engine.id,
      shape: state.shape.id,
      color: state.color.id,
      luxury: state.luxury.map(item => item.id),
      safety: state.safety.map(item => item.id),
      location: state.location.id,
      country: staticData.getCurrentCountry().id,
      sold: false,
      stars: 0,
      description: state.description,
      images: imageUrls,
      createdBy: authStore.userInfo?.name!!,
    };


    try {
      const response = await ApiService.post("/cars", car);
      console.log(response.data);
      await new Promise(resolve => setTimeout(resolve, 3000)); // 2-second pause

      loading.value = false
      await router.push('/cars');
    } catch (err) {
      const error = err as any;
      console.error(error.response?.data.errors || error);
      await new Promise(resolve => setTimeout(resolve, 3000)); // 2-second pause

      loading.value = false
    }


  } catch (err) {
    const error = err as any;
    console.error(error.response?.data.errors || error);
    await new Promise(resolve => setTimeout(resolve, 3000)); // 2-second pause

    loading.value = false

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
          <img :src="config.apiBaseUrl+`${make?.url } `" alt="carMake">
        </button>


        <div class="flex flex-col">
          <button class="flex-box bg-pink-700 text-white font-bold text-xl px-2 h-[30px]" @click="togglePopUp('pricePopUp')">
            {{ staticData.getLocalizedName(staticData.getCurrencyById(state.currency)) }} {{state.price > 0 ? formatNumber(state.price) : t('car_price')}}</button>
          <button class="w-fit bg-pink-400 text-white font-bold px-2 h-[30px]" @click="togglePopUp('originPopUp')">{{ staticData.getLocalizedName(state.origin) || t('car_status') }}</button>
        </div>



        <div class="flex flex-col">
        <button class="flex-box bg-pink-700 text-white font-bold text-xl px-2 h-[30px]" @click="togglePopUp('classPopUp')">{{ staticData.getLocalizedName(state.class) || t('car_class') }}</button>
        <button @click="togglePopUp('yearPopUp')" class="w-fit bg-pink-400 text-white font-bold px-2 h-[30px] self-end">{{state.year > 0 ? state.year : t('car_year')}}</button>
        </div>

      </div>

        <div class="grid grid-cols-4 w-full justify-between px-3 gap-2">
          <IconButton class="hover:bg-pink-100" :label="state.mileage > 0 ? formatNumber(state.mileage) : t('car_mileage')" icon="fas fa-tachometer" @click="togglePopUp('mileagePopUp')" />
          <IconButton class="hover:bg-pink-100" :label="staticData.getLocalizedName(state.gear) || t('car_gear')" icon="fas fa-gears" @click="togglePopUp('gearPopUp')" />
          <IconButton class="hover:bg-pink-100" :label="staticData.getLocalizedName(state.type) || t('car_fuel')" icon="fas fa-gas-pump" @click="togglePopUp('typePopUp')" />
          <IconButton class="hover:bg-pink-100" :label="staticData.getLocalizedName(state.engine) || t('car_engine')" icon="fas fa-bolt" @click="togglePopUp('enginePopUp')" />
          <IconButton class="hover:bg-pink-100" :label="staticData.getLocalizedName(state.shape) || t('car_shape')" icon="fas fa-car" @click="togglePopUp('shapePopUp')" />
          <IconButton class="hover:bg-pink-100" :label="staticData.getLocalizedName(state.color) || t('car_color')" icon="fas fa-brush" @click="togglePopUp('colorPopUp')" />
          <IconButton class="hover:bg-pink-100" :label="state.luxury.length > 0 ? t('car_luxury') + ` ` + state.luxury.length : t('car_luxury') " icon="fas fa-leaf" @click="togglePopUp('luxuryPopUp')" />
          <IconButton class="hover:bg-pink-100" :label="state.safety.length > 0 ?  t('car_safety')  + ` ` + state.safety.length : t('car_safety')" icon="fas fa-life-ring" @click="togglePopUp('safetyPopUp')" />
          <IconButton class="hover:bg-pink-100" :label="staticData.getLocalizedName(state.location) || t('car_location') " icon="fas fa-map-marker" @click="togglePopUp('locationPopUp')" />
          <IconButton class="hover:bg-pink-100" :label="t('description')" icon="fas fa-info" @click="togglePopUp('descriptionPopUp')" />
        </div>

        <div class="w-full border-b-2 border-b-pink-700 mt-4 "></div>
        <div class="flex row-auto w-full  justify-center">
          <h4 class="w-fit px-1 text-pink-500 text-sm bg-white rounded-full border-2 border-pink-700 -translate-y-1/2 ">{{ t('add_more_images')}}</h4>


        </div>




        <MultiImageLoader @save="(value) => formData = value"
        />

        <div class="w-full border-b-2 border-b-pink-700 mt-2 "></div>


        <div class="flex items-start justify-end  m-2">

          <!-- Save Post Button -->
          <button
              @click="saveCar"
              class="py-1 px-3 text-lg font-medium bg-pink-600 text-white rounded-md shadow hover:bg-pink-700 focus:outline-none"
          >
            {{t('save_car')}}
          </button>
        </div>


      </div>

    </div>

    <MakePopUp
        v-if="state.makePopUp"
        @choice="setMake"
        :makes="staticData.data.makes as Make[]"
        position="bottom"></MakePopUp>


    <PricePopUp
        v-if="state.pricePopUp"
        @close="state.pricePopUp = false"
        @save="(value) => handleSave('price')(value)"
        @currency="(value) => handleSave('currency')(value)"
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
        :options="staticData.data.status"
        :title="t('car_status_title')"></OptionsPopUp>

    <OptionsPopUp
        v-if="state.gearPopUp"
        @choice="(value) => handleSave('gear')(value)"
        @close="state.gearPopUp = false"
        :options="staticData.data.gears"
        :title="t('car_gear_title')"></OptionsPopUp>

    <OptionsPopUp
        v-if="state.classPopUp"
        @choice="(value) => handleSave('class')(value)"
        @close="state.classPopUp = false"
        :options="classes"
        :title="t('car_class_title')"></OptionsPopUp>


    <OptionsPopUp
        v-if="state.typePopUp"
        @choice="(value) => handleSave('type')(value)"
        @close="state.typePopUp = false"
        :options="staticData.data.fuel"
        :title="t('car_fuel_title')"></OptionsPopUp>


    <OptionsPopUp
        v-if="state.enginePopUp"
        @choice="(value) => handleSave('engine')(value)"
        @close="state.enginePopUp = false"
        :options="staticData.data.engines"
        :title="t('car_engine_title')"></OptionsPopUp>



    <OptionsPopUp
        v-if="state.shapePopUp"
        @choice="(value) => handleSave('shape')(value)"
        @close="state.shapePopUp = false"
        :options="staticData.data.shapes"
        :title="t('car_body_title')"></OptionsPopUp>



    <OptionsPopUp
        v-if="state.colorPopUp"
        @choice="(value) => handleSave('color')(value)"
        @close="state.colorPopUp = false"
        :options="staticData.data.colors"
        :title="t('car_color_title')"></OptionsPopUp>



    <ChipsPopUp
        v-if="state.luxuryPopUp"
        @choice="(value) => handleSave('luxury')(value)"
        @close="state.luxuryPopUp = false"
        :options="staticData.data.luxury"
        :title="t('car_luxury_title')"
        :checked="state.luxury"></ChipsPopUp>

    <ChipsPopUp
        v-if="state.safetyPopUp"
        @choice="(value) => handleSave('safety')(value)"
        @close="state.safetyPopUp = false"
        :options="staticData.data.safety"
        :title="t('car_safety_title')"
        :checked="state.safety"></ChipsPopUp>

     <OptionsPopUp
        v-if="state.locationPopUp"
        @choice="(value) => handleSave('location')(value)"
        @close="state.locationPopUp = false"
        :options="staticData.getCitiesByCountry()"
        :title="t('car_location_title')"></OptionsPopUp>

    <DescriptionPopUp
        v-if="state.descriptionPopUp"
        @save="(value) => handleSave('description')(value)"
        @close="state.descriptionPopUp = false"
        :value="state.description"
        ></DescriptionPopUp>

    <div v-if="loading"
         class="absolute top-0 text-white z-20 w-full h-full bg-pink-700 items-center">
      <div class="flex flex-col items-center justify-center h-full">
      <img src="../../assets/imgs/tires.png" alt="Loading" class="w-24 h-24 animate-pulse">
        <p>{{t('saving_post')}}</p>
      </div>

    </div>


  </div>
</template>

<style scoped>

</style>