<template>
  <div class="flex md:flex-row flex-wrap w-full bg-gray-200">
    <!-- Sidebar - Dealer Card -->
    <div class="w-full lg:w-1/5 p-4 text-center text-gray-400">
      <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
        <div class="group flex-col inset-px shadow-md">

          <div class="w-full mt-4 ">
            <!-- Tab Content -->
            <div v-show="activeTab === 1" class="w-full text-gray-600">
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
              <button type="submit" class="flex-row px-1 bg-pink-500 hover:bg-pink-700 text-white py-1 rounded-md transition shadow-md -translate-y-6 ">
                <i class="fas fa-thumbs-up text-white"></i>
                Like
              </button>
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

              </div>

            </div>
            <div v-show="activeTab === 4" class="text-gray-600">
              <div class="flex-col grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 -px-4 gap-3">

              </div>

            </div>
            <div v-show="activeTab === 5" class="text-gray-600">
              <div class="flex-col grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 -px-4 gap-3">

              </div>

            </div>
          </div>


          <!-- Profile Tabs -->
          <div class="lg:flex space-x-1 border-b-2 pb-2 bg-pink-100 justify-between">
            <button
                :class="{'border-b-2 border-pink-500': activeTab === 1}"
                class="text-xl font-semibold py-2 px-4 cursor-pointer"
                @click="activeTab = 1">
                <i class="fas fa-user"
                 :class="{'text-pink-500': activeTab === 1}"

                />
            </button>
            <button
                :class="{'border-b-2 border-pink-500': activeTab === 2}"
                class="text-xl font-semibold py-2 px-4 cursor-pointer"
                @click="activeTab = 2">
                <i class="fas fa-info"
                   :class="{'text-pink-500': activeTab === 2}"

                />
            </button>
            <button
                :class="{'border-b-2 border-pink-500': activeTab === 3}"
                class="text-xl font-semibold py-2 px-4 cursor-pointer"
                @click="activeTab = 3">
                <i class="fas fa-envelope"
                   :class="{'text-pink-500': activeTab === 3}"

                />
            </button>
            <button
                :class="{'border-b-2 border-pink-500': activeTab === 4}"
                class="text-xl font-semibold py-2 px-4 cursor-pointer"
                @click="activeTab = 4">
                <i class="fas fa-star"
                   :class="{'text-pink-500': activeTab === 4}"

                />
            </button>
            <button
                :class="{'border-b-2 border-pink-500': activeTab === 5}"
                class="text-xl font-semibold py-2 px-4 cursor-pointer"
                @click="activeTab = 5">
                <i class="fas fa-chart-line"
                   :class="{'text-pink-500': activeTab === 5}"

                />
            </button>
          </div>





        </div>




      </div>
    </div>

    <!-- Main Content Area -->
    <div class="w-full lg:w-3/5 p-4 text-gray-400">

     <p class="text-lg font-extrabold "> Recent Activity</p>


    </div>

    <!-- Fixed Sidebar - Ad Item -->
    <div class="w-full lg:w-1/5 p-4 text-center text-gray-700 fixed sticky">
      <AdItem/>
    </div>
  </div>
</template>

<script setup lang="ts">
import AdItem from "../../components/AdItem.vue";

import {ref} from "vue";

defineOptions({
  name: 'join-dealer'
});

const activeTab = ref(1);






</script>

<style scoped>
</style>
