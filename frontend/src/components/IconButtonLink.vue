<script setup lang="ts">

defineProps({
  text: {
    type: String,
    required: true,
    default: 'Click Me!'
  },
  icon: {
    type: [String, Array],
    required: true,
  },
  target: {
    type: String,
    required: true,
  }

});

export interface IconButtonLinkProps {
  text: string;
  icon: string;
  target: string;
}
</script>

<template>
  <RouterLink :to="target"
               class=" flex items-center space-x-2 px-4 py-2 bg-transparent text-white hover:transform
               transition-transform duration-900 ease-in-out  hover:bg-pink-500 hover:scale-110">
    <i :class="[icon, 'text-2xl text-center w-12 h-8 md:w-full md:h-10 xl:w-5 xl:h-8 xl:text-lg xl:text-center']" />
    <span class="hidden xl:block font-medium">{{ text }}</span>
  </RouterLink>

</template>

<style scoped>

</style>