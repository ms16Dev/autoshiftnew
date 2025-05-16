<script setup lang="ts">

import {computed, onMounted, ref} from "vue";
import TablePagination from "../../components/TablePagenition.vue";
import DealerItem from "../../components/dealer/DealerItem.vue";
import AdItem from "../../components/AdItem.vue";
import apiService from "../../core/services/ApiService.ts";
import { DealerListDto } from "../../core/models/DealerListDto.ts";
import {useStaticDataStore} from "../../stores/staticDataStore.ts";


defineOptions({
  name: 'list-dealers'
});
const dealers = ref<DealerListDto[]>([]);
const count = ref(0);
const currentPage = ref(1);  // Initialize at page 1
const itemsPerPage = ref(9); // Number of questions to show per page
const loading = ref(true);

onMounted(async () => {
  await handlePageChange(1)
});

const staticData = useStaticDataStore();

const totalPages = computed(() => Math.ceil(count.value / itemsPerPage.value));




const handlePageChange = async (page: number) => {
  const offset = (page-1)*itemsPerPage.value
  try {
    const response = await apiService.get1(`profiles/by-country/${staticData.getCurrentCountry().id}?offset=${offset}&limit=${itemsPerPage.value}`);
    dealers.value = response.data.data || [];
    count.value = response.data.count || 0;

  } catch (error) {
    console.error("Error fetching cars:", error);
    dealers.value = [];
  } finally {
    loading.value = false;
  }
};





</script>

<template>
  <div class="flex xl:flex-row-reverse flex-wrap w-full bg-gray-200">
    <div class="w-full xl:w-1/5  p-4 text-center text-gray-400">
      <AdItem/>

    </div>
    <div class="w-full xl:w-3/5  p-4 text-center text-gray-200">

      <div class="flex-col grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 -px-4 gap-3">

        <DealerItem v-for="dealer in dealers" :key="dealer.id" :dealer="dealer"/>



      </div>

      <TablePagination
          :current-page="currentPage"
          :per-page="itemsPerPage"
          :total="count"
          :total-pages="totalPages"
          @page-change="handlePageChange"
      ></TablePagination>


    </div>

    <div class="w-full xl:w-1/5 p-4 text-center text-gray-700 sticky">
      <AdItem/>

    </div>
  </div>
</template>

<style scoped>

</style>