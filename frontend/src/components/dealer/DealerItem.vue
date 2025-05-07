<script setup lang="ts">
import {defineProps, withDefaults} from "vue";
import {DealerListDto} from "../../core/models/DealerListDto";


// Define props with defaults
withDefaults(defineProps<{ dealer?: DealerListDto }>(), {
  dealer: () => ({
    id: 1,
    coverUrl: "/car_plchldr1.jpg",
    avatarUrl: "/car_plchldr1.jpg",
    username: "username",
    name: "Sanaa",
    subtitle: "subtitle",
    location: "location",
    likeCount: 900,
  })
});
</script>


<template>
  <div class="relative group">
    <RouterLink :to="{ name: 'profile', params: {id: dealer.username} }">
      <div class="absolute inset-px shadow-lg bg-white dark:bg-gray-900  group-hover:bg-purple-50"></div>
      <div class="relative flex flex-col overflow-hidden">
        <div class="group flex-col inset-px shadow-md">


          <!-- Cover image-->
          <div class="overflow-hidden">
            <img class="w-full transition-transform duration-700  group-hover:scale-110" :src="dealer.coverUrl || '/src/assets/cover_placeholder.jpg'" alt="Dealer Cover Image"/>

          </div>
          <!--Divider-->
          <div class="w-full border-b-2 border-b-pink-700 "></div>
          <div class="flex flex-row w-full justify-center">
            <div class="z-10"></div>
            <div class="flex rounded-full ring-2 ring-pink-700 h-24 w-24 bg-gray-100 -translate-y-1/2  overflow-hidden">
              <img :src="dealer.avatarUrl || '/src/assets/user.jpeg'" alt="Dealer avatar">
            </div>
          </div>
          <div class="flex justify-center -translate-y-12 text-pink-500 font-bold text-2xl ">{{ dealer.name }}</div>
          <div class="flex  justify-center text-gray-500 text-lg -translate-y-8"> {{ dealer.subtitle }}</div>




          <div class="flex items-center justify-between py-2 px-6 bg-pink-100 ">


            <!-- Likes -->
            <div class="flex items-center gap-2">
              <i class="fas fa-map-marker text-pink-300"/>
              <span class="text-sm text-gray-700">{{ dealer.location }}</span>
            </div>

            <!-- Name -->
            <div class="flex items-center gap-2">
              <i class="fas fa-heart text-pink-300"/>
              <span class="text-sm text-gray-700">{{ dealer.likeCount }}</span>
            </div>

          </div>
        </div>




      </div>
    </RouterLink>
  </div>

</template>

<style scoped>

</style>