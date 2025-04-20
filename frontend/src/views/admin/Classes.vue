<script setup lang="ts">
import IconButton from "../../components/IconButton.vue";
import {onMounted, ref, toRaw} from "vue";
import apiService from "../../core/services/ApiService.ts";
import Class_Item from "../../components/admin/classes/ClassItem.vue";
import UpdateClass_Popup from "../../components/admin/classes/UpdateClassPopup.vue";
import AddClass_Popup from "../../components/admin/classes/AddClassPopup.vue";
import MakePopUp, {Make} from "../../components/car/MakePopUp.vue";

export interface Class_ {
  id: string;
  name_en: string;
  name_ar: string;
  // add other properties if your role object has them
}
const makePopUp = ref(false)
const makes = ref<Make[]>([]); // If using Composition API
const selectedMake = ref<Make>();
const classes = ref<Class_[]>([]); // If using Composition API
const selectedClass_ = ref<Class_>()

const loading = ref(true);
const showAddClass_Popup = ref(false);
const showUpdateClass_Popup = ref(false);

onMounted(async () => {
  await getMakes()
});


const getMakes = async () => {
  try {
    const response = await apiService.get1("/ref-data/makes");
    makes.value = response.data || [];
  } catch (error) {
    console.error("Error fetching makes:", error);
    makes.value = [];
  } finally {
    loading.value = false;
    selectedMake.value = makes.value[0]
    await getClasses(selectedMake.value.id)


  }
};

const getClasses = async (id: string) => {
  try {
    const response = await apiService.get1(`/ref-data/makes/${id}/classes`);
    classes.value = response.data || [];
  } catch (error) {
    console.error("Error fetching makes:", error);
    classes.value = [];
  } finally {
    loading.value = false;

  }
};

const toggleAddClass_Popup = () => {
  showAddClass_Popup.value = !showAddClass_Popup.value;
};

const handleClass_Added = async () => {
  showAddClass_Popup.value = false;
  await getClasses(selectedMake.value.id)
};


const toggleUpdateClass_Popup = (class_: Class_) => {
  selectedClass_.value = class_
  showUpdateClass_Popup.value = !showUpdateClass_Popup.value;
};

const handleClass_Updated = async () => {
  showUpdateClass_Popup.value = false;
  await getClasses(selectedMake.value.id)

};

const setMake = (value: Make) =>{
  // Get raw value from Proxy
  const rawValue = toRaw(value);

  // Create typed object
  const make: Make = {
    ...rawValue,
  };

  selectedMake.value = make
  getClasses(make.id)
  toggleMake()

}

const toggleMake = () =>{
  makePopUp.value = !makePopUp.value
}



</script>

<template>
  <div class="relative min-h-[500px]">
    <MakePopUp
        v-if="makePopUp"
        @choice="(value) => setMake (value)"
        :makes="makes"
        position="bottom"></MakePopUp>

    <AddClass_Popup
        v-if="showAddClass_Popup"
        @close="toggleAddClass_Popup"
        @class-added="handleClass_Added"
        :id="selectedMake.id"
    />

    <UpdateClass_Popup
        v-if="showUpdateClass_Popup"
        @close="toggleUpdateClass_Popup"
        @class-updated="handleClass_Updated"
        :id="selectedClass_.id"
        :name_en="selectedClass_.name_en"
        :name_ar="selectedClass_.name_ar"

    />

    <div class="flex justify-center items-center">
      <div class="w-full p-4 text-center text-gray-400">
        <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
          <div class="group flex-col inset-px shadow-md">
            <!--Devider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>
            <div class="flex justify-between items-center bg-gray-100">
              <div class="flex px-4 text-pink-500 font-bold text-2xl">Classes</div>
              <button  @click="toggleMake" class="rounded-full ring-2 ring-pink-700 h-24 w-24 bg-gray-100 -translate-x-1/4  overflow-hidden">
                <img :src="'http://localhost:8080'+ selectedMake?.url">
              </button>
              <div class="flex p-4 text-pink-500 font-bold text-2xl">
                   <IconButton icon="fas fa-plus" label="Add" class="hover:bg-pink-100" @click="toggleAddClass_Popup"/>
              </div>
            </div>
            <!--Devider-->
            <div class="w-full border-b-2 border-b-pink-700"></div>

            <!-- User Items -->
            <div v-if="!loading">
              <Class_Item
                  v-for="(class_, index) in classes"
                  :key="index"
                  :name_en="class_.name_en"
                  :name_ar="class_.name_ar"
                  @click="() => toggleUpdateClass_Popup(class_)"
               id=""/>
            </div>
            <div v-else class="p-4 text-gray-500">Loading classes...</div>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<style scoped>
</style>