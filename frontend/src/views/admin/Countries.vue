
<script setup lang="ts">
import IconButton from "../../components/IconButton.vue";
import {onMounted, ref} from "vue";
import apiService from "../../core/services/ApiService.ts";
import CountryItem from "../../components/admin/countries/CountryItem.vue";
import UpdateCountryPopup from "../../components/admin/countries/UpdateCountryPopup.vue";
import AddCountryPopup from "../../components/admin/countries/AddCountryPopup.vue";

export interface Country {
  id: string;
  name_en: string;
  name_ar: string;
  // add other properties if your role object has them
}
const countries = ref<Country[]>([]); // If using Composition API
const selectedCountry = ref<Country>()

const loading = ref(true);
const showAddCountryPopup = ref(false);
const showUpdateCountryPopup = ref(false);

onMounted(async () => {
  await handlePageChange()
});



const handlePageChange = async () => {
  try {
    const response = await apiService.get1("/ref-data/countries");
    countries.value = response.data || [];
  } catch (error) {
    console.error("Error fetching countries:", error);
    countries.value = [];
  } finally {
    loading.value = false;
  }
};

const handleCountryAdded = async () => {
  showAddCountryPopup.value = false;
  await handlePageChange(); // Refresh the role list
};


const toggleAddCountryPopup = () => {
  showAddCountryPopup.value = !showAddCountryPopup.value;
};


const toggleUpdateCountryPopup = (engine: Country) => {
  selectedCountry.value = engine
  showUpdateCountryPopup.value = !showUpdateCountryPopup.value;
};

const handleCountryUpdated = async () => {
  showUpdateCountryPopup.value = false;
  await handlePageChange(); // Refresh the role list

};







</script>

<template>
  <div class="relative min-h-[500px]">


    <AddCountryPopup
        v-if="showAddCountryPopup"
        @close="toggleAddCountryPopup"
        @country-added="handleCountryAdded"
    />

    <UpdateCountryPopup
        v-if="showUpdateCountryPopup"
        @close="toggleUpdateCountryPopup"
        @country-updated="handleCountryUpdated"
        :id="selectedCountry.id"
        :name_en="selectedCountry.name_en"
        :name_ar="selectedCountry.name_ar"

    />

    <div class="flex justify-center items-center">
      <div class="w-full p-4 text-center text-gray-400">
        <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
          <div class="group flex-col inset-px shadow-md">
            <!--Devider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>
            <div class="flex justify-between items-center bg-gray-100">
              <div class="flex px-4 text-pink-500 font-bold text-2xl">Countries</div>

              <div class="flex p-4 text-pink-500 font-bold text-2xl">
                   <IconButton icon="fas fa-plus" label="Add" class="hover:bg-pink-100" @click="toggleAddCountryPopup"/>
              </div>
            </div>
            <!--Devider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>

            <!-- User Items -->
            <div v-if="!loading">
              <CountryItem
                  v-for="(engine, index) in countries"
                  :key="index"
                  :name_en="engine.name_en"
                  :name_ar="engine.name_ar"
                  @click="() => toggleUpdateCountryPopup(engine)"
               id=""/>
            </div>
            <div v-else class="p-4 text-gray-500">Loading countries...</div>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<style scoped>
</style>