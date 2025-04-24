<script setup lang="ts">
import IconButton from "../../components/IconButton.vue";
import {onMounted, ref} from "vue";
import apiService from "../../core/services/ApiService.ts";
import StatusItem from "../../components/admin/status/StatusItem.vue";
import UpdateStatusPopup from "../../components/admin/status/UpdateStatusPopup.vue";
import AddStatusPopup from "../../components/admin/status/AddStatusPopup.vue";

export interface Status {
  id: string;
  name_en: string;
  name_ar: string;
  // add other properties if your role object has them
}
const statuses = ref<Status[]>([]); // If using Composition API
const selectedStatus = ref<Status>()

const loading = ref(true);
const showAddStatusPopup = ref(false);
const showUpdateStatusPopup = ref(false);

onMounted(async () => {
  await handlePageChange()
});



const handlePageChange = async () => {
  try {
    const response = await apiService.get1("/ref-data/status");
    statuses.value = response.data || [];
  } catch (error) {
    console.error("Error fetching statuses:", error);
    statuses.value = [];
  } finally {
    loading.value = false;
  }
};

const handleStatusAdded = async () => {
  showAddStatusPopup.value = false;
  await handlePageChange(); // Refresh the role list
};


const toggleAddStatusPopup = () => {
  showAddStatusPopup.value = !showAddStatusPopup.value;
};


const toggleUpdateStatusPopup = (engine: Status) => {
  selectedStatus.value = engine
  showUpdateStatusPopup.value = !showUpdateStatusPopup.value;
};

const handleStatusUpdated = async () => {
  showUpdateStatusPopup.value = false;
  await handlePageChange(); // Refresh the role list

};







</script>

<template>
  <div class="relative min-h-[500px]">


    <AddStatusPopup
        v-if="showAddStatusPopup"
        @close="toggleAddStatusPopup"
        @status-added="handleStatusAdded"
    />

    <UpdateStatusPopup
        v-if="showUpdateStatusPopup"
        @close="toggleUpdateStatusPopup"
        @status-updated="handleStatusUpdated"
        :id="selectedStatus?.id!!"
        :name_en="selectedStatus?.name_en!!"
        :name_ar="selectedStatus?.name_ar!!"

    />

    <div class="flex justify-center items-center">
      <div class="w-full p-4 text-center text-gray-400">
        <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
          <div class="group flex-col inset-px shadow-md">
            <!--Divider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>
            <div class="flex justify-between items-center bg-gray-100">
              <div class="flex px-4 text-pink-500 font-bold text-2xl">Status</div>

              <div class="flex p-4 text-pink-500 font-bold text-2xl">
                   <IconButton icon="fas fa-plus" label="Add" class="hover:bg-pink-100" @click="toggleAddStatusPopup"/>
              </div>
            </div>
            <!--Divider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>

            <!-- User Items -->
            <div v-if="!loading">
              <StatusItem
                  v-for="(engine, index) in statuses"
                  :key="index"
                  :name_en="engine.name_en"
                  :name_ar="engine.name_ar"
                  @click="() => toggleUpdateStatusPopup(engine)"
               id=""/>
            </div>
            <div v-else class="p-4 text-gray-500">Loading statuses...</div>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<style scoped>
</style>