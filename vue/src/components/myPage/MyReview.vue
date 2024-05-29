<template>
  <div>
    <h1>내가 등록한 리뷰</h1>
    <div v-if="userChallengeStore.myCreateReview.length === 0">
      <span>등록한 리뷰가 없습니다.</span>
    </div>
    <div v-else>
      <div v-for="review in userChallengeStore.myCreateReview" :key="review.id">
        <div class="my-review">
          <p @click="goDetail(review.challengeId)">{{ review.content }}</p>
          <button @click="deleteReview(review.challengeId, review.id)">
            삭제
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from "@/stores/user";
import { useUserChallengeStore } from "@/stores/userChallenge";
import { useReviewStore } from "@/stores/review";
import { onMounted, ref, computed } from "vue";
import router from "@/router";

const userStore = useUserStore();
const userChallengeStore = useUserChallengeStore();
const reviewStore = useReviewStore();

const goDetail = function (challengeId) {
  router.push({ name: "challengeDetail", params: { id: challengeId } });
};

const deleteReview = function (challengeId, reviewId) {
  reviewStore.deleteReview(challengeId, reviewId);
  location.reload();
};

onMounted(() => {
  const userId = userStore.loginUserId;

  const query = {
    table: "review",
  };

  userChallengeStore.findRegistReview(userId, query);
});
</script>

<style scoped>
h1 {
  font-size: 24px;
  font-weight: 800;
  margin: 20px;
  padding: 20px;
}

.my-review {
  margin: 10px;
  padding: 3% 20%;
  display: flex;
  justify-content: space-between;
}

.my-review p {
  align-content: center;
}

.my-review p:hover {
  color: #ffa500;
}

button {
  padding: 5px 10px 5px;
  font-size: 12px;
  color: white;
  background-color: #ff8911;
  border-radius: 30px;
  border: 2px solid transparent;
}
</style>
