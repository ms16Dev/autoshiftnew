<script setup lang="ts">
import { defineProps, withDefaults } from "vue";
import type { CarListDto } from '../core/models/CarListDto';




// Define props with defaults
withDefaults(defineProps<{ car?: CarListDto }>(), {
  car: () => ({
    id: 1,
    coverImage: "/car_plchldr1.jpg",
    price: "12,000",
    origin: "USA",
    make: "https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg",
    class: "E-320",
    year: "2021",
    mileage: "78,000Km",
    gear: "Automatic",
    type: "Hybrid",
    engine: "4 piston",
    seen: 1200,
    likes: 900,
    shares: 150,
    comments: 35,
    dealer: "Car Care Ltd.",
    date_created: "2 days ago",
  }),
});
</script>


<template>
  <div class="relative group">
    <RouterLink :to="{ name: 'car-details', params: {id: car.id} }">
    <div class="absolute inset-px shadow-lg bg-white dark:bg-gray-900  group-hover:bg-purple-50"></div>
    <div class="relative flex flex-col overflow-hidden">
      <div class="group flex-col inset-px shadow-md">



<!-- Cover image-->
        <div class="overflow-hidden">
          <img class="w-full transition-transform duration-700  group-hover:scale-110" :src="car.coverImage"/>

        </div>
<!--Devider-->
      <div class="w-full border-b-2 border-b-pink-700 "></div>
      <div class="flex flex-row w-full justify-between -translate-y-1/2">

        <div class="z-10">
          <div class="flex-box bg-pink-700 text-white font-extrabold text-xl px-2 h-[30px]">$ {{ car.price }}</div>
          <p class="w-fit bg-pink-400 text-white font-bold px-2 h-[30px]">{{ car.origin }}</p>
        </div>
        <div class="absolute rounded-full ring-2 ring-pink-700 h-24 w-24 bg-gray-100 -translate-y-1/4 left-1/2 -translate-x-1/2 overflow-hidden">
          <img src="https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg">
        </div>




        <div class="flex flex-col">
        <div class="flex-box bg-pink-700 text-white font-extrabold text-xl px-2 h-[30px] ">{{car.class}}</div>
        <div class="w-fit bg-pink-400 text-white font-bold px-2 h-[30px] self-end">{{ car.year }}</div>
        </div>

      </div>

      <div class="flex flex-row md:grid md:grid-cols-2 xl:flex w-full justify-between px-3">



        <div class="flex flex-col items-center justify-center">
          <i class="w-8 h-8 rounded-full fas fa-tachometer bg-pink-300 text-white text-sm p-2 h-[30px] flex items-center justify-center"></i>
          <p class="text-pink-500 text-xs font-bold text-center px-2 h-[30px] flex items-center justify-center">
            {{ car.mileage }}</p>
        </div>

        <div class="flex flex-col items-center justify-center">
          <i class="w-8 h-8 rounded-full fas fa-gears bg-pink-300 text-white text-sm p-2 h-[30px] flex items-center justify-center"></i>
          <p class="text-pink-500 text-xs font-bold text-center px-2 h-[30px] flex items-center justify-center">
            {{ car.gear }}</p>
        </div>

        <div class="flex flex-col items-center justify-center">
          <i class="w-8 h-8 rounded-full fas fa-gas-pump bg-pink-300 text-white text-sm p-2 h-[30px] flex items-center justify-center"></i>
          <p class="text-pink-500 text-xs font-bold text-center px-2 h-[30px] flex items-center justify-center">{{ car.type }}</p>
        </div>

        <div class="flex flex-col items-center justify-center">
          <i class="w-8 h-8 rounded-full fas fa-bolt bg-pink-300 text-white text-sm p-2 h-[30px] flex items-center justify-center"></i>
          <p class="text-pink-500 text-xs font-bold text-center px-2 h-[30px] flex items-center justify-center">{{car.engine}}</p>
        </div>



      </div>

        <div class="flex items-center justify-between py-2 px-6 bg-pink-100 ">
          <!-- Seen -->
          <div class="flex items-center space-x-2">
            <i class="fas fa-eye text-pink-300"/>

            <span class="text-sm text-gray-700">{{ car.seen }}</span>
          </div>

          <!-- Likes -->
          <div class="flex items-center space-x-2">
           <i class="fas fa-heart text-pink-300"/>
            <span class="text-sm text-gray-700">{{ car.likes }}</span>
          </div>

          <!-- Shares -->
          <div class="flex items-center space-x-2">
            <i class="fas fa-share text-pink-300"/>

            <span class="text-sm text-gray-700">{{ car.shares }}</span>
          </div>

          <!-- Comments -->
          <div class="flex items-center space-x-2">
            <i class="fas fa-comment text-pink-300"/>

            <span class="text-sm text-gray-700">{{ car.comments }}</span>
          </div>
        </div>
      </div>


      <div class="flex-col ">

        <div class="flex flex-row px-4 my-2 justify-between">
          <div class="flex text-pink-500 font-bold text-xs">{{ car.dealer }}</div>
          <div class="flex  text-gray-500 text-xs"> {{ car.date_created }}</div>
        </div>

      </div>




    </div>
    </RouterLink>
  </div>

</template>

<style scoped>

</style>