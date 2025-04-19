<script setup lang="ts">
import { ref } from 'vue';
import apiService from "../../../core/services/ApiService.ts";
import ImageLoader from "../../ImageLoader.vue";
import axios from "axios";

const emit = defineEmits(['close', 'role-added']);

const makeNameEn = ref('');
const makeNameAr = ref('');
const MakeImage = ref(new FormData());


const handleSubmit = async () => {
  try {

    const response1 = await axios.post('http://localhost:8080/media/image', MakeImage.value, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });


    console.log("Upload success:", response1.data);
    const imageUrl = response1.data.urls[0]


    await apiService.post("/ref-data/makes", {
      name_en: makeNameEn.value,
      name_ar: makeNameAr.value,
      url: imageUrl,

    });

    // Emit event that user was added
    emit('role-added');




  } catch (error) {
    console.error("Error adding user:", error);
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

    <h2 class="text-xl font-bold text-white mb-4 text-center">Add Make</h2>
    <form @submit.prevent="handleSubmit">
      <div class="mb-4">
        <label for="makeNameEn" class="block text-white">Make name (En)</label>
        <input
            type="text"
            id="makeNameEn"
            v-model="makeNameEn"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
      </div>
      <div class="mb-4">
        <label for="makeNameAr" class="block text-white">Make name (Ar)</label>
        <input
            type="text"
            id="makeNameAr"
            v-model="makeNameAr"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
      </div>

      <label for="makeImage" class="block text-white">Make Image</label>

      <image-loader @file-selected="(value) => MakeImage = value"  class="w-24 h-24"/>

      <div class="flex justify-end">
        <button
            type="submit"
            class="bg-pink-500 text-white font-bold py-2 px-4 rounded hover:bg-pink-900 transition duration-200"
        >
          Add Make
        </button>
      </div>
    </form>
  </div>
</template>

<style scoped>
/* You can add any custom styles here if needed */
</style>