<script setup lang="ts">


import AdItem from "../components/AdItem.vue";
import Carousel from "../components/Carousel.vue";
import RecentCars from "../components/RecentCars.vue";
import FeaturedDealers from "../components/FeaturedDealers.vue";
import BrandsSlider from "../components/BrandsSlider.vue";
import apiService from "../core/services/ApiService.ts";
import {computed, onMounted, ref} from "vue";
import {CarListDto} from "../core/models/CarListDto.ts";
import {useStaticDataStore} from "../stores/staticDataStore.ts";
import {DealerListDto} from "../core/models/DealerListDto.ts";
import {useI18n} from "vue-i18n";
const { t } = useI18n();



defineOptions({
  name: 'Home'
})

const staticData = useStaticDataStore();

const cars = ref<CarListDto[]>([]);
const dealers = ref<DealerListDto[]>([]);
const { locale } = useI18n();

const slides = computed(() => {
  return locale.value === 'en' ? slidesDataEn : slidesDataAr;
});


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

const slidesDataEn = [
  {
    title: "Best Car Deals",
    subtitle: "Find the right car",
    description: "Explore cars by make, model, and all other specs",
    image: "/bmw_car_show.jpg",
  },
  {
    title: "Only Cars Market",
    subtitle: "Fast & Updated",
    description: "No need to get lost in between of thousands of ads ",
    image: "/coars_only.webp",
  },
  {
    title: "Growing Dealers Catalog",
    subtitle: "Find dealers easily",
    description: "Find dealers in your city, country or beyond",
    image: "/dealers.jpg",
  },

];
const slidesDataAr = [
  {
    title: "أفضل عروض السيارات",
    subtitle: "حتما ستجد ما تريد",
    description: "استعرض أو ابحث بجميع المواصفات",
    image: "/bmw_car_show.jpg",
  },
  {
    title: "سوق خاص بالسيارات فقط",
    subtitle: "سريع ومحدث على الدوام",
    description: "لا داعي للتوهان بين الاف الاعلانات",
    image: "/coars_only.webp",
  },
  {
    title: "مكان تجمع للموزعين والمعارض",
    subtitle: "ابحث عن المعارض بسهولة",
    description: "استعرض بالقرب منك أو في أي مكان",
    image: "/dealers.jpg",
  },

];


const brands = staticData.makes.map((brand) => {
  return {
     image: brand.url,
  };
});

import {useProfileStore} from "../stores/profileStore.ts";
import {useAuthStore} from "../stores/auth.ts";
import ProfileView from "../components/ProfileView.vue";

const profileStore = useProfileStore()
const authStore = useAuthStore()

onMounted(() => {
  if (authStore.userInfo?.name) {
    profileStore.fetchUserProfile(authStore.userInfo.name)
  }
})

</script>

<template>

  <div class="flex xl:flex-row-reverse flex-wrap w-full">
    <!-- Only show if profile is NOT complete and data is loaded -->
    <ProfileView
        v-if="authStore.isAuthenticated && !profileStore.isProfileComplete && !profileStore.loading && !profileStore.error"
        class="sticky top-16 z-10 bg-white border-b border-gray-200 h-20 w-full px-6 flex items-center justify-between shadow-sm"
    />

    <div class="w-full xl:w-1/5 p-4 text-center text-gray-400">
      <AdItem/>

    </div>
    <div class="w-full xl:w-3/5 p-4 text-center text-gray-200">

      <div class="flex-col w-full  gap-3">



        <Carousel :slides="slides" dir="ltr" />


        <h1 class="pt-12 pb-4 text-xl text-pink-500 font-extrabold">{{t('recent_cars')}}</h1>
        <RecentCars :cars="cars" />




        <div class="flex justify-end">
          <router-link to="cars" class="py-2  text-pink-500 font-extrabold">
            {{t('find_more')}}
          </router-link>
        </div>



        <h1 class="pt-12 pb-4 text-xl text-pink-500 font-extrabold">{{t('recent_dealers')}}</h1>
        <FeaturedDealers :dealers="dealers" />


        <div class="flex justify-end">
          <router-link to="dealers" class="py-2  text-pink-500 font-extrabold">
            {{t('find_more')}}
          </router-link>
        </div>


        <h1 class="pt-12 pb-4 text-xl text-pink-500 font-extrabold">{{t('brands')}}
        </h1>

        <BrandsSlider :slides="brands" dir="ltr" />






      </div>


    </div>
    <div class="w-full xl:w-1/5  p-4 text-center text-gray-700 sticky">
      <AdItem/>

    </div>
  </div>
  <div class="fixed bottom-14 end-6  rounded-full">
    <router-link

        class="flex items-center space-x-2 px-4 py-2 bg-pink-500 text-white hover:transform
               transition-transform duration-900 ease-in-out hover:bg-pink-600 hover:scale-110 rounded-full"
        to="/cars/add-car">
      <i class="fas fa-plus text-2xl text-center w-8 h-8" />
      <span class="hidden xl:block font-extrabold">{{ t('quick_add') }}</span>
    </router-link>
  </div>

</template>

<style scoped>

</style>