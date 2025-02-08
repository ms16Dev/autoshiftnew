<template>
  <button class="flex flex-row items-center justify-center p-4">
    <i :class="`w-16 h-16 rounded-full fas ${icon} bg-pink-200 text-white text-xl p-2 flex items-center justify-center`"></i>
    <p class="text-gray-400 text-xl font-bold text-center px-2 h-[30px] flex items-center justify-center">{{ label }}</p>
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
