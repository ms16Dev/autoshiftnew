<script setup lang="ts">
import AdItem from "../../components/AdItem.vue";
import Comments from "../../components/Comments.vue";
import UserView from "../../components/UserView.vue";
import ImageViewer from "../../components/ImageViewer.vue";
import IconButton from "../../components/IconButton.vue";
import IconButtonH from "../../components/IconButtonH.vue";
import ChipItemCheckedSm from "../../components/car/ChipItemCheckedSm.vue";
import SharePost from "../../components/SharePost.vue";
import {onMounted, ref} from "vue";
import {useRoute} from "vue-router";
import apiService from "../../core/services/ApiService.ts";
import {Car} from "../../types/Car.ts";
import {useAuthStore} from "../../stores/auth.ts";
import axios from "axios";


defineOptions({
  name: 'car-details'
});

const formatNumber = (number: number): string => {
  return new Intl.NumberFormat('en-US').format(number);
};

const authStore = useAuthStore();

// Car details state
const car = ref<Car | null>(null);
const loading = ref(true);

// Get the car ID from the route params
const route = useRoute();
const carId = route.params.id;

// Fetch car details from the backend
const fetchCarDetails = async () => {
  try {
    const response = await apiService.get1(`cars/${carId}`);
    car.value = response.data;
  } catch (error) {
    console.error('Error fetching car details:', error);
  } finally {
    loading.value = false;
  }
};


onMounted(() => {
  fetchCarDetails();
  fetchLikeStatus();
});

const hasLiked = ref(false);

const fetchLikeStatus = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/cars/${carId}/hasLiked`, {
      params: { username: authStore.userInfo?.name },
    });
    hasLiked.value = response.data;
    console.log("hasLiked", response.data);
  } catch (error) {
    console.error("Error fetching like status:", error);
  }
};

const toggleLike = async () => {
  try {
    const response = await axios.post(`/cars/${carId}/like`, {
      username: authStore.userInfo?.name,
    });

    if (response.data) {
      hasLiked.value = !hasLiked.value;
    }
  } catch (error) {
    console.error("Error toggling like:", error);
  }
};


</script>

<template>
  <div class="flex md:flex-row-reverse flex-wrap w-full bg-gray-200">
    <div class="w-full md:w-1/5 p-4 text-center text-gray-400">
      <AdItem/>

    </div>
    <div class="w-full md:w-3/5  p-4 text-center text-gray-200">

      <div class="flex-col grid grid-cols-1 md:grid-cols-2 xl:grid-cols-2 -px-4 gap-3">

<!--        Headline-->

        <div class="flex flex-wrap text-3xl text-pink-500 font-extrabold col-span-2 text-center">
          <span>{{ car?.make }} -</span>
          <span>{{ car?.model }} -</span>
          <span>{{ car?.year }} -</span>
          <span>{{ car?.origin }}</span>
        </div>
        <div class="flex flex-wrap text-xl text-gray-500 col-span-2 text-center items-center gap-2">
          <UserView :user="{ id: 1, name: 'John Doe', date: '3 weeks ago', avatar: 'https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg' }" />
          <div class="ltr:ml-auto rtl:mr-auto ">
            <SharePost />
          </div>
          <button
              @click="toggleLike"
              class="flex-row px-3 text-white text-sm font-bold py-2 rounded-lg transition shadow-md bg-pink-500 hover:bg-pink-700"
          >
            <i :class="hasLiked ? 'fas fa-thumbs-down text-white' : 'fas fa-thumbs-up text-white'"></i>
            {{ hasLiked ? 'Unlike' : 'Like' }}
          </button>
        </div>



        <ImageViewer class="w-full col-span-2 mb-8"
            :coverPhoto="car?.coverImage"
            :imageList="car?.images"
        />

        <h1 class="pt-12 pb-4 text-xl col-span-2 text-pink-500 font-extrabold">Specifications</h1>

        <div class="flex justify-center items-center flex-wrap col-span-2 gap-3">

          <IconButton icon="fa-gears" :label="formatNumber(Number(car?.mileage)) + 'Km'" />
          <IconButton icon="fa-gears" :label="car?.gear" />
          <IconButton icon="fa-gas-pump" :label="car?.type" />
          <IconButton icon="fa-bolt" :label="car?.engine" />
          <IconButton icon="fa-car" :label="car?.shape" />
          <IconButton icon="fa-brush" :label="car?.color" />
        </div>





        <div class="flex flex-col items-start col-span-2">

          <IconButtonH icon="fa-leaf" label="Luxury" />
          <div v-for="(luxuryItem, index) in car?.luxury" :key="index" class="flex flex-wrap col-span-2 mb-8">
            <ChipItemCheckedSm :option="luxuryItem" />
          </div>

          <IconButtonH icon="fa-life-ring" label="Safety" />
          <div v-for="(safetytem, index) in car?.safety" :key="index" class="flex flex-wrap col-span-2 mb-8">
            <ChipItemCheckedSm :option="safetytem" />
          </div>

          <IconButtonH icon="fa-info" label="Notes" />
          <p class="text-gray-400 text-lg mb-12">
            {{ car?.description }}
          </p>


          <Comments></Comments>




        </div>




      </div>




    </div>

    <div class="w-full md:w-1/5  p-4 text-center text-gray-700 fixed sticky">
      <AdItem/>

    </div>
  </div></template>


<style scoped>

</style>