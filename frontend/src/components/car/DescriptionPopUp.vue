<script setup lang="ts">
import { ref } from "vue";
import {useI18n} from "vue-i18n";
const { t } = useI18n()

const props = defineProps({
  position: {
    type: String,
    default: 'bottom',
  },
  value: {
    type: String,
    default: "",
  },
});

const emit = defineEmits(['close', 'save']);

const description = ref<string>(props.value);

const saveDescription = () => {
    emit('save', description.value);
};
</script>

<template>
  <div
      :class="[
      'absolute bg-pink-700 z-50 w-full h-full p-4',
      position === 'bottom' ? 'top-0 end-0' : '',
      position === 'top' ? 'bottom-0 end-0' : '',
      position === 'left' ? 'mr-2 right-full top-0' : '',
      position === 'right' ? 'ml-2 left-full top-0' : '',
    ]"
  >
    <div class="flex flex-col">
      <p class="text-white text-3xl my-8">{{ t('car_description_title') }}</p>

      <div class="mb-8">
        <textarea
            v-model="description"
            rows="4"
            class="w-full p-3 rounded border border-gray-300 bg-white text-black focus:outline-none focus:ring-2 focus:ring-pink-500"
            :placeholder="t('enter_description')"
        ></textarea>
      </div>

      <div class="flex justify-center gap-4">
        <button
            @click="saveDescription"
            class="w-24 h-24 text-3xl text-white bg-pink-700 hover:bg-pink-500"
        >
          <i class="fas fa-check"></i>
        </button>
        <button
            @click="$emit('close')"
            class="w-24 h-24 text-3xl text-white bg-pink-700 hover:bg-pink-500"
        >
          <i class="fas fa-x"></i>
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>
