<script setup lang="ts">
import {computed, onMounted, ref} from 'vue';
import apiService from "../../core/services/ApiService.ts";
import ImageLoader from "../ImageLoader.vue";
import {Profile} from "../../types/Profile.ts";
import {config} from "../../../config.ts";
import axios from "axios";
import {useStaticDataStore} from "../../stores/staticDataStore.ts";
import {useI18n} from "vue-i18n";
import {Country} from "../../types/staticData.ts";

const {t} = useI18n()

const staticData = useStaticDataStore();

const props = defineProps<{
  username: string,
}>()


const emit = defineEmits(['close', 'profile-updated']);

const username = props.username;
const profile = ref<Profile>({
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
    profile.value = response.data;

  } catch (error) {
    console.error('Error fetching car profile:', error);
  }
};

const handleSubmit = async () => {
  try {

    if (coverImage.value.get('file') != null) {
      const response1 = await axios.post(config.endpoints.images, coverImage.value, {
        headers: {
          'Content-Type': 'multipart/form-data',
        }
      });

      profile.value.coverUrl = response1.data.urls[0];
    }


    if (avatarImage.value.get('file') != null) {

      const response2 = await axios.post(config.endpoints.images, avatarImage.value, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      profile.value.avatarUrl = response2.data.urls[0]

    }

    profile.value.complete = true;

    // Use PUT for updates (since we're modifying an existing role)
    await apiService.update("/profiles", props.username, profile.value);

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

const selectedCountryId = computed(() => {
  return profile.value.country || staticData.getCurrentCountry().id;
});
const selectedCityId = computed(() => {
  return profile.value.location || staticData.getCurrentCountry().cities[0];
});

const selectCountry = (countryId: string) => {
  profile.value.country = countryId;
  selectCity((staticData.findItemById('countries',countryId) as Country).cities[0])

};
const selectCity = (cityId: string) => {
  profile.value.location = cityId;

};

const selectedRoleId = computed(() => {
  return profile.value.dealer ? "ROLE_DEALER" : "ROLE_USER";
});

const selectRole = (roleId: string) => {
  profile.value.dealer = roleId === "ROLE_DEALER";
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

    <h2 class="text-xl font-bold text-white mb-4 text-center">{{ t('edit_profile') }}</h2>
    <form @submit.prevent="handleSubmit">
      <div class="mb-4 text-start">
        <label for="colorId" class="block text-white">{{ t('username') }}</label>
        <input
            disabled
            type="text"
            id="colorId"
            v-model="profile.username"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />


        <label for="coverImage" class="block text-white">{{ t('cover_image') }}</label>

        <image-loader @file-selected="(value) => coverImage = value" :bg="config.apiBaseUrl+profile.coverUrl"
                      class="w-24 h-24"/>

        <label for="avatarImage" class="block text-white">{{ t('avatar_image') }}</label>

        <image-loader @file-selected="(value) => avatarImage = value" :bg="config.apiBaseUrl+profile.avatarUrl"
                      class="w-24 h-24"/>

      </div>
      <div class="mb-4 text-start">
        <div class="country-chips my-6">
          <div class="flex justify-center gap-2 overflow-x-scroll">
            <div
                v-for="country in staticData.data.countries"
                :key="country.id"
                @click="selectCountry(country.id)"
                :class="[
              'px-4 py-2 rounded-full border-2 border-white cursor-pointer transition-colors',
              selectedCountryId === country.id
                ? 'bg-pink-500 text-white'
                : 'bg-pink-700 text-white hover:bg-pink-600'
            ]"
            >
              {{ staticData.getLocalizedName(country) }}
            </div>
          </div>
        </div>
        <div class="country-chips my-6">
          <div class="flex justify-center gap-2 overflow-x-scroll">
            <div
                v-for="city in (staticData.findItemById('countries', selectedCountryId) as Country).cities.map(c => staticData.findItemById('cities',c))"
                :key="city?.id"
                @click="selectCity(city?.id!)"
                :class="[
              'px-4 py-2 rounded-full border-2 border-white cursor-pointer transition-colors',
              selectedCityId === city?.id
                ? 'bg-pink-500 text-white'
                : 'bg-pink-700 text-white hover:bg-pink-600'
            ]"
            >
              {{ staticData.getLocalizedName(city!!) }}
            </div>
          </div>
        </div>
        <div class="country-chips my-6">
          <div class="flex justify-center gap-2">
            <div
                v-for="role in staticData.data.roles.filter(r => r.id !== 'ROLE_ADMIN')"
                :key="role.id"
                @click="selectRole(role.id)"
                :class="[
              'px-4 py-2 rounded-full border-2 border-white cursor-pointer transition-colors',
              selectedRoleId === role.id
                ? 'bg-pink-500 text-white'
                : 'bg-pink-700 text-white hover:bg-pink-600'
            ]"
            >
              {{ staticData.getLocalizedName(role) }}
            </div>
          </div>
        </div>
        <label for="name" class="block text-white">{{ t('name') }}</label>
        <input
            type="text"
            id="name"
            v-model="profile.name"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />

        <label for="subtitle" class="block text-white">{{ t('subtitle') }}</label>
        <input
            type="text"
            id="name"
            v-model="profile.subtitle"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />

        <label for="info" class="block text-white">{{ t('about') }}</label>
        <input
            type="text"
            id="name"
            v-model="profile.info"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
        <label for="contact" class="block text-white">{{ t('contact') }}</label>
        <label class="block text-white">{{ t('tel') }}</label>
        <input
            type="text"
            id="contact"
            v-model="profile.contact[0]"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
        <label class="block text-white">{{ t('facebook') }}</label>
        <input
            type="text"
            id="contact"
            v-model="profile.contact[1]"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
        <label class="block text-white">{{ t('instagram') }}</label>
        <input
            type="text"
            id="contact"
            v-model="profile.contact[2]"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
        <label class="block text-white">{{ t('website') }}</label>
        <input
            type="text"
            id="contact"
            v-model="profile.contact[3]"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
      </div>

      <div class="flex gap-3 justify-end">
        <button
            type="submit"
            class="bg-pink-500 text-white font-bold py-2 px-4 rounded hover:bg-pink-900 transition duration-200"
        >
          {{ t('save') }}
        </button>


      </div>

    </form>

  </div>
</template>

<style scoped>
/* You can add any custom styles here if needed */
</style>