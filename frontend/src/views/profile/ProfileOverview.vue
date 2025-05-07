<template>


  <div class="flex lg:flex-row flex-wrap w-full bg-gray-200">


    <!-- Fixed Sidebar - Ad Item -->
    <div class="w-full lg:w-1/5 p-4 text-center text-gray-700 sticky">
      <AdItem/>
    </div>

    <!-- Main Content Area -->
    <div class="flex flex-col items-center w-full lg:w-3/5 p-4 text-gray-400">

      <!-- Sidebar - Dealer Card -->
      <div class="flex xl:max-w-lg p-4 text-center text-gray-400">
        <div class="flex flex-col overflow-hidden rounded-lg bg-white">
          <div class="group flex-col inset-px shadow-md">

            <!-- Cover image-->
            <div class="overflow-hidden">
              <img class="w-full transition-transform duration-700  group-hover:scale-110" :src="dealer?.coverUrl || '/src/assets/cover_placeholder.jpg'" alt="Cover Image"/>

            </div>
            <!--Divider-->
            <div class="w-full border-b-2 border-b-pink-700 "></div>
            <div class="flex flex-row w-full justify-center">
              <div class="z-10"></div>
              <div class="flex rounded-full ring-2 ring-pink-700 h-24 w-24 bg-gray-100 -translate-y-1/2  overflow-hidden">
                <img :src="dealer?.avatarUrl || '/src/assets/user.jpeg'" alt="Avatar">
              </div>
            </div>
            <div class="flex justify-center -translate-y-12 text-pink-500 font-bold text-2xl ">{{ dealer?.name }}</div>
            <div class="flex  justify-center text-gray-500 text-lg -translate-y-8"> {{ dealer?.subtitle }}</div>
            <div class="w-full mt-4 ">
              <!-- Tab Content -->
              <div v-show="activeTab === 1" class="w-full text-gray-600  pb-4">

                <p class="font-semibold">{{ dealer?.likeCount }} likes</p>
                <button type="submit" class="flex-row px-1 bg-pink-500 hover:bg-pink-700 text-white py-1 rounded-md transition shadow-md  ">
                  <i class="fas fa-thumbs-up text-white"></i>
                  Like
                </button>
              </div>


              <div v-show="activeTab === 2" class="text-gray-600 pb-4">
                Free car listing space
                Access to premium customer leads
                Flexible financing options
              </div>
              <div v-show="activeTab === 3" class="text-gray-600 pb-4">
                <div class="flex-col  -px-4 gap-3">
                  <div class="p-6  max-w-md mx-auto space-y-4">
                    <div class="flex items-center space-x-3 text-gray-700">
                      <i class="fas fa-phone-alt text-pink-500"></i>
                      <a href="tel:+1769778159236" class="hover:underline">+1 769 778 159 236</a>
                    </div>

                    <div class="flex items-center space-x-3 text-gray-700">
                      <i class="fab fa-facebook text-blue-600"></i>
                      <a href="https://facebook.com/fbidname" target="_blank" class="hover:underline">facebook.com/facebook-id</a>
                    </div>

                    <div class="flex items-center space-x-3 text-gray-700">
                      <i class="fab fa-instagram text-pink-600"></i>
                      <a href="https://instagram.com/fbidname" target="_blank" class="hover:underline">instagram.com/instagram</a>
                    </div>

                    <div class="flex items-center space-x-3 text-gray-700">
                      <i class="fas fa-globe text-green-500"></i>
                      <a href="https://somesite.com/" target="_blank" class="hover:underline">some-ite.com</a>
                    </div>
                  </div>
                </div>

              </div>

            </div>


            <!-- Profile Tabs -->
            <div class="lg:flex space-x-1 border-b-2 pb-2 bg-pink-100 justify-center">
              <button
                  :class="{'border-b-2 border-pink-500': activeTab === 1}"
                  class="text-xl font-semibold py-2 px-4 cursor-pointer"
                  @click="activeTab = 1">
                <i class="fas fa-user"
                   :class="activeTab === 1 ? 'text-pink-500' : 'text-pink-300'"

                />
              </button>
              <button
                  :class="{'border-b-2 border-pink-500': activeTab === 2}"
                  class="text-xl font-semibold py-2 px-4 cursor-pointer"
                  @click="activeTab = 2">
                <i class="fas fa-info"
                   :class="activeTab === 2 ? 'text-pink-500' : 'text-pink-300'"

                />
              </button>
              <button
                  :class="{'border-b-2 border-pink-500': activeTab === 3}"
                  class="text-xl font-semibold py-2 px-4 cursor-pointer"
                  @click="activeTab = 3">
                <i class="fas fa-envelope"
                   :class="activeTab === 3 ? 'text-pink-500' : 'text-pink-300'"

                />
              </button>

            </div>





          </div>




        </div>
      </div>


      <div class="w-full">

        <h1 class="pt-12 pb-4 text-xl text-pink-500 font-extrabold"> Recent Cars</h1>
        <FeaturedDealers :posts="recentPosts" />
        <div class="flex justify-end">
          <router-link to="cars" class="py-2  text-pink-500 font-extrabold">
            Browse All ..
          </router-link>
        </div>
      </div>



    </div>

    <!-- Fixed Sidebar - Ad Item -->
    <div class="w-full lg:w-1/5 p-4 text-center text-gray-700 sticky">
      <AdItem/>
    </div>
  </div>
</template>

<script setup lang="ts">
import AdItem from "../../components/AdItem.vue";

import {onMounted, ref} from "vue";
import FeaturedDealers from "../../components/FeaturedDealers.vue";
import {Dealer} from "../../types/Dealer.ts";
import {useRoute} from "vue-router";
import apiService from "../../core/services/ApiService.ts";

defineOptions({
  name: 'user-profile'
});

const activeTab = ref(1);
const recentPosts = [
  { title: "Post 1", image: "post1.jpg", link: "/post1" },
  { title: "Post 2", image: "post2.jpg", link: "/post2" },
  { title: "Post 3", image: "post3.jpg", link: "/post3" },
  { title: "Post 3", image: "post3.jpg", link: "/post3" },
  { title: "Post 3", image: "post3.jpg", link: "/post3" },
];



// Car details state
const dealer = ref<Dealer | null>(null);
const loading = ref(true);

// Get the car ID from the route params
const route = useRoute();
const dealerId = route.params.id as string;

// Fetch car details from the backend
const fetchDealerProfile = async () => {
  try {
    const response = await apiService.get1(`/profiles/${dealerId}`);
    dealer.value = response.data;
  } catch (error) {
    console.error('Error fetching car details:', error);
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchDealerProfile();
});




</script>

<style scoped>
</style>
