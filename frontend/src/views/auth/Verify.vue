<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import { config } from '../../../config.ts'
import { useI18n } from 'vue-i18n'

const { t } = useI18n()
const route = useRoute()
const router = useRouter()

const isVerified = ref(false)
const errorMessage = ref<string | null>(null)
const isLoading = ref(true)

onMounted(async () => {
  const token = route.query.token as string

  if (!token) {
    errorMessage.value = t('missing_token')
    isLoading.value = false
    return
  }

  try {
    await axios.get(`${config.apiBaseUrl}/auth/verify`, {
      params: { token }
    })
    isVerified.value = true
  } catch (error: any) {
    errorMessage.value =
        error.response?.data?.message ||
        error.response?.data?.error ||
        t('verification_failed')
  } finally {
    isLoading.value = false
  }
})
</script>

<template>
  <div class="relative group max-w-md mx-auto">
    <div class="absolute inset-px shadow-lg rounded-lg bg-white dark:bg-gray-900 group-hover:bg-pink-50"></div>

    <div class="relative flex flex-col p-6 space-y-6 overflow-hidden rounded-lg">
      <h2 class="text-xl font-bold text-center text-pink-700">{{ t('email_verification') }}</h2>

      <!-- Default loading state -->
      <div v-if="isLoading">
        <p class="text-center text-gray-500">{{ t('verifying_email') }}</p>
      </div>

      <!-- Success -->
      <div v-else-if="isVerified">
        <p class="text-center text-green-600">{{ t('verification_successful') }}</p>
        <div class="text-center">
          <router-link to="/sign-in" class="text-pink-500">{{ t('go_to_login') }}</router-link>
        </div>
      </div>

      <!-- Failure -->
      <div v-else-if="errorMessage">
        <p class="text-center text-red-600">{{ errorMessage }}</p>
        <div class="text-center">
          <router-link
              to="/resend-verification"
              class="w-full inline-block p-3 mt-4 font-bold text-white bg-pink-700 rounded-lg hover:bg-pink-600"
          >
            {{ t('resend_verification_email') }}
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>
