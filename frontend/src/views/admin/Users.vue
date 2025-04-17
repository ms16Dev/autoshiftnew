<script setup lang="ts">
import IconButton from "../../components/IconButton.vue";
import UserItem from "../../components/admin/users/UserItem.vue";
import {onMounted, ref} from "vue";
import {UserListDto} from "../../core/models/UserListDto.ts";
import apiService from "../../core/services/ApiService.ts";
import AddUserPopup from "../../components/admin/users/AddUserPopup.vue";

const users = ref<UserListDto[]>([]);
const loading = ref(true);
const showAddUserPopup = ref(false);

onMounted(async () => {
  await handlePageChange()
});

const handlePageChange = async () => {
  try {
    const response = await apiService.get1("users");
    users.value = response.data || [];
  } catch (error) {
    console.error("Error fetching users:", error);
    users.value = [];
  } finally {
    loading.value = false;
  }
};

const toggleAddUserPopup = () => {
  showAddUserPopup.value = !showAddUserPopup.value;
};

const handleUserAdded = async () => {
  showAddUserPopup.value = false;
  await handlePageChange(); // Refresh the user list
};
</script>

<template>
  <div class="relative min-h-[400px]">
    <AddUserPopup
        v-if="showAddUserPopup"
        @close="toggleAddUserPopup"
        @user-added="handleUserAdded"
    />

    <div class="flex justify-center items-center">
      <div class="w-full p-4 text-center text-gray-400">
        <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
          <div class="group flex-col inset-px shadow-md">
            <!--Devider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>
            <div class="flex justify-between items-center bg-gray-100">
              <div class="flex px-4 text-pink-500 font-bold text-2xl">Users</div>
              <div class="flex p-4 text-pink-500 font-bold text-2xl">
                <IconButton icon="fas fa-plus" label="Add" class="hover:bg-pink-100" @click="toggleAddUserPopup"/>

              </div>
            </div>
            <!--Devider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>

            <!-- User Items -->
            <div v-if="!loading">
              <UserItem
                  v-for="user in users"
                  :key="user.id"
                  :user="user"
              />
            </div>
            <div v-else class="p-4 text-gray-500">Loading users...</div>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<style scoped>
</style>