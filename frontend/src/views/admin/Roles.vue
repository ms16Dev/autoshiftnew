<script setup lang="ts">
import IconButton from "../../components/IconButton.vue";
import {onMounted, ref} from "vue";
import apiService from "../../core/services/ApiService.ts";
import RoleItem from "../../components/admin/roles/RoleItem.vue";
import AddRolePopup from "../../components/admin/roles/AddRolePopup.vue";
import UpdateRolePopup from "../../components/admin/roles/UpdateRolePopup.vue";

interface Role {
  id: string;
  name_en: string;
  name_ar: string;
  // add other properties if your role object has them
}

const roles = ref<Role[]>([]); // If using Composition API
const selectedRole = ref<Role>()

const loading = ref(true);
const showAddRolePopup = ref(false);
const showUpdateRolePopup = ref(false);

onMounted(async () => {
  await handlePageChange()
});

const handlePageChange = async () => {
  try {
    const response = await apiService.get1("/ref-data/roles");
    roles.value = response.data || [];
  } catch (error) {
    console.error("Error fetching roles:", error);
    roles.value = [];
  } finally {
    loading.value = false;
  }
};

const toggleAddRolePopup = () => {
  showAddRolePopup.value = !showAddRolePopup.value;
};

const handleRoleAdded = async () => {
  showAddRolePopup.value = false;
  await handlePageChange(); // Refresh the role list
};


const toggleUpdateRolePopup = (role: Role) => {
  selectedRole.value = role
  showUpdateRolePopup.value = !showUpdateRolePopup.value;
};

const handleRoleUpdated = async () => {
  showUpdateRolePopup.value = false;
  await handlePageChange(); // Refresh the role list
};
</script>

<template>
  <div class="relative min-h-[400px]">
    <AddRolePopup
        v-if="showAddRolePopup"
        @close="toggleAddRolePopup"
        @role-added="handleRoleAdded"
    />

    <UpdateRolePopup
        v-if="showUpdateRolePopup"
        @close="toggleUpdateRolePopup"
        @role-updated="handleRoleUpdated"
        :id="selectedRole?.id!!"
        :name_en="selectedRole?.name_en!!"
        :name_ar="selectedRole?.name_ar!!"

    />

    <div class="flex justify-center items-center">
      <div class="w-full p-4 text-center text-gray-400">
        <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
          <div class="group flex-col inset-px shadow-md">
            <!--Divider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>
            <div class="flex justify-between items-center bg-gray-100">
              <div class="flex px-4 text-pink-500 font-bold text-2xl">Roles</div>
              <div class="flex p-4 text-pink-500 font-bold text-2xl">
                <IconButton icon="fas fa-plus" label="Add" class="hover:bg-pink-100" @click="toggleAddRolePopup"/>

              </div>
            </div>
            <!--Devider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>

            <!-- User Items -->
            <div v-if="!loading">
              <RoleItem
                  v-for="(role, index) in roles"
                  :key="index"
                  :name_en="role.name_en"
                  :name_ar="role.name_ar"
                  :id="role.id"
                  @click="() => toggleUpdateRolePopup(role)"
              />
            </div>
            <div v-else class="p-4 text-gray-500">Loading roles...</div>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<style scoped>
</style>