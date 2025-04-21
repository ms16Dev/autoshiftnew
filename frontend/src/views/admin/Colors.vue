<script setup lang="ts">
import IconButton from "../../components/IconButton.vue";
import {onMounted, ref} from "vue";
import apiService from "../../core/services/ApiService.ts";
import ColorItem from "../../components/admin/colors/ColorItem.vue";
import UpdateColorPopup from "../../components/admin/colors/UpdateColorPopup.vue";
import AddColorPopup from "../../components/admin/colors/AddColorPopup.vue";

export interface Color {
  id: string;
  name_en: string;
  name_ar: string;
  // add other properties if your role object has them
}
const colors = ref<Color[]>([]); // If using Composition API
const selectedColor = ref<Color>()

const loading = ref(true);
const showAddColorPopup = ref(false);
const showUpdateColorPopup = ref(false);

onMounted(async () => {
  await handlePageChange()
});



const handlePageChange = async () => {
  try {
    const response = await apiService.get1("/ref-data/colors");
    colors.value = response.data || [];
  } catch (error) {
    console.error("Error fetching colors:", error);
    colors.value = [];
  } finally {
    loading.value = false;
  }
};

const handleColorAdded = async () => {
  showAddColorPopup.value = false;
  await handlePageChange(); // Refresh the role list
};


const toggleAddColorPopup = () => {
  showAddColorPopup.value = !showAddColorPopup.value;
};


const toggleUpdateColorPopup = (engine: Color) => {
  selectedColor.value = engine
  showUpdateColorPopup.value = !showUpdateColorPopup.value;
};

const handleColorUpdated = async () => {
  showUpdateColorPopup.value = false;
  await handlePageChange(); // Refresh the role list

};







</script>

<template>
  <div class="relative min-h-[500px]">


    <AddColorPopup
        v-if="showAddColorPopup"
        @close="toggleAddColorPopup"
        @color-added="handleColorAdded"
    />

    <UpdateColorPopup
        v-if="showUpdateColorPopup"
        @close="toggleUpdateColorPopup"
        @color-updated="handleColorUpdated"
        :id="selectedColor.id"
        :name_en="selectedColor.name_en"
        :name_ar="selectedColor.name_ar"

    />

    <div class="flex justify-center items-center">
      <div class="w-full p-4 text-center text-gray-400">
        <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
          <div class="group flex-col inset-px shadow-md">
            <!--Devider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>
            <div class="flex justify-between items-center bg-gray-100">
              <div class="flex px-4 text-pink-500 font-bold text-2xl">Colors</div>

              <div class="flex p-4 text-pink-500 font-bold text-2xl">
                   <IconButton icon="fas fa-plus" label="Add" class="hover:bg-pink-100" @click="toggleAddColorPopup"/>
              </div>
            </div>
            <!--Devider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>

            <!-- User Items -->
            <div v-if="!loading">
              <ColorItem
                  v-for="(engine, index) in colors"
                  :key="index"
                  :name_en="engine.name_en"
                  :name_ar="engine.name_ar"
                  @click="() => toggleUpdateColorPopup(engine)"
               id=""/>
            </div>
            <div v-else class="p-4 text-gray-500">Loading colors...</div>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<style scoped>
</style>