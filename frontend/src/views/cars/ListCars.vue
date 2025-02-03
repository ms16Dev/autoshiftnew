<script setup lang="ts">

import CarItem from "../../components/CarItem.vue";
import cars from "../../core/data/CarsData.ts";
import {computed, ref} from "vue";
import TablePagination from "../../components/TablePagenition.vue";
import AdItem from "../../components/AdItem.vue";

defineOptions({
  name: 'List-Cars'
});



const currentPage = ref(1);  // Initialize at page 1
const itemsPerPage = ref(15); // Number of questions to show per page

const totalPages = computed(() => {
  return Math.ceil(cars.length / itemsPerPage.value);
});

// Compute the paginated questions to display based on the current page
const paginatedCars = computed(() => {
  const startIndex = (currentPage.value - 1) * itemsPerPage.value;
  const endIndex = startIndex + itemsPerPage.value;
  return cars.slice(startIndex, endIndex);
});

const handlePageChange = (page: number) => {


  currentPage.value = page;  // Update current page
};




</script>

<template>
  <div class="flex md:flex-row-reverse flex-wrap w-full bg-gray-200">
    <div class="w-full md:w-1/5 p-4 text-center text-gray-400">
      <AdItem/>

    </div>
    <div class="w-full md:w-3/5  p-4 text-center text-gray-200">

      <div class="flex-col grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 -px-4 gap-3">

        <car-item v-for="car in paginatedCars" :key="car.id" :car="car"/>



      </div>

      <TablePagination
          :current-page="currentPage"
          :per-page="itemsPerPage"
          :total="cars.length"
          :total-pages="totalPages"
          @page-change="handlePageChange"
      ></TablePagination>


    </div>

    <div class="w-full md:w-1/5  p-4 text-center text-gray-700 fixed sticky">
      <AdItem/>

    </div>
  </div></template>

<style scoped>

</style>