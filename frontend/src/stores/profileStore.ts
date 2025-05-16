// stores/profileStore.ts
import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import axios from 'axios'
import {config} from "../../config.ts";

export interface UserProfile {
    id: string
    username: string
    complete: boolean
    dealer: boolean
    // ...add any other fields returned by the backend
}

export const useProfileStore = defineStore('profileStore', () => {
    const profile = ref<UserProfile | null>(null)
    const loading = ref(false)
    const error = ref<string | null>(null)

    const isProfileComplete = computed(() => profile.value?.complete ?? false)
    const isDealer = computed(() => profile.value?.dealer ?? false)

    async function fetchUserProfile(username: string) {
        loading.value = true
        error.value = null
        try {
            const response = await axios.get<UserProfile>(`${config.apiBaseUrl}/profiles/${username}`)
            profile.value = response.data
        } catch (err: any) {
            error.value = err.response?.data?.message ?? 'Failed to fetch profile'
            profile.value = null
        } finally {
            loading.value = false
        }
    }

    return {
        profile,
        isProfileComplete,
        isDealer,
        fetchUserProfile,
        loading,
        error,
    }
})
