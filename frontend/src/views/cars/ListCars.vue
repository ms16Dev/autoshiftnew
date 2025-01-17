<script setup lang="ts">

import CarItem from "../../components/CarItem.vue";
import cars from "../../core/data/SampleData.ts";

defineOptions({
  name: 'List-Cars'
});




</script>

<template>
  <div class="flex md:flex-row-reverse flex-wrap w-full">
    <div class="w-full md:w-1/5 bg-gray-200 p-4 text-center text-gray-400">
      <CarItem/>

    </div>
    <div class="w-full md:w-3/5  p-4 text-center text-gray-200">

      <div class="flex-col grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 -px-4 gap-3">

        <car-item v-for="car in cars" :key="car.id" :car="car"/>

      </div>


    </div>
    <div class="w-full md:w-1/5 bg-gray-200 p-4 text-center text-gray-700 fixed sticky">
      <CarItem class="w-1\4"/>

    </div>
  </div></template>

<style scoped>

</style>