<template>
  <button class="flex flex-col items-center justify-center">
    <i :class="`w-8 h-8 rounded-full fas ${icon} bg-pink-300 text-white text-sm p-2 flex items-center justify-center`"></i>
    <span class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">{{ label }}</span>
  </button>
</template>

<script setup>
defineProps({
  icon: {
    type: String,
    required: true,
  },
  label: {
    type: String,
    required: true,
  },
});
</script>
