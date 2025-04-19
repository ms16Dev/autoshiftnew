<template>
  <div
      class="flex items-center justify-between gap-3 p-2 cursor-pointer hover:bg-gray-100 rounded-lg transition"
  >

      <!-- Avatar -->
      <img :src="'http://localhost:8080'+props.url" width="100" height="100"/>

      <!-- User Name -->
      <span class="text-pink-500 font-lg font-extrabold">{{ props.name_en }}</span>

    <!-- User Name -->
    <span class="text-pink-500 font-lg font-extrabold">{{ props.name_ar }}</span>


  </div>
</template>

<script setup lang="ts">

const props = defineProps<{
  id: string,
  name_en: string,
  name_ar: string,
  url: string,
}>()

</script>
