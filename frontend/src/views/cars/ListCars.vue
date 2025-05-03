<script setup lang="ts">
import CarItem from "../../components/CarItem.vue";
import { computed, ref, onMounted } from "vue";
import TablePagination from "../../components/TablePagenition.vue";
import AdItem from "../../components/AdItem.vue";
import apiService from "../../core/services/ApiService.ts";
import { CarListDto } from "../../core/models/CarListDto.ts";

defineOptions({
  name: "List-Cars",
});

const cars = ref<CarListDto[]>([]);
const count = ref(0);
const currentPage = ref(1);
const itemsPerPage = ref(10);
const loading = ref(true);

onMounted(async () => {
await handlePageChange(1)
});

const totalPages = computed(() => Math.ceil(count.value / itemsPerPage.value));


const handlePageChange = async (page: number) => {
  const offset = (page-1)*itemsPerPage.value
  try {
    const response = await apiService.get1("cars?offset="+offset+"&limit="+itemsPerPage.value);
    cars.value = response.data.data || [];
    count.value = response.data.count || 0;

  } catch (error) {
    console.error("Error fetching cars:", error);
    cars.value = [];
  } finally {
    loading.value = false;
  }
};
</script>

<template>
  <div class="flex xl:flex-row-reverse flex-wrap w-full bg-gray-200">
    <div class="w-full xl:w-1/5 p-4 text-center text-gray-400">
      <AdItem />
    </div>

    <div class="w-full xl:w-3/5 p-4 text-center text-gray-200">
      <div v-if="loading" class="text-center text-gray-600">Loading cars...</div>
      <div v-else class="flex-col grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 px-4 gap-3">
        <CarItem v-for="car in cars" :key="car.id" :car="car" />
      </div>

      <TablePagination
          :current-page="currentPage"
          :per-page="itemsPerPage"
          :total="count"
          :total-pages="totalPages"
          @page-change="handlePageChange"
      />
    </div>

    <div class="w-full xl:w-1/5 p-4 text-center text-gray-700 fixed sticky">
      <AdItem />
    </div>
  </div>
</template>

<style scoped></style>
