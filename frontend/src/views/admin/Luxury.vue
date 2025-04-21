<script setup lang="ts">
import IconButton from "../../components/IconButton.vue";
import {onMounted, ref} from "vue";
import apiService from "../../core/services/ApiService.ts";
import LuxuryItem from "../../components/admin/luxury/LuxuryItem.vue";
import UpdateLuxuryPopup from "../../components/admin/luxury/UpdateLuxuryPopup.vue";
import AddLuxuryPopup from "../../components/admin/luxury/AddLuxuryPopup.vue";

export interface Luxury {
  id: string;
  name_en: string;
  name_ar: string;
  // add other properties if your role object has them
}
const luxury_items = ref<Luxury[]>([]); // If using Composition API
const selectedLuxury = ref<Luxury>()

const loading = ref(true);
const showAddLuxuryPopup = ref(false);
const showUpdateLuxuryPopup = ref(false);

onMounted(async () => {
  await handlePageChange()
});



const handlePageChange = async () => {
  try {
    const response = await apiService.get1("/ref-data/luxury");
    luxury_items.value = response.data || [];
  } catch (error) {
    console.error("Error fetching luxury_items:", error);
    luxury_items.value = [];
  } finally {
    loading.value = false;
  }
};

const handleLuxuryAdded = async () => {
  showAddLuxuryPopup.value = false;
  await handlePageChange(); // Refresh the role list
};


const toggleAddLuxuryPopup = () => {
  showAddLuxuryPopup.value = !showAddLuxuryPopup.value;
};


const toggleUpdateLuxuryPopup = (item: Luxury) => {
  selectedLuxury.value = item
  showUpdateLuxuryPopup.value = !showUpdateLuxuryPopup.value;
};

const handleLuxuryUpdated = async () => {
  showUpdateLuxuryPopup.value = false;
  await handlePageChange(); // Refresh the role list

};







</script>

<template>
  <div class="relative min-h-[500px]">


    <AddLuxuryPopup
        v-if="showAddLuxuryPopup"
        @close="toggleAddLuxuryPopup"
        @luxury-added="handleLuxuryAdded"
    />

    <UpdateLuxuryPopup
        v-if="showUpdateLuxuryPopup"
        @close="toggleUpdateLuxuryPopup"
        @luxury-updated="handleLuxuryUpdated"
        :id="selectedLuxury.id"
        :name_en="selectedLuxury.name_en"
        :name_ar="selectedLuxury.name_ar"

    />

    <div class="flex justify-center items-center">
      <div class="w-full p-4 text-center text-gray-400">
        <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
          <div class="group flex-col inset-px shadow-md">
            <!--Devider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>
            <div class="flex justify-between items-center bg-gray-100">
              <div class="flex px-4 text-pink-500 font-bold text-2xl">Luxury</div>

              <div class="flex p-4 text-pink-500 font-bold text-2xl">
                   <IconButton icon="fas fa-plus" label="Add" class="hover:bg-pink-100" @click="toggleAddLuxuryPopup"/>
              </div>
            </div>
            <!--Devider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>

            <!-- User Items -->
            <div v-if="!loading">
              <LuxuryItem
                  v-for="(item, index) in luxury_items"
                  :key="index"
                  :name_en="item.name_en"
                  :name_ar="item.name_ar"
                  @click="() => toggleUpdateLuxuryPopup(item)"
               id=""/>
            </div>
            <div v-else class="p-4 text-gray-500">Loading luxury...</div>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<style scoped>
</style>