<script setup lang="ts">
import { ref } from "vue";

interface Comment {
  id: number;
  user: string;
  text: string;
  date: string;
}

const comments = ref<Comment[]>([
  { id: 1, user: "John Doe", text: "Great post!", date: "2024-02-05" },
  { id: 2, user: "Jane Smith", text: "Thanks for sharing.", date: "2024-02-05" }
]);

const newComment = ref("");

const addComment = () => {
  if (!newComment.value.trim()) return;

  comments.value.push({
    id: comments.value.length + 1,
    user: "You", // Change this to an actual username from your auth system
    text: newComment.value,
    date: new Date().toISOString().split("T")[0]
  });

  newComment.value = "";
};
</script>

<template>
  <div class="w-full  bg-white p-4 shadow mt-4 ">
    <h2 class="text-xl text-gray-600 text-start font-semibold mb-4">Comments</h2>

    <!-- Comment List -->
    <div v-if="comments.length">
      <div v-for="comment in comments" :key="comment.id" class="border-b py-2 text-start">
        <p class="text-sm text-gray-600">{{ comment.user }} - <span class="text-xs">{{ comment.date }}</span></p>
        <p class="text-gray-800">{{ comment.text }}</p>
      </div>
    </div>
    <p v-else class="text-gray-500">No comments yet. Be the first to comment!</p>

    <!-- Add Comment -->
    <div class="mt-4">
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
  </div>
</template>
