<script setup lang="ts">

import {useI18n} from "vue-i18n";
import {useStaticDataStore} from "../stores/staticDataStore.ts";
import {nextTick, onMounted, onUnmounted, ref} from "vue";

const { t } = useI18n()

const staticData = useStaticDataStore();

defineProps({
  position: {
    type: String,
    default: 'up', // Can be 'top', 'bottom', 'left', 'right'
  },
});

const emit = defineEmits(['close']);

const switchCountry = (countryId: string) => {
  staticData.setCurrentCountry(countryId);
  emit('close');
  window.location.reload();

};

const popupRef = ref<HTMLElement | null>(null)

const handleClickOutside = (event: MouseEvent) => {
  if (popupRef.value && !popupRef.value.contains(event.target as Node)) {
    emit('close')
  }
}


onMounted(() => {
  // Delay adding the listener to avoid immediate close
  nextTick(() => {
    document.addEventListener('click', handleClickOutside)
  })
})

onUnmounted(() => document.removeEventListener('click', handleClickOutside))


</script>

<template>
  <div
      ref="popupRef"
      class="absolute bg-gradient-to-br from-[#ff80b5] to-[#9089fc]  rounded-lg shadow-lg ring-1 ring-gray-300 z-50 !important top-full start-1/2  md:block  "
  >

    <div class="flex-col p-2">
      <h1 class="text-white text-center">{{t('choose_country')}}</h1>
    </div>

    <ul v-for="(country,index) in staticData.data.countries"  class="divide-y divide-gray-200">
      <li v-if="country" :key="index" @click="switchCountry(country.id)" class="p-2 text-white hover:bg-pink-500 hover:scale-105 cursor-pointer">
        {{staticData.getLocalizedName(country)}}
      </li>
    </ul>

  </div>
</template>

<style scoped>
</style>
