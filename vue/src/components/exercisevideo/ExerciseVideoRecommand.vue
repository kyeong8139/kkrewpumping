<template>
  <div>
    <div class="searchFoam">
      <select id="purposeForAI" name="purposeForAI" v-model="AIStore.purpose">
        <option value="DIET">다이어트</option>
        <option value="POSTURE_CORRECTION">자세교정</option>
        <option value="MUSCULAR_STRENGTH">근력강화</option>
      </select>
      <input
        type="text"
        v-model="userRequest"
        placeholder="어떤 부위를 운동하고 싶으신가요?"
        style="width: 250px"
        @keyup.enter="generateRecommandVideo"
      />
      <button @click="generateRecommandVideo">AI 추천 영상</button>
    </div>
    <div class="message">
      <div>{{ message }}</div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useAIStore } from "@/stores/gemini";

//// AI 영상 추천
const AIStore = useAIStore();
const userRequest = ref("");

const message = ref("");

const generateRecommandVideo = function () {
  const queryForAI = {
    purpose: purposeForAI.value,
    userRequest: userRequest.value,
  };

  message.value = "AI가 영상을 추천하고 있습니다.";
  AIStore.generateRecommandVideo(queryForAI);
};
</script>

<style scoped>
.searchFoam {
  display: flex;
  align-items: center;
  border: 2px solid #ccc;
  border-radius: 50px;
  padding: 10px;
}

#purposeForAI,
input[type="text"],
button {
  margin: 5px;
}

button {
  padding: 5px 10px;
}

#purposeForAI,
input[type="text"],
button {
  margin: 0;
  border: none;
}

.message {
  margin: 10px;
}
</style>
