<script setup lang="ts">
import { ref, computed } from "vue";
import router from "../../router";
import { useAuthStore } from "../../stores/auth.ts";
import forge from "node-forge";
import { useI18n } from "vue-i18n";

const { t } = useI18n();

const authStore = useAuthStore();

const username = ref("");
const email = ref("");
const password = ref("");
const loading = ref(false);
const errorMessage = ref("");
const usernameError = ref("");
const passwordError = ref("");

const isValid = computed(() => {
  return (
      username.value &&
      email.value &&
      password.value &&
      !usernameError.value &&
      !passwordError.value
  );
});

const validateUsername = () => {
  username.value = username.value.toLowerCase();
  const regex = /^[a-z][a-z0-9_]{5,}$/;

  if (!regex.test(username.value)) {
    usernameError.value = t('username_rules');
  } else {
    usernameError.value = "";
  }
};

const validatePassword = () => {
  const regex = /^(?!.*\s).{6,}$/;

  if (!regex.test(password.value)) {
    passwordError.value = t('password_rules');
  } else {
    passwordError.value = "";
  }
};

async function encryptPassword(password: string): Promise<string> {
  try {
    const base64Key = await authStore.fetchPublicKey();
    const keyBytes = forge.util.decode64(base64Key);
    const publicKey = forge.pki.publicKeyFromAsn1(forge.asn1.fromDer(keyBytes));
    const encryptedPassword = publicKey.encrypt(password, 'RSA-OAEP');
    return forge.util.encode64(encryptedPassword);
  } catch (error) {
    console.error("Error during password encryption:", error);
    throw error;
  }
}

async function submitForm() {
  validateUsername();
  validatePassword();

  if (!isValid.value) return;

  loading.value = true;
  errorMessage.value = "";

  try {
    const encryptedPass = await encryptPassword(password.value);
    console.log("Encrypted password:", encryptedPass);
    await authStore.register({
      username: username.value,
      email: email.value,
      password: password.value,
    });

    await router.push("/auth/verify-email");
  } catch (error) {
    const axiosError = error as any;
    const responseMessage =
        axiosError.response?.data?.error ||
        axiosError.response?.data?.message ||
        t('signup.failed');

    errorMessage.value = responseMessage;
  } finally {
    loading.value = false;
  }
}
</script>

<template>
  <div class="relative group px-6 max-w-xl mx-auto">
    <form @submit.prevent="submitForm" class="relative flex flex-col p-6 space-y-6 overflow-hidden rounded-lg bg-white shadow-lg">
      <h1 class="text-extrabold text-center text-3xl text-pink-900">{{ t("sign_up") }}</h1>

      <p v-if="errorMessage" class="text-red-500 text-center font-bold">{{ errorMessage }}</p>

      <div class="flex flex-col">
        <label for="username" class="font-bold text-pink-700">{{ t("username") }}</label>
        <input
            v-model="username"
            type="text"
            id="username"
            required
            class="form-input w-full px-3 py-2 rounded-lg border-2 border-pink-300 focus:border-pink-700 invalid:border-red-500"
            :placeholder="t('username_placeholder')"
            @input="validateUsername"
        />
        <p v-if="usernameError" class="text-red-500 text-sm mt-1">{{ t("username_error") }}</p>
      </div>

      <div class="flex flex-col">
        <label for="email" class="font-bold text-pink-700">{{ t("email") }}</label>
        <input
            v-model="email"
            type="email"
            id="email"
            required
            class="form-input w-full px-3 py-2 rounded-lg border-2 border-pink-300 focus:border-pink-700 invalid:border-red-500"
            :placeholder="t('email_placeholder')"
        />
      </div>

      <div class="flex flex-col">
        <label for="password" class="font-bold text-pink-700">{{ t("password") }}</label>
        <input
            v-model="password"
            type="password"
            id="password"
            required
            class="w-full px-3 py-2 rounded-lg border-2 border-pink-700 focus:border-pink-700 invalid:border-red-500"
            :placeholder="t('password_placeholder')"
            @input="validatePassword"
        />
        <p v-if="passwordError" class="text-red-500 text-sm mt-1">{{ t("password_error") }}</p>
      </div>

      <button
          type="submit"
          :disabled="loading || !isValid"
          class="w-full py-3 font-bold text-white bg-pink-700 rounded-lg hover:bg-pink-600"
      >
        {{ loading ? t("registering") : t("register") }}
      </button>
    </form>

    <div class="text-center my-3">
      <p class="text-white">{{ t("already_have_account") }}</p>
      <router-link to="/auth/sign-in" class="mt-2 text-pink-700 font-bold hover:underline">{{ t("sign_in") }}</router-link>
    </div>
  </div>
</template>

<style scoped>
/* Add any additional scoped styles here if needed */
</style>
