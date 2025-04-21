<script setup lang="ts">
import IconButton from "../../components/IconButton.vue";
import {onMounted, ref} from "vue";
import apiService from "../../core/services/ApiService.ts";
import ShapeItem from "../../components/admin/shapes/ShapeItem.vue";
import UpdateShapePopup from "../../components/admin/shapes/UpdateShapePopup.vue";
import AddShapePopup from "../../components/admin/shapes/AddShapePopup.vue";

export interface Shape {
  id: string;
  name_en: string;
  name_ar: string;
  // add other properties if your role object has them
}
const shapes = ref<Shape[]>([]); // If using Composition API
const selectedShape = ref<Shape>()

const loading = ref(true);
const showAddShapePopup = ref(false);
const showUpdateShapePopup = ref(false);

onMounted(async () => {
  await handlePageChange()
});



const handlePageChange = async () => {
  try {
    const response = await apiService.get1("/ref-data/shapes");
    shapes.value = response.data || [];
  } catch (error) {
    console.error("Error fetching shapes:", error);
    shapes.value = [];
  } finally {
    loading.value = false;
  }
};

const handleShapeAdded = async () => {
  showAddShapePopup.value = false;
  await handlePageChange(); // Refresh the role list
};


const toggleAddShapePopup = () => {
  showAddShapePopup.value = !showAddShapePopup.value;
};


const toggleUpdateShapePopup = (engine: Shape) => {
  selectedShape.value = engine
  showUpdateShapePopup.value = !showUpdateShapePopup.value;
};

const handleShapeUpdated = async () => {
  showUpdateShapePopup.value = false;
  await handlePageChange(); // Refresh the role list

};







</script>

<template>
  <div class="relative min-h-[500px]">


    <AddShapePopup
        v-if="showAddShapePopup"
        @close="toggleAddShapePopup"
        @shape-added="handleShapeAdded"
    />

    <UpdateShapePopup
        v-if="showUpdateShapePopup"
        @close="toggleUpdateShapePopup"
        @shape-updated="handleShapeUpdated"
        :id="selectedShape.id"
        :name_en="selectedShape.name_en"
        :name_ar="selectedShape.name_ar"

    />

    <div class="flex justify-center items-center">
      <div class="w-full p-4 text-center text-gray-400">
        <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
          <div class="group flex-col inset-px shadow-md">
            <!--Devider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>
            <div class="flex justify-between items-center bg-gray-100">
              <div class="flex px-4 text-pink-500 font-bold text-2xl">Shapes</div>

              <div class="flex p-4 text-pink-500 font-bold text-2xl">
                   <IconButton icon="fas fa-plus" label="Add" class="hover:bg-pink-100" @click="toggleAddShapePopup"/>
              </div>
            </div>
            <!--Devider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>

            <!-- User Items -->
            <div v-if="!loading">
              <ShapeItem
                  v-for="(engine, index) in shapes"
                  :key="index"
                  :name_en="engine.name_en"
                  :name_ar="engine.name_ar"
                  @click="() => toggleUpdateShapePopup(engine)"
               id=""/>
            </div>
            <div v-else class="p-4 text-gray-500">Loading shapes...</div>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<style scoped>
</style>