<template>
  <button class="flex flex-col items-center justify-center">
    <i :class="`w-12 h-12 rounded-full fas ${icon} bg-pink-300 text-white text-xl p-2 flex items-center justify-center`"></i>
    <span class="text-gray-400 text-lg font-bold text-center px-2 h-[30px] flex items-center justify-center">{{ label }}</span>
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
