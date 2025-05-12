<script setup lang="ts">
import AdItem from "../../components/AdItem.vue";
import Comments from "../../components/Comments.vue";
import UserView from "../../components/UserView.vue";
import ImageViewer from "../../components/ImageViewer.vue";
import IconButton from "../../components/IconButton.vue";
import IconButtonH from "../../components/IconButtonH.vue";
import ChipItemCheckedSm from "../../components/car/ChipItemCheckedSm.vue";
import SharePost from "../../components/SharePost.vue";
import {computed, onMounted, ref} from "vue";
import {useRoute} from "vue-router";
import apiService from "../../core/services/ApiService.ts";
import {useAuthStore} from "../../stores/auth.ts";
import axios from "axios";
import {useI18n} from "vue-i18n";
import {config} from "../../../config.ts";
import {useStaticDataStore} from "../../stores/staticDataStore.ts";
import {formatRelativeDate} from "../../utils/dateUtils.ts";
import {Dealer} from "../../types/Dealer.ts";
import {CarDetailDto} from "../../core/models/CarDetailDto.ts";
const { t } = useI18n()
const staticData = useStaticDataStore();

const { locale } = useI18n();




defineOptions({
  name: 'car-details'
});

const formatNumber = (number: number): string => {
  return new Intl.NumberFormat('en-US').format(number);
};

const authStore = useAuthStore();

// Car details state
const car = ref<CarDetailDto | null>(null);
const dealer = ref<Dealer | null>(null);
const loading = ref(true);

// Get the car ID from the route params
const route = useRoute();
const carId = route.params.id as string;

// Fetch car details from the backend
const fetchCarDetails = async () => {
  try {
    const response = await apiService.get1(`cars/${carId}`);
    car.value = response.data;
    // Once car is fetched and createdBy is available, fetch user
    if (car.value?.createdBy) {
      await fetchUserDetails(car.value.createdBy);
    }
  } catch (error) {
    console.error('Error fetching car details:', error);
  } finally {
    loading.value = false;
  }
};

// Fetch user details from the backend
const fetchUserDetails = async (createdBy: string) => {
  try {
    const response = await apiService.get1(`profiles/${createdBy}`);
    dealer.value = response.data;
  } catch (error) {
    console.error('Error fetching dealer details:', error);
  } finally {
    loading.value = false;
  }
};

const formattedCreatedDate = computed(() =>
    car.value
        ? formatRelativeDate(car.value.lastModifiedDate, locale.value as 'en' | 'ar')
        : ''
);


onMounted(() => {
  fetchCarDetails();

});

const hasLiked = computed(() => {
  return car.value?.likedBy?.includes(authStore.userInfo?.name ?? '') ?? false;
});




const toggleLike = async () => {
  try {
    const response = await axios.post(config.apiBaseUrl+`/cars/${carId}/like`, {
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
          <span>{{ staticData.getLocalizedName(staticData.getMakeById(car?.make!!)) }}  -</span>
          <span>{{ staticData.getLocalizedName(staticData.getClassById(car?.model!!)) }} -</span>
          <span>{{ car?.year }} -</span>
          <span>{{staticData.getLocalizedName(staticData.getStatusById(car?.origin!!)) }}</span>
        </div>
        <div class="flex flex-wrap text-xl text-gray-500 col-span-2 text-center items-center gap-2">
          <UserView :user="{ id: car?.createdBy, name: dealer?.name , date: formattedCreatedDate, avatar: config.apiBaseUrl+dealer?.avatarUrl }" />
          <div class="ltr:ml-auto rtl:mr-auto ">
            <SharePost />
          </div>
          <button
              v-if="authStore.isAuthenticated"
              @click="toggleLike"
              class="flex-row px-3 text-white text-sm font-bold py-2 rounded-lg transition shadow-md bg-pink-500 hover:bg-pink-700"
          >
            <i  :class="hasLiked ? 'fas fa-thumbs-down text-white' : 'fas fa-thumbs-up text-white'"></i>
            {{ hasLiked ? t('unlike') : t('like') }}
          </button>
        </div>



        <ImageViewer class="w-full col-span-2 mb-8"
             :coverPhoto="car?.coverImage || ''"
             :imageList="car?.images || []"
        />

        <h1 class="pt-12 pb-4 text-xl col-span-2 text-pink-500 font-extrabold">{{t('specs')}}</h1>

        <div class="flex justify-center items-center flex-wrap col-span-2 gap-3">

          <IconButton icon="fa-tachometer" :label="formatNumber(Number(car?.mileage)) + t('km') || ''" />
          <IconButton icon="fa-gears" :label="staticData.getLocalizedName(staticData.getGearById(car?.gear!!)) || ''" />
          <IconButton icon="fa-gas-pump" :label="staticData.getLocalizedName(staticData.getFuelById(car?.type!!)) || ''" />
          <IconButton icon="fa-bolt" :label="staticData.getLocalizedName(staticData.getEngineById(car?.engine!!)) || ''" />
          <IconButton icon="fa-car" :label="staticData.getLocalizedName(staticData.getShapeById(car?.shape!!)) || ''" />
          <IconButton icon="fa-brush" :label="staticData.getLocalizedName(staticData.getColorById(car?.color!!)) || ''" />
        </div>





        <div class="flex flex-col items-start col-span-2">

          <IconButtonH icon="fa-leaf" :label="t('car_luxury')" />
          <div  v-for="(luxuryItem, index) in car?.luxury" :key="index" class="flex flex-wrap col-span-2 mb-8">
            <ChipItemCheckedSm :option="staticData.getLocalizedName(staticData.findItemById('luxury',luxuryItem)!)" />
          </div>

          <IconButtonH icon="fa-life-ring" :label="t('car_safety')" />
          <div v-for="(safetyItem, index) in car?.safety" :key="index" class="flex flex-wrap col-span-2 mb-8">
            <ChipItemCheckedSm :option="staticData.getLocalizedName(staticData.findItemById('safety',safetyItem)!)" />
          </div>

          <IconButtonH icon="fa-info" :label="t('description')" />
          <p class="text-gray-400 text-lg mb-12">
            {{ car?.description }}
          </p>


          <Comments :carId="carId"></Comments>




        </div>




      </div>




    </div>

    <div class="w-full md:w-1/5  p-4 text-center text-gray-700 sticky">
      <AdItem/>

    </div>
  </div></template>


<style scoped>

</style>