<script setup lang="ts">
import IconButton from "../../components/IconButton.vue";
import {onMounted, ref} from "vue";
import apiService from "../../core/services/ApiService.ts";
import CityItem from "../../components/admin/cities/CityItem.vue";
import UpdateCityPopup from "../../components/admin/cities/UpdateCityPopup.vue";
import AddCityPopup from "../../components/admin/cities/AddCityPopup.vue";
import {Country} from "./Countries.vue";

export interface City {
  id: string;
  name_en: string;
  name_ar: string;
  // add other properties if your role object has them
}

const countries = ref<Country[]>([]); // If using Composition API
const selectedCountry = ref<Country>();
const cities = ref<City[]>([]); // If using Composition API
const selectedCity = ref<City>()

const loading = ref(true);
const showAddCityPopup = ref(false);
const showUpdateCityPopup = ref(false);

onMounted(async () => {
  await getCountries()
});

const getCountries = async () => {
  try {
    const response = await apiService.get1("/ref-data/countries");
    countries.value = response.data || [];
    if (countries.value.length > 0) {
      selectedCountry.value = countries.value[0];
      console.log(selectedCountry.value)
      await getCities(selectedCountry.value.id);
    }
  } catch (error) {
    console.error("Error fetching countries:", error);
    countries.value = [];
  } finally {
    loading.value = false;



  }
}

const getCities = async (id: string) => {
  try {
    const response = await apiService.get1(`/ref-data/countries/${id}/cities`);
    cities.value = response.data || [];
  } catch (error) {
    console.error("Error fetching makes:", error);
    cities.value = [];
  } finally {
    loading.value = false;

  }
};



const handleCityAdded = async () => {
  showAddCityPopup.value = false;
  await getCities(selectedCountry.value?.id!!); // Refresh the role list
};


const toggleAddCityPopup = () => {
  showAddCityPopup.value = !showAddCityPopup.value;
};


const toggleUpdateCityPopup = (engine: City) => {
  selectedCity.value = engine
  showUpdateCityPopup.value = !showUpdateCityPopup.value;
};

const handleCityUpdated = async () => {
  showUpdateCityPopup.value = false;
  await getCities(selectedCountry.value?.id!!); // Refresh the role list

};







</script>

<template>
  <div class="relative min-h-[500px]">


    <AddCityPopup
        v-if="showAddCityPopup"
        @close="toggleAddCityPopup"
        @city-added="handleCityAdded"
        :id="selectedCountry?.id!!"
    />

    <UpdateCityPopup
        v-if="showUpdateCityPopup"
        @close="toggleUpdateCityPopup"
        @city-updated="handleCityUpdated"
        :id="selectedCity?.id!!"
        :name_en="selectedCity?.name_en!!"
        :name_ar="selectedCity?.name_ar!!"

    />

    <div class="flex justify-center items-center">
      <div class="w-full p-4 text-center text-gray-400">
        <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
          <div class="group flex-col inset-px shadow-md">
            <!--Divider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>
            <div class="flex justify-between items-center bg-gray-100">
              <div class="flex px-4 text-pink-500 font-bold text-2xl">Cities</div>


              <div class="flex p-4 text-pink-500 font-bold text-2xl">
                <select
                    class="hover:bg-pink-100"
                    v-model="selectedCountry"
                    @change="getCities(selectedCountry?.id!!)"
                >
                  <option
                      v-for="(item, index) in countries"
                      :key="index"
                      :value="item"
                  >
                    {{ item.name_en }}
                  </option>
                </select>
                <IconButton icon="fas fa-plus" label="Add" class="hover:bg-pink-100" @click="toggleAddCityPopup"/>
              </div>
            </div>
            <!--Divider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>

            <!-- User Items -->
            <div v-if="!loading">
              <CityItem
                  v-for="(item, index) in cities"
                  :key="index"
                  :name_en="item.name_en"
                  :name_ar="item.name_ar"
                  @click="() => toggleUpdateCityPopup(item)"
               id=""/>
            </div>
            <div v-else class="p-4 text-gray-500">Loading cities...</div>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<style scoped>
</style>