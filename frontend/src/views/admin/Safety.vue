<script setup lang="ts">
import IconButton from "../../components/IconButton.vue";
import {onMounted, ref} from "vue";
import apiService from "../../core/services/ApiService.ts";
import SafetyItem from "../../components/admin/safety/SafetyItem.vue";
import UpdateSafetyPopup from "../../components/admin/safety/UpdateSafetyPopup.vue";
import AddSafetyPopup from "../../components/admin/safety/AddSafetyPopup.vue";

export interface Safety {
  id: string;
  name_en: string;
  name_ar: string;
  // add other properties if your role object has them
}
const safety_items = ref<Safety[]>([]); // If using Composition API
const selectedSafety = ref<Safety>()

const loading = ref(true);
const showAddSafetyPopup = ref(false);
const showUpdateSafetyPopup = ref(false);

onMounted(async () => {
  await handlePageChange()
});



const handlePageChange = async () => {
  try {
    const response = await apiService.get1("/ref-data/safety");
    safety_items.value = response.data || [];
  } catch (error) {
    console.error("Error fetching safety_items:", error);
    safety_items.value = [];
  } finally {
    loading.value = false;
  }
};

const handleSafetyAdded = async () => {
  showAddSafetyPopup.value = false;
  await handlePageChange(); // Refresh the role list
};


const toggleAddSafetyPopup = () => {
  showAddSafetyPopup.value = !showAddSafetyPopup.value;
};


const toggleUpdateSafetyPopup = (item: Safety) => {
  selectedSafety.value = item
  showUpdateSafetyPopup.value = !showUpdateSafetyPopup.value;
};

const handleSafetyUpdated = async () => {
  showUpdateSafetyPopup.value = false;
  await handlePageChange(); // Refresh the role list

};







</script>

<template>
  <div class="relative min-h-[500px]">


    <AddSafetyPopup
        v-if="showAddSafetyPopup"
        @close="toggleAddSafetyPopup"
        @safety-added="handleSafetyAdded"
    />

    <UpdateSafetyPopup
        v-if="showUpdateSafetyPopup"
        @close="toggleUpdateSafetyPopup"
        @safety-updated="handleSafetyUpdated"
        :id="selectedSafety.id"
        :name_en="selectedSafety.name_en"
        :name_ar="selectedSafety.name_ar"

    />

    <div class="flex justify-center items-center">
      <div class="w-full p-4 text-center text-gray-400">
        <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
          <div class="group flex-col inset-px shadow-md">
            <!--Devider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>
            <div class="flex justify-between items-center bg-gray-100">
              <div class="flex px-4 text-pink-500 font-bold text-2xl">Safety</div>

              <div class="flex p-4 text-pink-500 font-bold text-2xl">
                   <IconButton icon="fas fa-plus" label="Add" class="hover:bg-pink-100" @click="toggleAddSafetyPopup"/>
              </div>
            </div>
            <!--Devider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>

            <!-- User Items -->
            <div v-if="!loading">
              <SafetyItem
                  v-for="(item, index) in safety_items"
                  :key="index"
                  :name_en="item.name_en"
                  :name_ar="item.name_ar"
                  @click="() => toggleUpdateSafetyPopup(item)"
               id=""/>
            </div>
            <div v-else class="p-4 text-gray-500">Loading safety...</div>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<style scoped>
</style>