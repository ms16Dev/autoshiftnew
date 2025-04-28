<script setup lang="ts">
import { ref } from 'vue';
import apiService from "../../../core/services/ApiService.ts";



const emit = defineEmits(['close', 'engine-added']);

const errorMassage = ref('');
const engineId = ref('');
const engineNameEn = ref('');
const engineNameAr = ref('');


const handleSubmit = async () => {
  try {


    await apiService.post(`/ref-data/engines`, {
      id: engineId.value,
      name_en: engineNameEn.value,
      name_ar: engineNameAr.value,

    });

    // Emit event that user was added
    emit('engine-added');




  } catch (error : any) {
    console.error("Error adding user:", error);
    // You might want to add error handling here
    if (error.response && error.response.data) {
      // Error came from Axios (HTTP response)
      errorMassage.value = error.response.data;
    } else if (error instanceof Error) {
      // Some other normal JavaScript error
      errorMassage.value = error.message;
    } else {
      // Fallback
      errorMassage.value = String(error);
    }
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

    <h2 class="text-xl font-bold text-white mb-4 text-center">Add Engine</h2>
    <form @submit.prevent="handleSubmit">
      <div class="mb-4">
        <label for="engineId" class="block text-white">Engine id</label>
        <input
            type="text"
            id="engineId"
            v-model="engineId"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
        <label class="text-gray-900">{{ errorMassage }}</label>

      </div>
      <div class="mb-4">
        <label for="classNameEn" class="block text-white">Engine name (En)</label>
        <input
            type="text"
            id="classNameEn"
            v-model="engineNameEn"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
      </div>
      <div class="mb-4">
        <label for="classNameAr" class="block text-white">Engine name (Ar)</label>
        <input
            type="text"
            id="classNameAr"
            v-model="engineNameAr"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
      </div>

      <div class="flex justify-end">
        <button
            type="submit"
            class="bg-pink-500 text-white font-bold py-2 px-4 rounded hover:bg-pink-900 transition duration-200"
        >
          Add Engine
        </button>
      </div>
    </form>
  </div>
</template>

<style scoped>
/* You can add any custom styles here if needed */
</style>