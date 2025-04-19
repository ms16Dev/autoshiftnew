<script setup lang="ts">
import { ref, onMounted } from 'vue';
import apiService from "../../../core/services/ApiService.ts";

const props = defineProps<{
  id: string,
  name: string
}>();

const emit = defineEmits(['close', 'role-updated']);

const rolename = ref('');

// Populate the input when component mounts or props change
onMounted(() => {
  rolename.value = props.name;
});

const handleSubmit = async () => {
  try {
    // Use PUT for updates (since we're modifying an existing role)
    await apiService.update("/ref-data/roles", props.id, {
      name: rolename.value
    });

    // Emit event that user was updated
    emit('role-updated');

    // Reset form
    rolename.value = '';

  } catch (error) {
    console.error("Error updating role:", error);
    // You might want to add error handling here
  }
};

const handleDelete = async () => {
  try {
    // Use PUT for updates (since we're modifying an existing role)
    await apiService.delete(`/ref-data/roles/${props.id}`);

    // Emit event that user was updated
    emit('role-updated');

    // Reset form
    rolename.value = '';

  } catch (error) {
    console.error("Error updating role:", error);
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

    <h2 class="text-xl font-bold text-white mb-4 text-center">Update Role</h2>
    <form @submit.prevent="handleSubmit">
      <div class="mb-4">
        <label for="rolename" class="block text-white">Role name</label>
        <input
            type="text"
            id="rolename"
            v-model="rolename"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
      </div>

      <div class="flex gap-3 justify-end">
        <button
            type="submit"
            class="bg-pink-500 text-white font-bold py-2 px-4 rounded hover:bg-pink-900 transition duration-200"
        >
          Update Role
        </button>


      </div>


    </form>

    <button
        @click="handleDelete"
        class="bg-pink-500 text-white font-bold py-2 px-4 rounded hover:bg-pink-900 transition duration-200"
    >
      Delete Role
    </button>
  </div>
</template>

<style scoped>
/* You can add any custom styles here if needed */
</style>