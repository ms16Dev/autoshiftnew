<script setup lang="ts">
import {ref, onMounted} from "vue";
import apiService from "../core/services/ApiService.ts";
import {useAuthStore} from "../stores/auth.ts";

interface Comment {
  id: number;
  createdBy: string;
  content: string;
  createdDate: string;
}

const authStore = useAuthStore();


// Props to get car ID from parent
const props = defineProps<{ carId: string }>();

const comments = ref<Comment[]>([]);
const newComment = ref("");
const loading = ref(false);

// Fetch comments from backend
const fetchComments = async () => {
  try {
    loading.value = true;
    const response = await apiService.get1(`cars/${props.carId}/comments`);
    comments.value = response.data;
  } catch (error) {
    console.error("Error fetching comments:", error);
  } finally {
    loading.value = false;
  }
};

// Add a new comment
const addComment = async () => {
  if (!newComment.value.trim()) return;

  try {
    await apiService.post(`cars/${props.carId}/comments`, {
      content: newComment.value
    });

    // Refresh comments after posting
    await fetchComments();

    newComment.value = "";
  } catch (error) {
    console.error("Error adding comment:", error);
  }
};

const formatCreatedDate = (dateString: string): string => {
  const createdDate = new Date(dateString); // Convert input to Date object
  const currentDate = new Date();

  // Ensure it's a valid date
  if (isNaN(createdDate.getTime())) {
    return "Invalid date";
  }

  const differenceInTime = currentDate.getTime() - createdDate.getTime();
  const differenceInDays = Math.floor(differenceInTime / (1000 * 3600 * 24));

  if (differenceInDays <= 0) return "Today";
  if (differenceInDays === 1) return "1 day ago";
  return `${differenceInDays} days ago`;
};

// Fetch comments when the component mounts
onMounted(fetchComments);
</script>

<template>
  <div class="w-full bg-white p-4 shadow mt-4">
    <h2 class="text-xl text-gray-600 text-start font-semibold mb-4">Comments</h2>

    <!-- Loading Indicator -->
    <p v-if="loading" class="text-gray-500">Loading comments...</p>

    <!-- Comment List -->
    <div v-else-if="comments.length">
      <div v-for="comment in comments" :key="comment.id" class="border-b py-2 text-start">
        <p class="text-sm text-gray-600">
          {{ comment.createdBy }} - <span class="text-xs">{{ formatCreatedDate(comment.createdDate) }}</span>
        </p>
        <p class="text-gray-800">{{ comment.content }}</p>
      </div>
    </div>
    <p v-else class="text-gray-500">No comments yet. Be the first to comment!</p>

    <!-- Add Comment -->
    <div
        v-if="authStore.isAuthenticated"
        class="mt-4">
      <input
          v-model="newComment"
          type="text"
          placeholder="Write a comment..."
          class="w-full p-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-400 text-gray-400"
      />
      <button
          @click="addComment"
          class="mt-2 bg-pink-500 text-white px-4 py-2 rounded-lg hover:bg-pink-600 transition"
      >
        Add Comment
      </button>

    </div>

    <router-link
        v-else
        class="mt-2 bg-gray-300 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-400 transition"
        to="/auth/sign-in">
      Login to Add Comment
    </router-link>
  </div>
</template>
