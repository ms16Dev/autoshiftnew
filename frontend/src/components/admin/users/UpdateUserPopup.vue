<script setup lang="ts">
import {onMounted, ref, watch} from 'vue';
import apiService from "../../../core/services/ApiService.ts";
import { UserListDto } from "../../../core/models/UserListDto.ts";

const props = defineProps<{
  user: UserListDto | null;
}>();

const emit = defineEmits(['close', 'user-updated']);

// Initialize with empty values
const email = ref('');
const active = ref(false);
const password = ref('');
const errorMessage = ref('');
const selectedRoles = ref<Record<string, boolean>>({});
const userRoles = ref<{ id: string; name_en: string; name_ar: string }[]>([]); // Fixed to be an array of Role objects

// Watch for user changes and initialize form
watch(() => props.user, (newUser) => {
  if (newUser) {
    email.value = newUser.email;
    active.value = newUser.active;
    selectedRoles.value = newUser.roles.reduce((acc, role) => {
      acc[role] = true;
      return acc;
    }, {} as Record<string, boolean>);
  } else {
    // Reset form if user is null
    email.value = '';
    active.value = false;
    selectedRoles.value = {};
  }
}, { immediate: true });


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
  if (!props.user) return; // Guard against null user

  try {
    const rolesArray = Object.keys(selectedRoles.value)
        .filter(roleName => selectedRoles.value[roleName]);

    const updateData = {
      username: props.user.username,
      email: email.value,
      active: active.value,
      roles: rolesArray,
      ...(password.value && { password: password.value })
    };

    await apiService.update(`/users`, `${props.user.username}`, updateData);
    emit('user-updated');
    emit('close');
  } catch (error: any) {
    errorMessage.value = error.response?.data?.message || error.message || 'Update failed';
  }
};
</script>

<template>
  <div v-if="user" class="absolute bg-pink-700 shadow-lg p-6 h-full w-full z-50">
    <button @click="$emit('close')" class="absolute top-0 left-0 w-12 h-12 text-white hover:bg-white hover:text-pink-700">
      <i class="fas fa-arrow-left text-xl"></i>
    </button>

    <h2 class="text-xl font-bold text-white mb-4 text-center">Edit User: {{ user.username }}</h2>

    <form @submit.prevent="handleSubmit">
      <div class="mb-4">
        <label class="block text-white">Username</label>
        <input :value="user.username" disabled class="border border-gray-300 rounded px-3 py-2 w-full bg-gray-200">
      </div>

      <div class="mb-4">
        <label class="block text-white">New Password (leave blank to keep current)</label>
        <input
            type="password"
            v-model="password"
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
      </div>

      <div class="mb-4">
        <label class="block text-white">Email</label>
        <input
            type="email"
            v-model="email"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
      </div>

      <div class="mb-4">
        <label class="block text-white mb-2">Status</label>
        <label class="inline-flex items-center">
          <input type="checkbox" v-model="active" class="rounded border-gray-300 text-pink-600">
          <span class="ml-2 text-white">Active</span>
        </label>
      </div>

      <div v-for="role in userRoles" :key="role.name_en" class="flex items-center justify-between">
        <span class="text-white">{{ role.name_en }}</span>
        <label class="flex items-center cursor-pointer">
          <input type="checkbox" v-model="selectedRoles[role.name_en]" class="sr-only" />
          <div class="w-10 h-5 bg-gray-400 rounded-full transition-colors relative"
               :class="{ 'bg-pink-500': selectedRoles[role.name_en] }">
            <div class="absolute w-4 h-4 bg-white rounded-full top-0.5 transition-transform"
                 :class="{ 'translate-x-5': selectedRoles[role.name_en], 'left-0.5': !selectedRoles[role.name_en] }">
            </div>
          </div>
        </label>
      </div>
      <div v-if="errorMessage" class="text-red-300 mb-4">{{ errorMessage }}</div>

      <div class="flex justify-end">
        <button type="submit" class="bg-pink-500 text-white font-bold py-2 px-4 rounded hover:bg-pink-900">
          Update User
        </button>
      </div>
    </form>
  </div>
</template>