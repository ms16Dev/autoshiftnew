<template>
  <div class="flex justify-between items-center w-full">
    <div class="text-sm text-gray-700">
      {{ t('profile_greeting', { username }) }}
    </div>

    <RouterLink
        :to="`/users/${username}`"
        class="bg-pink-600 hover:bg-pink-700 text-white text-sm font-medium py-1.5 px-4 rounded transition"
    >
      {{ t('view_profile') }}
    </RouterLink>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useAuthStore } from '../stores/auth'
import { useI18n } from 'vue-i18n'

const authStore = useAuthStore()
const username = computed(() => authStore.userInfo?.name ?? '')
const { t } = useI18n()
</script>
