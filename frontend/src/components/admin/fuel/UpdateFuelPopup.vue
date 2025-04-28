<script setup lang="ts">
import { ref, onMounted } from 'vue';
import apiService from "../../../core/services/ApiService.ts";


const props = defineProps<{
  id: string,
  name_en: string,
  name_ar: string,
}>()


const emit = defineEmits(['close', 'fuel-updated']);

const fuelId = ref('');
const fuelNameEn = ref('');
const fuelNameAr = ref('');

// Populate the input when component mounts or props change
onMounted(() => {
  fuelId.value = props.id;
  fuelNameEn.value = props.name_en;
  fuelNameAr.value = props.name_ar;
});

const handleSubmit = async () => {
  try {


    // Use PUT for updates (since we're modifying an existing role)
    await apiService.update("/ref-data/fuel", props.id, {
      id: fuelId.value,
      name_en: fuelNameEn.value,
      name_ar: fuelNameAr.value,
    });

    // Emit event that user was updated
    emit('fuel-updated');



  } catch (error) {
    console.error("Error updating fuel:", error);
    // You might want to add error handling here
  }
};

const handleDelete = async () => {
  try {
    // Use PUT for updates (since we're modifying an existing role)
    await apiService.delete(`/ref-data/fuel/${props.id}`);

    // Emit event that user was updated
    emit('fuel-updated');



  } catch (error) {
    console.error("Error updating fuel:", error);
    // You might want to add error handling here
  }
};

const handleClose = () => {
  emit('close');
};


</script>

<template>
  <div class="absolute bg-pink-700 shadow-lg p-6 h-full w-full z-50">
    <!-- Back button at top left -->
    <button
        @click="handleClose"
        class="absolute top-0 left-0 w-12 h-12 text-white hover:bg-white hover:text-pink-700 transition duration-200"
        aria-label="Close"
    >
      <i class="fas fa-arrow-left text-xl"></i>
    </button>

    <h2 class="text-xl font-bold text-white mb-4 text-center">Update Fuel</h2>
    <form @submit.prevent="handleSubmit">
      <div class="mb-4">
        <label for="fuelId" class="block text-white">Fuel id</label>
        <input
            disabled
            type="text"
            id="fuelId"
            v-model="fuelId"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
      </div>
      <div class="mb-4">
        <label for="makeNameEn" class="block text-white">Fuel name (En)</label>
        <input
            type="text"
            id="makeNameEn"
            v-model="fuelNameEn"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
      </div>
      <div class="mb-4">
        <label for="makeNameAr" class="block text-white">Fuel name (Ar)</label>
        <input
            type="text"
            id="makeNameAr"
            v-model="fuelNameAr"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
      </div>

      <div class="flex gap-3 justify-end">
        <button
            type="submit"
            class="bg-pink-500 text-white font-bold py-2 px-4 rounded hover:bg-pink-900 transition duration-200"
        >
          Update Fuel
        </button>


      </div>


    </form>

    <div class="flex gap-3 justify-end mt-4">
    <button
        @click="handleDelete"
        class="bg-pink-500  text-white font-bold py-2 px-4 rounded hover:bg-pink-900 transition duration-200"
    >
      Delete Fuel
    </button>
    </div>
  </div>
</template>

<style scoped>
/* You can add any custom styles here if needed */
</style>