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
const currentPage = ref(1);
const itemsPerPage = ref(15);
const loading = ref(true);

onMounted(async () => {
  try {
    const response = await apiService.get1("cars");
    cars.value = response.data.data || [];
    console.log("Fetched cars:", cars.value);
  } catch (error) {
    console.error("Error fetching cars:", error);
    cars.value = [];
  } finally {
    loading.value = false;
  }
});

const totalPages = computed(() => Math.ceil(cars.value.length / itemsPerPage.value));

const paginatedCars = computed(() => {
  const startIndex = (currentPage.value - 1) * itemsPerPage.value;
  return cars.value.slice(startIndex, startIndex + itemsPerPage.value);
});

const handlePageChange = (page: number) => {
  currentPage.value = page;
};
</script>

<template>
  <div class="flex md:flex-row-reverse flex-wrap w-full bg-gray-200">
    <div class="w-full md:w-1/5 p-4 text-center text-gray-400">
      <AdItem />
    </div>

    <div class="w-full md:w-3/5 p-4 text-center text-gray-200">
      <div v-if="loading" class="text-center text-gray-600">Loading cars...</div>
      <div v-else class="flex-col grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 px-4 gap-3">
        <CarItem v-for="car in paginatedCars" :key="car.id" :car="car" />
      </div>

      <TablePagination
          :current-page="currentPage"
          :per-page="itemsPerPage"
          :total="cars.length"
          :total-pages="totalPages"
          @page-change="handlePageChange"
      />
    </div>

    <div class="w-full md:w-1/5 p-4 text-center text-gray-700 fixed sticky">
      <AdItem />
    </div>
  </div>
</template>

<style scoped></style>
