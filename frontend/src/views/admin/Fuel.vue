<script setup lang="ts">
import IconButton from "../../components/IconButton.vue";
import {onMounted, ref} from "vue";
import apiService from "../../core/services/ApiService.ts";
import FuelItem from "../../components/admin/fuel/FuelItem.vue";
import UpdateFuelPopup from "../../components/admin/fuel/UpdateFuelPopup.vue";
import AddFuelPopup from "../../components/admin/fuel/AddFuelPopup.vue";

export interface Fuel {
  id: string;
  name_en: string;
  name_ar: string;
  // add other properties if your role object has them
}
const fuels = ref<Fuel[]>([]); // If using Composition API
const selectedFuel = ref<Fuel>()

const loading = ref(true);
const showAddFuelPopup = ref(false);
const showUpdateFuelPopup = ref(false);

onMounted(async () => {
  await handlePageChange()
});



const handlePageChange = async () => {
  try {
    const response = await apiService.get1("/ref-data/fuel");
    fuels.value = response.data || [];
  } catch (error) {
    console.error("Error fetching fuels:", error);
    fuels.value = [];
  } finally {
    loading.value = false;
  }
};

const handleFuelAdded = async () => {
  showAddFuelPopup.value = false;
  await handlePageChange(); // Refresh the role list
};


const toggleAddFuelPopup = () => {
  showAddFuelPopup.value = !showAddFuelPopup.value;
};


const toggleUpdateFuelPopup = (engine: Fuel) => {
  selectedFuel.value = engine
  showUpdateFuelPopup.value = !showUpdateFuelPopup.value;
};

const handleFuelUpdated = async () => {
  showUpdateFuelPopup.value = false;
  await handlePageChange(); // Refresh the role list

};







</script>

<template>
  <div class="relative min-h-[500px]">


    <AddFuelPopup
        v-if="showAddFuelPopup"
        @close="toggleAddFuelPopup"
        @fuel-added="handleFuelAdded"
    />

    <UpdateFuelPopup
        v-if="showUpdateFuelPopup"
        @close="toggleUpdateFuelPopup"
        @fuel-updated="handleFuelUpdated"
        :id="selectedFuel?.id!!"
        :name_en="selectedFuel?.name_en!!"
        :name_ar="selectedFuel?.name_ar!!"

    />

    <div class="flex justify-center items-center">
      <div class="w-full p-4 text-center text-gray-400">
        <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
          <div class="group flex-col inset-px shadow-md">
            <!--Divider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>
            <div class="flex justify-between items-center bg-gray-100">
              <div class="flex px-4 text-pink-500 font-bold text-2xl">Fuel</div>

              <div class="flex p-4 text-pink-500 font-bold text-2xl">
                   <IconButton icon="fas fa-plus" label="Add" class="hover:bg-pink-100" @click="toggleAddFuelPopup"/>
              </div>
            </div>
            <!--Divider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>

            <!-- User Items -->
            <div v-if="!loading">
              <FuelItem
                  v-for="(engine, index) in fuels"
                  :key="index"
                  :name_en="engine.name_en"
                  :name_ar="engine.name_ar"
                  @click="() => toggleUpdateFuelPopup(engine)"
               id=""/>
            </div>
            <div v-else class="p-4 text-gray-500">Loading fuels...</div>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<style scoped>
</style>