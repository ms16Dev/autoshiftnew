<template>
  <div
      class="flex items-center justify-between gap-3 p-2 cursor-pointer hover:bg-gray-100 rounded-lg transition"
  >

      <!-- Avatar -->
      <span class="text-pink-500 font-lg font-extrabold">
        <i class="fas fa-shield text-3xl"></i>
      </span>

      <!-- User Name -->
      <span class="text-pink-500 font-lg font-extrabold">{{ props.name_en }}</span>
      <span class="text-pink-500 font-lg font-extrabold">{{ props.name_ar }}</span>

  </div>
</template>

<script setup lang="ts">

const props = defineProps<{
  id: string,
  name_en: string,
  name_ar: string,

}>()

</script>
