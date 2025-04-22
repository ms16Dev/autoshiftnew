<script setup lang="ts">
import IconButton from "../../components/IconButton.vue";
import {onMounted, ref} from "vue";
import apiService from "../../core/services/ApiService.ts";
import CurrencyItem from "../../components/admin/currencies/CurrencyItem.vue";
import UpdateCurrencyPopup from "../../components/admin/currencies/UpdateCurrencyPopup.vue";
import AddCurrencyPopup from "../../components/admin/currencies/AddCurrencyPopup.vue";
import {Country} from "./Countries.vue";

export interface Currency {
  id: string;
  name_en: string;
  name_ar: string;
  // add other properties if your role object has them
}

const countries = ref<Country[]>([]); // If using Composition API
const selectedCountry = ref<Country>();
const currencies = ref<Currency[]>([]); // If using Composition API
const selectedCurrency = ref<Currency>()

const loading = ref(true);
const showAddCurrencyPopup = ref(false);
const showUpdateCurrencyPopup = ref(false);

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
      await getCurrencies(selectedCountry.value.id);
    }
  } catch (error) {
    console.error("Error fetching countries:", error);
    countries.value = [];
  } finally {
    loading.value = false;



  }
}

const getCurrencies = async (id: string) => {
  try {
    const response = await apiService.get1(`/ref-data/countries/${id}/currencies`);
    currencies.value = response.data || [];
  } catch (error) {
    console.error("Error fetching makes:", error);
    currencies.value = [];
  } finally {
    loading.value = false;

  }
};



const handleCurrencyAdded = async () => {
  showAddCurrencyPopup.value = false;
  await getCurrencies(selectedCountry.value?.id!!); // Refresh the role list
};


const toggleAddCurrencyPopup = () => {
  showAddCurrencyPopup.value = !showAddCurrencyPopup.value;
};


const toggleUpdateCurrencyPopup = (engine: Currency) => {
  selectedCurrency.value = engine
  showUpdateCurrencyPopup.value = !showUpdateCurrencyPopup.value;
};

const handleCurrencyUpdated = async () => {
  showUpdateCurrencyPopup.value = false;
  await getCurrencies(selectedCountry.value?.id!!); // Refresh the role list

};







</script>

<template>
  <div class="relative min-h-[500px]">


    <AddCurrencyPopup
        v-if="showAddCurrencyPopup"
        @close="toggleAddCurrencyPopup"
        @currency-added="handleCurrencyAdded"
        :id="selectedCountry?.id!!"
    />

    <UpdateCurrencyPopup
        v-if="showUpdateCurrencyPopup"
        @close="toggleUpdateCurrencyPopup"
        @currency-updated="handleCurrencyUpdated"
        :id="selectedCurrency?.id!!"
        :name_en="selectedCurrency?.name_en!!"
        :name_ar="selectedCurrency?.name_ar!!"

    />

    <div class="flex justify-center items-center">
      <div class="w-full p-4 text-center text-gray-400">
        <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
          <div class="group flex-col inset-px shadow-md">
            <!--Divider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>
            <div class="flex justify-between items-center bg-gray-100">
              <div class="flex px-4 text-pink-500 font-bold text-2xl">Currencies</div>


              <div class="flex p-4 text-pink-500 font-bold text-2xl ">
                <select
                    class="hover:bg-pink-100 border-2 border-pink-200 focus:border-pink-700 focus:ring-0 focus:outline-none"
                    v-model="selectedCountry"
                    @change="getCurrencies(selectedCountry?.id!!)"
                >
                  <option
                      v-for="(item, index) in countries"
                      :key="index"
                      :value="item"
                  >
                    {{ item.name_en }}
                  </option>
                </select>

                <IconButton icon="fas fa-plus" label="Add" class="hover:bg-pink-100" @click="toggleAddCurrencyPopup"/>
              </div>
            </div>
            <!--Divider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>

            <!-- User Items -->
            <div v-if="!loading">
              <CurrencyItem
                  v-for="(item, index) in currencies"
                  :key="index"
                  :name_en="item.name_en"
                  :name_ar="item.name_ar"
                  @click="() => toggleUpdateCurrencyPopup(item)"
               id=""/>
            </div>
            <div v-else class="p-4 text-gray-500">Loading currencies...</div>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<style scoped>
</style>