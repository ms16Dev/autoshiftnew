<script setup lang="ts">
import {ref} from "vue";
import router from "../../router";
import {useAuthStore} from "../../stores/auth.ts";
import forge from "node-forge";
import { computed } from "vue";


defineOptions({
  username: "Sign-Up",
});

const authStore = useAuthStore();

// Reactive form inputs
const username = ref("");
const email = ref("");
const password = ref("");
const loading = ref(false);
const errorMessage = ref("");

const usernameError = ref("");


const isValid = computed(() => {
  return (
      username.value &&
      email.value &&
      password.value &&
      !usernameError.value &&
      !passwordError.value
  );
});

// Validate username on input
const validateUsername = () => {
  // Convert username to lowercase
  username.value = username.value.toLowerCase();

  // Regex to check if it contains only lowercase letters, numbers, or underscores,
  // doesn't start with a number or an underscore, and is at least 6 characters long
  const regex = /^[a-z][a-z0-9_]{5,}$/;

  if (!regex.test(username.value)) {
    usernameError.value = "Username must be at least 6 characters, start with a letter, and can only contain lowercase letters, numbers, and underscores.";
  } else {
    usernameError.value = "";
  }
};

const passwordError = ref("");

// Validate password on input
const validatePassword = () => {
  // Regex to check that the password is at least 6 characters long and doesn't contain spaces
  const regex = /^(?!.*\s).{6,}$/;

  if (!regex.test(password.value)) {
    passwordError.value = "Password must be at least 6 characters and cannot contain spaces.";
  } else {
    passwordError.value = "";
  }
};


// Function to encrypt the password using RSA
async function encryptPassword(password: string): Promise<string> {
  try {
    const base64Key = await authStore.fetchPublicKey(); // Get the public key from the store
    console.log('Base64 Public Key:', base64Key);  // Debugging line

    if (!base64Key) {
      throw new Error("Public key is not available");
    }

    // Decode the base64 string into bytes using the correct method
    const keyBytes = forge.util.decode64(base64Key);  // decode64 will give us a binary string

    if (!keyBytes || keyBytes.length === 0) {
      throw new Error("Invalid Base64 key data");
    }

    // Convert the decoded bytes into ASN.1 format for RSA
    const publicKey = forge.pki.publicKeyFromAsn1(forge.asn1.fromDer(keyBytes)); // Create public key from the decoded bytes

    // Encrypt the password using RSA with OAEP padding
    const encryptedPassword = publicKey.encrypt(password, 'RSA-OAEP');  // Use RSA-OAEP padding for encryption

    // Return the encrypted password as Base64
    return forge.util.encode64(encryptedPassword);
  } catch (error) {
    console.error("Error during password encryption:", error);
    throw error;  // Rethrow the error for the caller to handle
  }
}


async function submitForm() {
  // Ensure latest validation is run
  validateUsername();
  validatePassword();

  if (!isValid.value) {
    return; // Stop form submission if validation fails
  }

  loading.value = true;
  errorMessage.value = "";

  try {
    const encryptedPass = await encryptPassword(password.value);
    console.debug(encryptedPass);

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
        "Signup failed. Please try again.";
    errorMessage.value = responseMessage;
  } finally {
    loading.value = false;
  }
}
</script>

<template>
  <div class="relative group px-6 max-w-xl mx-auto">
    <form @submit.prevent="submitForm" class="relative flex flex-col p-6 space-y-6 overflow-hidden rounded-lg bg-white shadow-lg">
      <h1 class="text-extrabold text-center text-3xl text-pink-900">Sign Up</h1>

      <!-- Error Message -->
      <p v-if="errorMessage" class="text-red-500 text-center font-bold">{{ errorMessage }}</p>

      <div class="flex flex-col">
        <label for="username" class="font-bold text-pink-700">Username</label>
        <input
            v-model="username"
            type="text"
            id="username"
            required
            class="form-input w-full px-3 py-2 rounded-lg border-2 border-pink-300 focus:border-pink-700 invalid:border-red-500"
            placeholder="Your Username"
            @input="validateUsername"
        />
        <p v-if="usernameError" class="text-red-500 text-sm mt-1">{{ usernameError }}</p>
      </div>

      <!-- Email Input -->
      <div class="flex flex-col">
        <label for="email" class="font-bold text-pink-700">Email</label>
        <input v-model="email" type="email" id="email" required
               class="form-input w-full px-3 py-2 rounded-lg border-2 border-pink-300 focus:border-pink-700 invalid:border-red-500"
               placeholder="Your Email" />
      </div>

      <!-- Password Input -->
      <div class="flex flex-col">
        <label for="password" class="font-bold text-pink-700">Password</label>
        <input
            v-model="password"
            type="password"
            id="password"
            required
            class="w-full px-3 py-2 rounded-lg border-2 border-pink-700 focus:border-pink-700 invalid:border-red-500"
            placeholder="Your Password"
            @input="validatePassword"
        />
        <p v-if="passwordError" class="text-red-500 text-sm mt-1">{{ passwordError }}</p>
      </div>

      <!-- Submit Button -->
      <button
          type="submit"
          :disabled="loading || !isValid"
          class="w-full py-3 font-bold text-white bg-pink-700 rounded-lg hover:bg-pink-600"
      >
        {{ loading ? "Registering..." : "Register" }}
      </button>
    </form>

    <!-- Sign-In Section -->
    <div class="text-center my-3">
      <p class="text-white">Already have an account?</p>
      <router-link to="/auth/sign-in" class="mt-2 text-pink-700 font-bold hover:underline">Sign In</router-link>
    </div>
  </div>
</template>

<style scoped>
/* Add any additional scoped styles here if needed */
</style>
