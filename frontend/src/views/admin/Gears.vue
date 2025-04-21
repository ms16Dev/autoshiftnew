<script setup lang="ts">
import IconButton from "../../components/IconButton.vue";
import {onMounted, ref} from "vue";
import apiService from "../../core/services/ApiService.ts";
import GearItem from "../../components/admin/gears/GearItem.vue";
import UpdateGearPopup from "../../components/admin/gears/UpdateGearPopup.vue";
import AddGearPopup from "../../components/admin/gears/AddGearPopup.vue";

export interface Gear {
  id: string;
  name_en: string;
  name_ar: string;
  // add other properties if your role object has them
}
const gears = ref<Gear[]>([]); // If using Composition API
const selectedGear = ref<Gear>()

const loading = ref(true);
const showAddGearPopup = ref(false);
const showUpdateGearPopup = ref(false);

onMounted(async () => {
  await handlePageChange()
});



const handlePageChange = async () => {
  try {
    const response = await apiService.get1("/ref-data/gears");
    gears.value = response.data || [];
  } catch (error) {
    console.error("Error fetching gears:", error);
    gears.value = [];
  } finally {
    loading.value = false;
  }
};

const handleGearAdded = async () => {
  showAddGearPopup.value = false;
  await handlePageChange(); // Refresh the role list
};


const toggleAddGearPopup = () => {
  showAddGearPopup.value = !showAddGearPopup.value;
};


const toggleUpdateGearPopup = (engine: Gear) => {
  selectedGear.value = engine
  showUpdateGearPopup.value = !showUpdateGearPopup.value;
};

const handleGearUpdated = async () => {
  showUpdateGearPopup.value = false;
  await handlePageChange(); // Refresh the role list

};







</script>

<template>
  <div class="relative min-h-[500px]">


    <AddGearPopup
        v-if="showAddGearPopup"
        @close="toggleAddGearPopup"
        @gear-added="handleGearAdded"
    />

    <UpdateGearPopup
        v-if="showUpdateGearPopup"
        @close="toggleUpdateGearPopup"
        @gear-updated="handleGearUpdated"
        :id="selectedGear.id"
        :name_en="selectedGear.name_en"
        :name_ar="selectedGear.name_ar"

    />

    <div class="flex justify-center items-center">
      <div class="w-full p-4 text-center text-gray-400">
        <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
          <div class="group flex-col inset-px shadow-md">
            <!--Devider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>
            <div class="flex justify-between items-center bg-gray-100">
              <div class="flex px-4 text-pink-500 font-bold text-2xl">Gears</div>

              <div class="flex p-4 text-pink-500 font-bold text-2xl">
                   <IconButton icon="fas fa-plus" label="Add" class="hover:bg-pink-100" @click="toggleAddGearPopup"/>
              </div>
            </div>
            <!--Devider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>

            <!-- User Items -->
            <div v-if="!loading">
              <GearItem
                  v-for="(engine, index) in gears"
                  :key="index"
                  :name_en="engine.name_en"
                  :name_ar="engine.name_ar"
                  @click="() => toggleUpdateGearPopup(engine)"
               id=""/>
            </div>
            <div v-else class="p-4 text-gray-500">Loading gears...</div>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<style scoped>
</style>