<script setup lang="ts">
import IconButtonLink, { IconButtonLinkProps } from './IconButtonLink.vue';

defineProps({
  items: {
    type: Array as () => IconButtonLinkProps[],
    default: () => [
      { text: 'Car', icon: 'fas fa-car', target: '/cars/add-car' },
      { text: 'Part', icon: 'fas fa-cogs', target: '/home' },
      { text: 'Rental', icon: 'fas fa-tag', target: '/home' },
      { text: 'Auction', icon: 'fas fa-hammer', target: '/home' },
    ],
  },
  position: {
    type: String,
    default: 'bottom', // Can be 'top', 'bottom', 'left', 'right'
  },
});

defineEmits(['close']);
</script>

<template>
  <div
      :class="[
      'absolute  bg-gradient-to-r from-[#ff80b5] to-[#9089fc] opacity-80 rounded-lg shadow-lg ring-1 ring-gray-300 z-50',
      position === 'bottom' ? 'mt-2 top-full end-0' : '',
      position === 'top' ? 'mb-2 bottom-full end-0' : '',
      position === 'left' ? 'mr-2 right-full top-0' : '',
      position === 'right' ? 'ml-2 left-full top-0' : '',
    ]"
  >
    <ul class="divide-y divide-gray-200">
      <!-- Render each IconButtonLink as a menu item -->
      <li v-for="(item, index) in items" :key="index" class="p-2">
        <IconButtonLink v-bind="item" class="text-pink-500" @click="$emit('close')"/>
      </li>
    </ul>

  </div>
</template>

<style scoped>
</style>
