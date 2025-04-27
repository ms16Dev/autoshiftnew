<script setup lang="ts">
import IconButton from "../../components/IconButton.vue";
import {onMounted, ref} from "vue";
import apiService from "../../core/services/ApiService.ts";
import MakeItem from "../../components/admin/makes/MakeItem.vue";
import UpdateMakePopup from "../../components/admin/makes/UpdateMakePopup.vue";
import AddMakePopup from "../../components/admin/makes/AddMakePopup.vue";

interface Make {
  id: string;
  name_en: string;
  name_ar: string;
  url: string
  // add other properties if your role object has them
}

const makes = ref<Make[]>([]); // If using Composition API
const selectedMake = ref<Make>()

const loading = ref(true);
const showAddMakePopup = ref(false);
const showUpdateMakePopup = ref(false);

onMounted(async () => {
  await handlePageChange()
});

const handlePageChange = async () => {
  try {
    const response = await apiService.get1("/ref-data/makes");
    makes.value = response.data || [];
  } catch (error) {
    console.error("Error fetching makes:", error);
    makes.value = [];
  } finally {
    loading.value = false;
  }
};

const toggleAddMakePopup = () => {
  showAddMakePopup.value = !showAddMakePopup.value;
};

const handleMakeAdded = async () => {
  showAddMakePopup.value = false;
  await handlePageChange(); // Refresh the role list
};


const toggleUpdateMakePopup = (make: Make) => {
  selectedMake.value = make
  showUpdateMakePopup.value = !showUpdateMakePopup.value;
};

const handleMakeUpdated = async () => {
  showUpdateMakePopup.value = false;
  await handlePageChange(); // Refresh the role list
};
</script>

<template>
  <div class="relative min-h-[500px]">
    <AddMakePopup
        v-if="showAddMakePopup"
        @close="toggleAddMakePopup"
        @role-added="handleMakeAdded"
    />

    <UpdateMakePopup
        v-if="showUpdateMakePopup"
        @close="toggleUpdateMakePopup"
        @make-updated="handleMakeUpdated"
        :id="selectedMake?.id!!"
        :name_en="selectedMake?.name_en!!"
        :name_ar="selectedMake?.name_ar!!"
        :url="selectedMake?.url!!"

    />

    <div class="flex justify-center items-center">
      <div class="w-full p-4 text-center text-gray-400">
        <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
          <div class="group flex-col inset-px shadow-md">
            <!--Divider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>
            <div class="flex justify-between items-center bg-gray-100">
              <div class="flex px-4 text-pink-500 font-bold text-2xl">Makes</div>
              <div class="flex p-4 text-pink-500 font-bold text-2xl">
                <IconButton icon="fas fa-plus" label="Add" class="hover:bg-pink-100" @click="toggleAddMakePopup"/>

              </div>
            </div>
            <!--Divider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>

            <!-- User Items -->
            <div v-if="!loading">
              <MakeItem
                  v-for="(make, index) in makes"
                  :key="index"
                  :name_en="make.name_en"
                  :name_ar="make.name_ar"
                  :url="make.url"
                  @click="() => toggleUpdateMakePopup(make)"
               id=""/>
            </div>
            <div v-else class="p-4 text-gray-500">Loading makes...</div>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<style scoped>
</style>