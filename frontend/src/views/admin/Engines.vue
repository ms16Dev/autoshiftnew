<script setup lang="ts">
import IconButton from "../../components/IconButton.vue";
import {onMounted, ref} from "vue";
import apiService from "../../core/services/ApiService.ts";
import EngineItem from "../../components/admin/engines/EngineItem.vue";
import UpdateEnginePopup from "../../components/admin/engines/UpdateEnginePopup.vue";
import AddEnginePopup from "../../components/admin/engines/AddEnginePopup.vue";

export interface Engine {
  id: string;
  name_en: string;
  name_ar: string;
  // add other properties if your role object has them
}
const engines = ref<Engine[]>([]); // If using Composition API
const selectedEngine = ref<Engine>()

const loading = ref(true);
const showAddEnginePopup = ref(false);
const showUpdateEnginePopup = ref(false);

onMounted(async () => {
  await handlePageChange()
});



const handlePageChange = async () => {
  try {
    const response = await apiService.get1("/ref-data/engines");
    engines.value = response.data || [];
  } catch (error) {
    console.error("Error fetching engines:", error);
    engines.value = [];
  } finally {
    loading.value = false;
  }
};

const handleEngineAdded = async () => {
  showAddEnginePopup.value = false;
  await handlePageChange(); // Refresh the role list
};


const toggleAddEnginePopup = () => {
  showAddEnginePopup.value = !showAddEnginePopup.value;
};


const toggleUpdateEnginePopup = (engine: Engine) => {
  selectedEngine.value = engine
  showUpdateEnginePopup.value = !showUpdateEnginePopup.value;
};

const handleEngineUpdated = async () => {
  showUpdateEnginePopup.value = false;
  await handlePageChange(); // Refresh the role list

};







</script>

<template>
  <div class="relative min-h-[500px]">


    <AddEnginePopup
        v-if="showAddEnginePopup"
        @close="toggleAddEnginePopup"
        @engine-added="handleEngineAdded"
    />

    <UpdateEnginePopup
        v-if="showUpdateEnginePopup"
        @close="toggleUpdateEnginePopup"
        @engine-updated="handleEngineUpdated"
        :id="selectedEngine.id"
        :name_en="selectedEngine.name_en"
        :name_ar="selectedEngine.name_ar"

    />

    <div class="flex justify-center items-center">
      <div class="w-full p-4 text-center text-gray-400">
        <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
          <div class="group flex-col inset-px shadow-md">
            <!--Devider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>
            <div class="flex justify-between items-center bg-gray-100">
              <div class="flex px-4 text-pink-500 font-bold text-2xl">Engines</div>

              <div class="flex p-4 text-pink-500 font-bold text-2xl">
                   <IconButton icon="fas fa-plus" label="Add" class="hover:bg-pink-100" @click="toggleAddEnginePopup"/>
              </div>
            </div>
            <!--Devider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>

            <!-- User Items -->
            <div v-if="!loading">
              <EngineItem
                  v-for="(engine, index) in engines"
                  :key="index"
                  :name_en="engine.name_en"
                  :name_ar="engine.name_ar"
                  @click="() => toggleUpdateEnginePopup(engine)"
               id=""/>
            </div>
            <div v-else class="p-4 text-gray-500">Loading engines...</div>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<style scoped>
</style>