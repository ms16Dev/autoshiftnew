<script setup lang="ts">
import { ref } from "vue";
import MakeItem from "./MakeItem.vue";

// Define the Make interface
export interface Make {
  name: string;
  name_ar: string;
  url: string;
}

// Define props with proper typing
const props = defineProps({
  position: {
    type: String,
    default: "bottom", // Can be 'top', 'bottom', 'left', 'right'
  },
  makes: {
    type: Array as () => Make[],
    default: () => [],
  },
});

const emit = defineEmits(["close", "choice"]);

// Local state for the selected make
const make = ref<Make | null>(null);

// Emits close and save events
const saveMake = (value: number) => {
  // Assign a selected make from the makes array
  make.value = props.makes[value];
  // Emit the selected make to the parent
  emit("choice", make.value);
};
</script>


<template>
  <div
      :class="[
      'absolute  bg-pink-700 z-100 w-full h-full ',
      position === 'bottom' ? 'top-0 end-0' : '',
      position === 'top' ? 'bottom-full end-0' : '',
      position === 'left' ? 'mr-2 right-full top-0' : '',
      position === 'right' ? 'ml-2 left-full top-0' : '',
    ]"
  >
    <div class="h-full flex-col grid grid-cols-2 gap-4 overflow-y-scroll scrollbar-none ">
        <MakeItem  v-for="(make, index) in makes" :key="index" :url="`/public/carmakes/${make.url}`" class="h-full p-2 hover:bg-white" @click="saveMake(index)"></MakeItem>
    </div>




  </div>
</template>

<style scoped>
</style>
