<template>
  <div class="container">
    <div class="flex flex-col items-center justify-center">
      <h1 class="text-2xl text-center text-pink-900 font-semibold">
        Welcome to AUTOSHIFT
      </h1>
      <img src="../../assets/asl-pink.png" height="150" width="150" class="my-4" />

      <div class="flex flex-col items-center justify-center text-gray-900 bg-gray-50 pattern-bg p-6 rounded-lg shadow-md">
        <h1 class="text-xl text-center text-pink-900 font-semibold">
          Let's setup your profile
        </h1>

        <h1 class="text text-center text-pink-900 font-semibold">
          Are you a car's dealer or a user? Flip the switch below to choose the correct profile.
        </h1>

        <br />
        <div class="flex items-center space-x-4">
          <!-- Label for User -->
          <span class="text-pink-900 text-lg">User only profile</span>

          <!-- Toggle Switch -->
          <label class="relative inline-flex items-center cursor-pointer">
            <input type="checkbox" class="sr-only peer" v-model="isDealer" />
            <div
                class="w-14 h-7 bg-gray-300 rounded-full peer peer-focus:ring-2 peer-focus:ring-pink-900 peer-checked:bg-pink-900 transition-all duration-300"
            ></div>
            <div
                class="absolute left-1 top-1 w-5 h-5 bg-white rounded-full shadow-md peer-checked:translate-x-7 transform transition-transform duration-300"
            ></div>
          </label>

          <!-- Label for Dealer -->
          <span class="text-pink-900 text-lg">Cars Dealer profile</span>
        </div>
      </div>
    </div>

    <div class="flex md:flex-row flex-wrap w-full">
      <!-- Sidebar - Dealer Card -->
      <div class="w-full lg:w-1/5 p-4 text-center text-gray-400">
        <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
          <div class="group flex-col inset-px shadow-md">



            <!-- Cover image-->
            <div class="overflow-hidden">
              <img class="w-full transition-transform duration-700  group-hover:scale-110" src="/car_plchldr1.jpg"/>

            </div>
            <!--Devider-->
            <div class="w-full border-b-2 border-b-pink-700 "></div>
            <div class="flex flex-row w-full justify-center">
              <div class="z-10"></div>
              <div class="flex rounded-full ring-2 ring-pink-700 h-24 w-24 bg-gray-100 -translate-y-1/2  overflow-hidden">
                <img src="https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg">
              </div>
            </div>
            <div class="flex justify-center -translate-y-12 text-pink-500 font-bold text-2xl ">fdsfdsfsfs</div>
            <div class="flex  justify-center text-gray-500 text-lg -translate-y-8"> fasdfafsafa</div>




            <div class="flex items-center justify-between py-2 px-6 bg-pink-100 ">


              <!-- Likes -->
              <div class="flex items-center space-x-2">
                <i class="fas fa-map-marker text-pink-300"/>
                <span class="text-sm text-gray-700">afafafas</span>
              </div>

              <!-- Name -->
              <div class="flex items-center space-x-2">
                <i class="fas fa-heart text-pink-300"/>
                <span class="text-sm text-gray-700">faffa</span>
              </div>

            </div>
          </div>




        </div>
      </div>
      <!-- Main Content Area -->
      <div class="w-full lg:w-3/5 p-4 text-center text-gray-200">
        <div class="w-full flex flex-col items-center justify-center text-gray-900 bg-gray-50 pattern-bg p-6 rounded-lg shadow-md">
          <div class="lg:flex space-x-4 border-b-2 pb-2">
            <button
                :class="{ 'border-b-2 border-pink-500': activeTab === 1 }"
                class="text-xl font-semibold py-2 px-4 cursor-pointer"
                @click="activeTab = 1"
            >
              About
            </button>
            <button
                :class="{ 'border-b-2 border-pink-500': activeTab === 2 }"
                class="text-xl font-semibold py-2 px-4 cursor-pointer"
                @click="activeTab = 2"
            >
              Contact
            </button>
          </div>

          <div class="w-full mt-4">
            <!-- About Section -->
            <div v-show="activeTab === 1" class="w-full text-gray-600">
              <form @submit.prevent="submitForm" class="relative flex flex-col p-6 space-y-6 overflow-hidden">
                <p v-if="errorMessage" class="text-red-500 text-center font-bold">{{ errorMessage }}</p>

                <!-- Name / Dealer Name -->
                <div class="flex flex-col">
                  <label for="name" class="font-bold text-pink-700">{{ isDealer ? "Dealer's Name" : "Name" }}</label>
                  <input
                      v-model="name"
                      type="text"
                      id="name"
                      required
                      class="form-input w-full px-3 py-2 rounded-lg border-2 border-pink-300 focus:border-pink-700"
                      placeholder="Enter your name"
                  />
                </div>

                <!-- Car / Subtitle -->
                <div class="flex flex-col">
                  <label for="car" class="font-bold text-pink-700">{{ isDealer ? "Subtitle" : "Car" }}</label>
                  <input
                      v-model="car"
                      type="text"
                      id="car"
                      required
                      class="form-input w-full px-3 py-2 rounded-lg border-2 border-pink-300 focus:border-pink-700"
                      placeholder="Enter {{ isDealer ? 'a subtitle' : 'your car' }}"
                  />
                </div>

                <!-- Profile Pic -->
                <div class="flex flex-col">
                  <label class="font-bold text-pink-700">Profile Picture</label>
                  <input type="file" accept="image/*" @change="handleProfilePicUpload" class="border-2 p-2 rounded-lg" />
                </div>

                <!-- Cover Pic -->
                <div class="flex flex-col">
                  <label class="font-bold text-pink-700">Cover Picture</label>
                  <input type="file" accept="image/*" @change="handleCoverPicUpload" class="border-2 p-2 rounded-lg" />
                </div>

                <button type="submit" class="bg-pink-700 text-white py-2 px-4 rounded-lg hover:bg-pink-900">
                  Submit
                </button>
              </form>
            </div>

            <!-- Contact Section -->
            <div v-show="activeTab === 2" class="text-gray-600">
              <h3 class="text-2xl font-semibold">{{ isDealer ? "Dealer Contact Info" : "User Contact Info" }}</h3>
              <ul class="list-disc pl-6">
                <li v-if="isDealer">Business Address</li>
                <li v-if="isDealer">Phone Number</li>
                <li>Email</li>
                <li v-if="!isDealer">Social Media (Optional)</li>
              </ul>
            </div>
          </div>
        </div>
      </div>

      <!-- Fixed Sidebar - Ad Item -->
      <div class="w-full lg:w-1/5 p-4 text-center text-gray-700 fixed sticky">
        <AdItem />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import AdItem from "../../components/AdItem.vue";

defineOptions({
  name: "complete-profile",
});

const activeTab = ref(1); // Active tab default is 'About'
const isDealer = ref(false); // Tracks if the user is a dealer
const name = ref("");
const car = ref("");
const errorMessage = ref("");

const handleProfilePicUpload = (event: Event) => {
  const file = (event.target as HTMLInputElement).files?.[0];
  if (file) {
    console.log("Profile Picture Selected:", file.name);
  }
};

const handleCoverPicUpload = (event: Event) => {
  const file = (event.target as HTMLInputElement).files?.[0];
  if (file) {
    console.log("Cover Picture Selected:", file.name);
  }
};

const submitForm = () => {
  if (!name.value || !car.value) {
    errorMessage.value = "Please fill in all fields.";
    return;
  }
  console.log("Form Submitted:", {
    type: isDealer.value ? "Dealer" : "User",
    name: name.value,
    car: car.value,
  });
};
</script>
