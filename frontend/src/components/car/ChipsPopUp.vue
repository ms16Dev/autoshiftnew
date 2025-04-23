<script setup lang="ts">
import {computed, PropType, ref} from "vue";
import ChipItem from "./ChipItem.vue";
import ChipItemChecked from "./ChipItemChecked.vue";
import { RefOption } from "./CarFormNew.vue";

// Props
const props = defineProps({
  title: {
    type: String,
    default: 'Title',
  },
  position: {
    type: String,
    default: 'bottom',
  },
  options: {
    type: Array as PropType<RefOption[]>,
    required: true,
  },
  checked: {
    type: Array as PropType<string[]>,
    default: () => [],
  },
});

const emit = defineEmits(['choice', 'close']);

// Reactive checked state from props
const checked = ref<string[]>([...props.checked]);

// Save selected options (string array)
const saveOptions = () => {
  emit('choice', checked.value);
};

// Toggle selected/unselected state
function toggleOption(value: string, fromChecked: boolean) {
  if (fromChecked) {
    checked.value = checked.value.filter(v => v !== value);
  } else {
    checked.value.push(value);
  }
}

// Only show options that are NOT checked
const availableOptions = computed(() => {
  return props.options.filter(opt => !checked.value.includes(opt.name_en));
});
</script>

<template>
  <div
      :class="[
      'absolute bg-pink-700 z-100 w-full h-full',
      position === 'bottom' ? 'top-0 end-0' : '',
      position === 'top' ? 'bottom-full end-0' : '',
      position === 'left' ? 'mr-2 right-full top-0' : '',
      position === 'right' ? 'ml-2 left-full top-0' : '',
    ]"
  >
    <div class="overflow-y-scroll h-full scrollbar-none">
      <div class="flex items-center h-24">
        <button @click="emit('close')" class="w-24 h-24 hover:bg-pink-500 flex items-center justify-center">
          <span class="fas fa-arrow-left text-white text-2xl"></span>
        </button>
        <h1 class="text-white text-2xl text-center">{{ title }}</h1>
      </div>

      <!-- Checked chips -->
      <div class="flex flex-wrap">
        <ChipItemChecked
            v-for="(opt, index) in checked"
            :key="index"
            :option="opt"
            class="p-2 text-white hover:bg-pink-500"
            @click="toggleOption(opt, true)"
        />
      </div>

      <div class="my-3 mx-1 border-b-2 border-white"></div>

      <!-- Available options -->
      <div class="flex flex-wrap">
        <ChipItem
            v-for="(opt, index) in availableOptions"
            :key="index"
            :option="opt.name_en"
            class="p-2 text-white hover:bg-pink-500"
            @click="toggleOption(opt.name_en, false)"
        />
      </div>

      <!-- Save -->
      <div class="flex justify-center">
        <button
            @click="saveOptions"
            class="w-32 h-32 text-3xl text-white bg-pink-700 hover:bg-pink-500"
        >
          <i class="fas fa-check"></i>
        </button>
      </div>
    </div>
  </div>
</template>


<style scoped>
</style>
