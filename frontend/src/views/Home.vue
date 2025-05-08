<script setup lang="ts">


import ButtonWithPopupMenu from "../components/FAB.vue";
import AdItem from "../components/AdItem.vue";
import Carousel from "../components/Carousel.vue";
import RecentCars from "../components/RecentCars.vue";
import ContactSection from "../components/ContactSection.vue";
import FeaturedDealers from "../components/FeaturedDealers.vue";
import BrandsSlider from "../components/BrandsSlider.vue";
import apiService from "../core/services/ApiService.ts";
import {onMounted, ref} from "vue";
import {CarListDto} from "../core/models/CarListDto.ts";
import {useStaticDataStore} from "../stores/staticDataStore.ts";
import {DealerListDto} from "../core/models/DealerListDto.ts";

defineOptions({
  name: 'Home'
})

const staticData = useStaticDataStore();

const cars = ref<CarListDto[]>([]);
const dealers = ref<DealerListDto[]>([]);


const fetchRecentCars = async () => {
  try {
    const response = await apiService.get1(`cars/by-country/${staticData.getCurrentCountry().id}?&limit=5`);
    cars.value = response.data.data || [];
  } catch (error) {
    console.error("Error fetching cars:", error);
    cars.value = [];
  }
};
const fetchRecentDealers = async () => {
  try {
    const response = await apiService.get1(`profiles/by-country/${staticData.getCurrentCountry().id}?&limit=5`);
    dealers.value = response.data.data || [];
  } catch (error) {
    console.error("Error fetching cars:", error);
    dealers.value = [];
  }
};

onMounted(async () => {
  await fetchRecentCars();
  await fetchRecentDealers();
});

const slidesData = [
  {
    title: "Slide 1 Title",
    subtitle: "Amazing Features",
    description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    image: "https://th.bing.com/th/id/OIP.nPmpgQk1u1rHhqliruS7SAHaEc?rs=1&pid=ImgDetMain",
  },
  {
    title: "Slide 2 Title",
    subtitle: "Fast & Reliable",
    description: "Suspendisse potenti. Sed cursus nisi nec diam cursus feugiat.",
    image: "https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/044c73103154745.5f46af6da78a3.jpg",
  },
  {
    title: "Slide 3 Title",
    subtitle: "Responsive Design",
    description: "Donec facilisis metus nec est luctus, et consequat mauris aliquet.",
    image: "https://cdn.dribbble.com/users/5571397/screenshots/12253619/shot-cropped-1592978684837.png",
  },

];

const brands = [
  {
    image: "/carmakes/1.png",
  },
  {

    image: "/carmakes/2.png",
  },
  {

    image: "/carmakes/3.png",
  },
  {

    image: "/carmakes/4.png",
  },
  {

    image: "/carmakes/6.png",
  },
  {

    image: "/carmakes/7.png",
  },
  {

    image: "/carmakes/8.png",
  },
  {

    image: "/carmakes/9.png",
  },
  {

    image: "/carmakes/10.png",
  },
  {

    image: "/carmakes/11.png",
  },
  {

    image: "/carmakes/12.png",
  },
  {

    image: "/carmakes/13.png",
  },
  {

    image: "/carmakes/14.png",
  },
  {

    image: "/carmakes/15.png",
  },

];


</script>

<template>
  <div class="flex xl:flex-row-reverse flex-wrap w-full">
    <div class="w-full xl:w-1/5 p-4 text-center text-gray-400">
      <AdItem/>

    </div>
    <div class="w-full xl:w-3/5 p-4 text-center text-gray-200">

      <div class="flex-col w-full  gap-3">



        <Carousel :slides="slidesData" dir="ltr" />


        <h1 class="pt-12 pb-4 text-xl text-pink-500 font-extrabold"> Featured Cars</h1>
        <RecentCars :cars="cars" />




        <div class="flex justify-end">
          <router-link to="cars" class="py-2  text-pink-500 font-extrabold">
            Find more ..
          </router-link>
        </div>



        <h1 class="pt-12 pb-4 text-xl text-pink-500 font-extrabold"> Featured Dealers</h1>
        <FeaturedDealers :dealers="dealers" />


        <div class="flex justify-end">
          <router-link to="dealers" class="py-2  text-pink-500 font-extrabold">
            Find more ..
          </router-link>
        </div>


        <h1 class="pt-12 pb-4 text-xl text-pink-500 font-extrabold"> Brands</h1>

        <BrandsSlider :slides="brands" dir="ltr" />




        <ContactSection class="mt-12"/>


      </div>


    </div>
    <div class="w-full xl:w-1/5  p-4 text-center text-gray-700 sticky">
      <AdItem/>

    </div>
  </div>
  <div class="fixed bottom-14 end-6  rounded-full">
    <ButtonWithPopupMenu
        text="Quick Add"
        icon="fas fa-plus"
        menuPosition="top"
    />
  </div>

</template>

<style scoped>

</style>