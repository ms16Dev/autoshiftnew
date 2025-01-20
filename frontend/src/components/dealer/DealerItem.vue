<script setup lang="ts">
import { defineProps, withDefaults } from "vue";
import DealerListDto from "../../core/models/DealerListDto.ts";



// Define props with defaults
withDefaults(defineProps<{
  dealer: DealerListDto;
}>(), {
  dealer: {
    id: 1,
    coverImage: "/car_plchldr1.jpg",
    logo: "/car_plchldr1.jpg",
    name: "kdjsdldfj",
    address: "dsadasdasd",
    title: "dasdsdasd",
    subtitle: "dsadsadsad",
    likes: 900,
    date_joined: "2 days ago",
  }
});
</script>


<template>
  <div class="relative group">
    <RouterLink :to="{ name: 'Home', params: {id: dealer.id} }">
    <div class="absolute inset-px shadow-lg bg-white dark:bg-gray-900  group-hover:bg-pink-50"></div>
    <div class="relative flex flex-col overflow-hidden rounded-lg">
      <div class="group flex-col inset-px shadow-md">



<!-- Cover image-->
        <div class="overflow-hidden">
          <img class="w-full transition-transform duration-700  group-hover:scale-110" :src="dealer.coverImage"/>

        </div>
<!--Devider-->
      <div class="w-full border-b-2 border-b-pink-700 "></div>
        <div class="flex flex-row w-full justify-between ">

          <div class="z-10">
          </div>
          <div class="absolute rounded-full ring-2 ring-pink-700 h-24 w-24 bg-gray-100 -translate-y-1/2 start-1/2 -translate-x-1/2 overflow-hidden">
            <img src="https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg">
          </div>




          <div class="flex flex-col">
          </div>

        </div>



        <div class="flex items-center justify-between py-2 px-6 bg-pink-100 ">


          <!-- Likes -->
          <div class="flex items-center space-x-2">
           <i class="fas fa-shop text-pink-300"/>
            <span class="text-sm text-gray-700">{{ dealer.name }}</span>
          </div>

          <!-- Name -->
          <div class="flex items-center space-x-2">
            <i class="fas fa-heart text-pink-300"/>
            <span class="text-sm text-gray-700">{{ dealer.likes }}</span>
          </div>

        </div>
      </div>


      <div class="flex-col ">

        <div class="flex flex-row px-4 my-2 justify-between">
          <div class="flex text-pink-500 font-bold text-xs">{{ dealer.address }}</div>
          <div class="flex  text-gray-500 text-xs"> {{ dealer.date_joined }}</div>
        </div>

      </div>




    </div>
    </RouterLink>
  </div>

</template>

<style scoped>

</style>