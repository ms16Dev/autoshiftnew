<script setup lang="ts">
import { ref, onMounted } from 'vue';
import apiService from "../../core/services/ApiService.ts";
import ImageLoader from "../ImageLoader.vue";
import {Dealer} from "../../types/Dealer.ts";
import {config} from "../../../config.ts";
import axios from "axios";


const props = defineProps<{
  username: string,
}>()


const emit = defineEmits(['close', 'profile-updated']);

const username = props.username;
const dealer = ref<Dealer> ({
  avatarUrl: "",
  complete: false,
  contact: [],
  country: "",
  coverUrl: "",
  createdDate: 0,
  dealer: false,
  id: "",
  info: "",
  lastModifiedDate: 0,
  likeCount: 0,
  location: "",
  name: "",
  subtitle: "",
  username: ""
});

const coverImage = ref(new FormData());
const avatarImage = ref(new FormData());



// Populate the input when component mounts or props change
onMounted(() => {
  fetchDealerProfile();

});

const fetchDealerProfile = async () => {
  try {


    const response = await apiService.get1(`/profiles/${username}`);
    dealer.value = response.data;

  } catch (error) {
    console.error('Error fetching car profile:', error);
  }
};

const handleSubmit = async () => {
  try {

    console.log("Submitting images to:", config.endpoints.images);
    const response1 = await axios.post(config.endpoints.images, coverImage.value, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    dealer.value.coverUrl = response1.data.urls[0]

    const response2 = await axios.post(config.endpoints.images, avatarImage.value, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    dealer.value.avatarUrl = response2.data.urls[0]

    // Use PUT for updates (since we're modifying an existing role)
    await apiService.update("/profiles", props.username, dealer.value);

    // Emit event that user was updated
    emit('profile-updated');



  } catch (error) {
    console.error("Error updating profile:", error);
    // You might want to add error handling here
  }
};


const handleClose = () => {
  emit('close');
};


</script>

<template>
  <div class="absolute bg-pink-700 shadow-lg w-full h-full p-6 z-10 overflow-x-hidden overflow-y-scroll scrollbar-none">
    <!-- Back button at top left -->
    <button
        @click="handleClose"
        class="absolute top-0 left-0 w-12 h-12 text-white hover:bg-white hover:text-pink-700 transition duration-200"
        aria-label="Close"
    >
      <i class="fas fa-arrow-left text-xl"></i>
    </button>

    <h2 class="text-xl font-bold text-white mb-4 text-center">Edit Profile</h2>
    <form @submit.prevent="handleSubmit">
      <div class="mb-4 text-start">
        <label for="colorId" class="block text-white">Username</label>
        <input
            disabled
            type="text"
            id="colorId"
            v-model="dealer.username"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />


        <label for="coverImage" class="block text-white">Cover Image</label>

        <image-loader @file-selected="(value) => coverImage = value" :bg="config.apiBaseUrl+dealer.coverUrl"  class="w-24 h-24"/>

        <label for="avatarImage" class="block text-white">Avatar Image</label>

        <image-loader @file-selected="(value) => avatarImage = value"  :bg="config.apiBaseUrl+dealer.avatarUrl" class="w-24 h-24"/>

      </div>
      <div class="mb-4 text-start">
        <label for="name" class="block text-white">Name</label>
        <input
            type="text"
            id="name"
            v-model="dealer.name"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />

        <label for="subtitle" class="block text-white">Subtitle</label>
        <input
            type="text"
            id="name"
            v-model="dealer.subtitle"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />

        <label for="info" class="block text-white">About</label>
        <input
            type="text"
            id="name"
            v-model="dealer.info"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
        <label for="contact" class="block text-white">Contact</label>
        <label  class="block text-white">Tel</label>
        <input
            type="text"
            id="contact"
            v-model="dealer.contact[0]"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
        <label  class="block text-white">Facebook</label>
        <input
            type="text"
            id="contact"
            v-model="dealer.contact[1]"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
        <label  class="block text-white">Instagram</label>
        <input
            type="text"
            id="contact"
            v-model="dealer.contact[2]"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
        <label  class="block text-white">Website</label>
        <input
            type="text"
            id="contact"
            v-model="dealer.contact[3]"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
      </div>

      <div class="flex gap-3 justify-end">
        <button
            type="submit"
            class="bg-pink-500 text-white font-bold py-2 px-4 rounded hover:bg-pink-900 transition duration-200"
        >
          Save
        </button>


      </div>


    </form>

  </div>
</template>

<style scoped>
/* You can add any custom styles here if needed */
</style>