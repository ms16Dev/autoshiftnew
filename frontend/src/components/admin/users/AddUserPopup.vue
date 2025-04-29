<script setup lang="ts">
import { onMounted, ref } from 'vue';
import apiService from "../../../core/services/ApiService.ts";

export interface Role {
  id: string;
  name_en: string;
  name_ar: string;
}

const emit = defineEmits(['close', 'user-added']);

const userRoles = ref<Role[]>([]); // Fixed to be an array of Role objects
const selectedRoles = ref<Record<string, boolean>>({}); // Track selected roles
const errorMessage = ref(''); // Fixed typo from errorMassage
const username = ref('');
const password = ref('');
const email = ref('');
const active = ref(true);

onMounted(() => {
  getRoles();
});

const getRoles = async () => {
  try {
    const response = await apiService.get1('/ref-data/roles');
    userRoles.value = response.data || [];
    // Initialize selectedRoles with all roles set to false
    userRoles.value.forEach(role => {
      selectedRoles.value[role.name_en] = false;
    });
  } catch (error) {
    console.error("Error fetching roles:", error);
    userRoles.value = [];
  }
};

const handleSubmit = async () => {
  try {
    // Convert selected roles to array of role names
    const rolesArray = Object.entries(selectedRoles.value)
        .filter(([_, isSelected]) => isSelected)
        .map(([roleName]) => roleName);

    await apiService.post(`/users`, {
      username: username.value,
      password: password.value,
      email: email.value,
      active: active.value,
      roles: rolesArray,
    });

    emit('user-added');

  } catch (error: any) {
    console.error("Error adding user:", error);
    if (error.response && error.response.data) {
      errorMessage.value = error.response.data;
    } else if (error instanceof Error) {
      errorMessage.value = error.message;
    } else {
      errorMessage.value = String(error);
    }
  }
};

const handleClose = () => {
  emit('close');
};
</script>

<template>
  <div class="absolute bg-pink-700 shadow-lg p-6 h-full w-full z-50">
    <button
        @click="handleClose"
        class="absolute top-0 left-0 w-12 h-12 text-white hover:bg-white hover:text-pink-700 transition duration-200"
        aria-label="Close"
    >
      <i class="fas fa-arrow-left text-xl"></i>
    </button>

    <h2 class="text-xl font-bold text-white mb-4 text-center">Add User</h2>
    <form @submit.prevent="handleSubmit">
      <div class="mb-4">
        <label for="username" class="block text-white">Username</label>
        <input
            type="text"
            id="username"
            v-model="username"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
        <label class="text-gray-900">{{ errorMessage }}</label>
      </div>

      <div class="mb-4">
        <label for="password" class="block text-white">Password</label>
        <input
        id="password"
        v-model="password"
        required
        class="border border-gray-300 rounded px-3 py-2 w-full"
        />
      </div>

      <div class="mb-4">
        <label for="email" class="block text-white">Email</label>
        <input
            type="email"
            id="email"
            v-model="email"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
      </div>

      <div class="mb-4">
        <label class="block text-white mb-2">Roles</label>
        <div class="space-y-3">
          <div v-for="role in userRoles" :key="role.id" class="flex items-center justify-between">
            <span class="text-white">{{ role.name_en }}</span>
            <label class="flex items-center cursor-pointer">
              <div class="relative">
                <input
                    type="checkbox"
                    v-model="selectedRoles[role.name_en]"
                    class="sr-only"
                >
                <!-- Switch track with border and conditional pink background -->
                <div
                    class="w-10 h-5 rounded-full border border-gray-300 shadow-inner transition-colors"
                    :class="selectedRoles[role.name_en] ? 'bg-pink-500' : 'bg-gray-400'"
                ></div>
                <!-- Switch thumb with border -->
                <div
                    class="absolute w-4 h-4 bg-white rounded-full border border-gray-300 shadow top-0.5 left-0.5 transition-transform"
                    :class="{ 'translate-x-5': selectedRoles[role.name_en] }"
                ></div>
              </div>
            </label>
          </div>
        </div>
      </div>      <div class="flex justify-end">
        <button
            type="submit"
            class="bg-pink-500 text-white font-bold py-2 px-4 rounded hover:bg-pink-900 transition duration-200"
        >
          Add User
        </button>
      </div>
    </form>
  </div>
</template>

<style scoped>
/* You can add any custom styles here if needed */
</style>