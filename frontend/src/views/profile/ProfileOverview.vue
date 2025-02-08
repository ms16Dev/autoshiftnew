<template>
  <div class="flex md:flex-row flex-wrap w-full bg-gray-200">
    <!-- Sidebar - Dealer Card -->
    <div class="w-full lg:w-1/5 p-4 text-center text-gray-400">
      <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
        <div class="group flex-col inset-px shadow-md">


          <!-- Cover image-->
          <div class="overflow-hidden">
            <img class="w-full transition-transform duration-700  group-hover:scale-110" src="/car_plchldr1.jpg"/>

          </div>
          <!--Devider-->
          <div class="w-full border-b-2 border-b-pink-700 "></div>
          <div class="flex flex-row w-full justify-center">
            <div class="z-10"></div>
            <div class="flex rounded-full ring-2 ring-pink-700 h-24 w-24 bg-gray-100 -translate-y-1/2  overflow-hidden">
              <img src="https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg">
            </div>
          </div>
          <div class="flex justify-center -translate-y-12 text-pink-500 font-bold text-2xl ">fdsfdsfsfs</div>
          <div class="flex  justify-center text-gray-500 text-lg -translate-y-8"> fasdfafsafa</div>




          <div class="flex items-center justify-between py-2 px-6 bg-pink-100 ">


            <!-- Likes -->
            <div class="flex items-center space-x-2">
              <i class="fas fa-map-marker text-pink-300"/>
              <span class="text-sm text-gray-700">afafafas</span>
            </div>

            <!-- Name -->
            <div class="flex items-center space-x-2">
              <i class="fas fa-heart text-pink-300"/>
              <span class="text-sm text-gray-700">faffa</span>
            </div>

          </div>
        </div>




      </div>
    </div>

    <!-- Main Content Area -->
    <div class="w-full lg:w-3/5 p-4 text-center text-gray-200">
      <div class="w-full flex flex-col items-center justify-center text-gray-900 bg-gray-50 pattern-bg p-6 rounded-lg shadow-md">


        <div class="lg:flex space-x-4 border-b-2 pb-2">
          <button
              :class="{'border-b-2 border-pink-500': activeTab === 1}"
              class="text-xl font-semibold py-2 px-4 cursor-pointer"
              @click="activeTab = 1">
            About
          </button>
          <button
              :class="{'border-b-2 border-pink-500': activeTab === 2}"
              class="text-xl font-semibold py-2 px-4 cursor-pointer"
              @click="activeTab = 2">
            Contact
          </button>
          <button
              :class="{'border-b-2 border-pink-500': activeTab === 3}"
              class="text-xl font-semibold py-2 px-4 cursor-pointer"
              @click="activeTab = 3">
            Cars
          </button>
        </div>

        <div class="w-full mt-4">
          <!-- Tab Content -->
          <div v-show="activeTab === 1" class="w-full text-gray-600">
            <h3 class="text-xl font-semibold">Dealer Requirements</h3>
            <ul class="list-disc pl-6 text-start">
              <li>Valid ID</li>
              <li>Business License</li>
              <li>Minimum of 5 cars</li>
            </ul>

            <!-- Ensure the parent div of the button takes full width -->
            <div class="w-full flex justify-end">
              <button type="submit" class="flex-row px-3 bg-pink-500 hover:bg-pink-700 text-white font-bold py-2 rounded-lg transition shadow-md mt-3">
                <i class="fas fa-thumbs-up text-white"></i>
                Like
              </button>
            </div>

          </div>


          <div v-show="activeTab === 2" class="text-gray-600">
            <h3 class="text-2xl font-semibold">Dealer Benefits</h3>
            <ul class="list-disc pl-6">
              <li>Free car listing space</li>
              <li>Access to premium customer leads</li>
              <li>Flexible financing options</li>
            </ul>
          </div>
          <div v-show="activeTab === 3" class="text-gray-600">
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
        </div>
      </div>

    </div>

    <!-- Fixed Sidebar - Ad Item -->
    <div class="w-full lg:w-1/5 p-4 text-center text-gray-700 fixed sticky">
      <AdItem/>
    </div>
  </div>
</template>

<script setup lang="ts">
import AdItem from "../../components/AdItem.vue";
import {computed, ref} from "vue";
import cars from "../../core/data/CarsData.ts";
import TablePagination from "../../components/TablePagenition.vue";
import CarItem from "../../components/CarItem.vue";

defineOptions({
  name: 'join-dealer'
});

const activeTab = ref(1); // Active tab default is 'Requirements'




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

<style scoped>
/* Add custom styles if needed */
</style>
