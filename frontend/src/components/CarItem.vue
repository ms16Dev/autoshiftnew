<script setup lang="ts">
import {computed, defineProps, withDefaults} from "vue";
import type { CarListDto } from '../core/models/CarListDto';
import {useStaticDataStore} from "../stores/staticDataStore.ts";
import {config} from "../../config.ts";
import {formatNumber, formatRelativeDate} from "../utils/dateUtils.ts";
import {useI18n} from "vue-i18n";
const { locale } = useI18n();

const staticData = useStaticDataStore();



// Define props with defaults
const props = withDefaults(defineProps<{ car?: CarListDto }>(), {
  car: () => ({
    id: 1,
    coverImage: "/car_plchldr1.jpg",
    price: "12,000",
    currency: "$",
    origin: "USA",
    make: "Toyota",
    makeUrl: "https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg",
    model: "E-320",
    year: "2021",
    seenCount: 1200,
    likeCount: 900,
    shareCount: 150,
    commentCount: 35,
    description: "Lorem Ipsum dolor lorem ipsum dolor",
    createdBy: "Car Care Ltd.",
    createdDate: "2 days ago",
  }),
});


const formattedCreatedDate = computed(() =>
    props.car
        ? formatRelativeDate(props.car.createdDate, locale.value as 'en' | 'ar')
        : ''
);

</script>


<template>
  <div class="relative group">
    <RouterLink :to="{ name: 'car-details', params: {id: car.id} }">
    <div class="absolute inset-px shadow-lg bg-white dark:bg-gray-900  group-hover:bg-purple-50"></div>
    <div class="relative flex flex-col overflow-hidden">
      <div class="group flex-col inset-px shadow-md">



<!-- Cover image-->
        <div class="overflow-hidden">
          <img class="w-full h-[200px] transition-transform duration-700 object-contain  group-hover:scale-110"
               :src="config.getImageUrl(car.coverImage)"
               alt=""/>

        </div>
<!--Divider-->
      <div class="w-full border-b-2 border-b-pink-700 "></div>
      <div class="flex flex-row w-full justify-between -translate-y-1/2">

        <div class="z-10 translate-y-1/2">
          <div class="flex-box bg-pink-700 text-white font-bold text-sm px-2 h-[20px]">{{ staticData.getLocalizedName(staticData.getCurrencyById(car.currency)) }} {{ formatNumber(car.price) }}</div>
          <p class="flex-box bg-pink-400 text-white text-sm font-bold px-2 h-[20px]">{{ staticData.getLocalizedName(staticData.getStatusById(car.origin)) }}</p>
        </div>
        <div class="absolute rounded-full ring-2 ring-pink-700 h-24 w-24 bg-gray-100 -translate-y-1/4 left-1/2 -translate-x-1/2 overflow-hidden">
          <img class="object-contain w-full h-full"
               :src="config.getImageUrl(car.makeUrl)"
               alt=""/>
        </div>




        <div class="flex flex-col translate-y-1/2">
        <div class="flex-box bg-pink-700 text-white font-bold text-sm px-2 h-[20px] ">{{staticData.getLocalizedName(staticData.getClassById(car.model))}}</div>
        <div class="w-fit bg-pink-400 text-white text-sm font-bold px-2 h-[20px] self-end">{{ car.year }}</div>
        </div>

      </div>

      <div class="flex flex-row xl:flex w-full justify-between px-3">

        <div class="text-lg text-gray-900 text-center truncate w-full pt-4">
          {{ car.description ? car.description : 'No description added' }}
        </div>


      </div>

        <div class="flex items-center justify-between py-2 px-6 bg-pink-100 ">
          <!-- Seen -->
          <div class="flex items-center gap-2">
            <i class="fas fa-eye text-pink-300"/>

            <span class="text-sm text-gray-700">{{ car.seenCount }}</span>
          </div>

          <!-- Likes -->
          <div class="flex items-center gap-2">
           <i class="fas fa-thumbs-up text-pink-300"/>
            <span class="text-sm text-gray-700">{{ car.likeCount }}</span>
          </div>

          <!-- Shares -->
          <div class="flex items-center gap-2">
            <i class="fas fa-share text-pink-300"/>

            <span class="text-sm text-gray-700">{{ car.shareCount }}</span>
          </div>

          <!-- Comments -->
          <div class="flex items-center gap-2">
            <i class="fas fa-comment text-pink-300"/>

            <span class="text-sm text-gray-700">{{ car.commentCount }}</span>
          </div>
        </div>
      </div>


      <div class="flex-col ">

        <div class="flex flex-row px-4 my-2 justify-between">
          <div class="flex text-pink-500 font-bold text-xs">{{ car.createdBy }}</div>
          <div class="flex  text-gray-500 text-xs"> {{ formattedCreatedDate }}</div>
        </div>

      </div>




    </div>
    </RouterLink>
  </div>

</template>

<style scoped>

</style>