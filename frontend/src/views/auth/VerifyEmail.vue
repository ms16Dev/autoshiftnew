<script setup lang="ts">
import { useI18n } from "vue-i18n";

const { t } = useI18n();
</script>
<template>
  <div class="relative group max-w-md mx-auto">
    <div class="absolute inset-px shadow-lg rounded-lg bg-white dark:bg-gray-900 group-hover:bg-pink-50"></div>

    <div class="relative flex flex-col p-6 space-y-6 overflow-hidden rounded-lg">
      <h2 class="text-xl font-bold text-center text-pink-700">
        {{ t('verify_email_title') }}
      </h2>
      <p class="text-center text-gray-600">
        {{ t('verify_email_message') }}
      </p>
    </div>
  </div>
</template>

