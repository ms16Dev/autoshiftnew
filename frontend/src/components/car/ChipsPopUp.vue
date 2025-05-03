<script setup lang="ts">
import {computed, PropType, ref} from "vue";
import ChipItem from "./ChipItem.vue";
import ChipItemChecked from "./ChipItemChecked.vue";
import {DataItem} from "../../types/staticData.ts";

import {useStaticDataStore} from "../../stores/staticDataStore.ts";
const staticData = useStaticDataStore();

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
    type: Array as PropType<DataItem[]>,
    required: true,
  },
  checked: {
    type: Array as PropType<DataItem[]>,
    default: () => [],
  },
});

const emit = defineEmits(['choice', 'close']);

// Reactive checked state from props
const checked = ref<DataItem[]>([...props.checked]);

// Save selected options (string array)
const saveOptions = () => {
  emit('choice', checked.value);
};

function toggleOption(value: DataItem, fromChecked: boolean) {
  if (fromChecked) {
    checked.value = checked.value.filter(v => v.id !== value.id);
  } else {
    const alreadyExists = checked.value.some(v => v.id === value.id);
    if (!alreadyExists) {
      checked.value.push(value);
    }
  }
}


const isRtl = computed(() => document.documentElement.dir === 'rtl');


const availableOptions = computed(() => {
  const checkedIds = checked.value.map(item => item.id);
  return props.options.filter(opt => !checkedIds.includes(opt.id));
});

</script>

<template>
  <div
      :class="[
      'absolute overflow-y-scroll scrollbar-none bg-pink-700 z-100 w-full h-full',
      position === 'bottom' ? 'top-0 end-0' : '',
      position === 'top' ? 'bottom-full end-0' : '',
      position === 'left' ? 'mr-2 right-full top-0' : '',
      position === 'right' ? 'ml-2 left-full top-0' : '',
    ]"
  >
    <div class="overflow-y-scroll h-full scrollbar-none">
      <div class="flex items-center h-24">
        <button @click="emit('close')" class="w-24 h-24 hover:bg-pink-500 flex items-center justify-center">
          <span class="text-white text-2xl" :class="{
            'fas fa-arrow-right': isRtl,
            'fas fa-arrow-left': !isRtl
          }"></span>
        </button>
        <h1 class="text-white text-2xl text-center">{{ title }}</h1>
      </div>

      <!-- Checked chips -->
      <div class="flex flex-wrap">
        <ChipItemChecked
            v-for="(opt, index) in checked"
            :key="index"
            :option="staticData.getLocalizedName(opt)"
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
            :option="staticData.getLocalizedName(opt)"
            class="p-2 text-white hover:bg-pink-500"
            @click="toggleOption(opt, false)"
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
