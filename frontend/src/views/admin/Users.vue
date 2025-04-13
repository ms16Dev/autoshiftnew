<script setup lang="ts">

import IconButton from "../../components/IconButton.vue";
</script>

<template>
  <div class="flex justify-center items-center">
    <div class="w-full  p-4 text-center text-gray-400">
      <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
        <div class="group flex-col inset-px shadow-md">


          <!--Devider-->
          <div class="w-full border-b-2 border-b-pink-700 "></div>
          <div class="flex justify-between items-center bg-gray-100">

          <div class="flex px-4  text-pink-500 font-bold text-2xl ">Users</div>
          <div class="flex p-4  text-pink-500 font-bold text-2xl ">
            <IconButton icon="fas fa-plus" label="Add" class="hover:bg-pink-100"/>
            <IconButton icon="fas fa-x" label="Delete" class="hover:bg-pink-100"/>
            <IconButton icon="fas fa-pen" label="Edit" class="hover:bg-pink-100"/>

          </div>
          </div>
          <!--Devider-->

          <div class="w-full border-b-2 border-b-pink-700 "></div>

          <!--User Item -->

          <div
              class="flex items-center gap-3 p-2 cursor-pointer hover:bg-gray-100 rounded-lg transition"
          >
            <!-- User Avatar -->
            <img src="../../assets/asl-pink.png" alt="User Avatar" class="w-16 h-16 rounded-full object-cover" />

            <div class="flex flex-col items-start">
              <!-- User Name -->
              <span class="text-pink-500 font-lg font-extrabold">Username</span>
              <!-- Date -->
              <span class="text-gray-500 text-sm ">Date</span>
            </div>

          </div>
          <div
              class="flex items-center gap-3 p-2 cursor-pointer hover:bg-gray-100 rounded-lg transition"
          >
            <!-- User Avatar -->
            <img src="../../assets/asl-pink.png" alt="User Avatar" class="w-16 h-16 rounded-full object-cover" />

            <div class="flex flex-col items-start">
              <!-- User Name -->
              <span class="text-pink-500 font-lg font-extrabold">Username</span>
              <!-- Date -->
              <span class="text-gray-500 text-sm ">Date</span>
            </div>

          </div>
          <div
              class="flex items-center gap-3 p-2 cursor-pointer hover:bg-gray-100 rounded-lg transition"
          >
            <!-- User Avatar -->
            <img src="../../assets/asl-pink.png" alt="User Avatar" class="w-16 h-16 rounded-full object-cover" />

            <div class="flex flex-col items-start">
              <!-- User Name -->
              <span class="text-pink-500 font-lg font-extrabold">Username</span>
              <!-- Date -->
              <span class="text-gray-500 text-sm ">Date</span>
            </div>

          </div>
          <div
              class="flex items-center gap-3 p-2 cursor-pointer hover:bg-gray-100 rounded-lg transition"
          >
            <!-- User Avatar -->
            <img src="../../assets/asl-pink.png" alt="User Avatar" class="w-16 h-16 rounded-full object-cover" />

            <div class="flex flex-col items-start">
              <!-- User Name -->
              <span class="text-pink-500 font-lg font-extrabold">Username</span>
              <!-- Date -->
              <span class="text-gray-500 text-sm ">Date</span>
            </div>

          </div>
          <div
              class="flex items-center gap-3 p-2 cursor-pointer hover:bg-gray-100 rounded-lg transition"
          >
            <!-- User Avatar -->
            <img src="../../assets/asl-pink.png" alt="User Avatar" class="w-16 h-16 rounded-full object-cover" />

            <div class="flex flex-col items-start">
              <!-- User Name -->
              <span class="text-pink-500 font-lg font-extrabold">Username</span>
              <!-- Date -->
              <span class="text-gray-500 text-sm ">Date</span>
            </div>

          </div>
          <div
              class="flex items-center gap-3 p-2 cursor-pointer hover:bg-gray-100 rounded-lg transition"
          >
            <!-- User Avatar -->
            <img src="../../assets/asl-pink.png" alt="User Avatar" class="w-16 h-16 rounded-full object-cover" />

            <div class="flex flex-col items-start">
              <!-- User Name -->
              <span class="text-pink-500 font-lg font-extrabold">Username</span>
              <!-- Date -->
              <span class="text-gray-500 text-sm ">Date</span>
            </div>

          </div>



        </div>


      </div>
    </div>

  </div>
</template>

<style scoped>

</style>