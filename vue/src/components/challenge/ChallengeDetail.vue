<template>
  <div>
    <div id="challengeHeader">
      <p v-if="challengeStore.challenge.purpose === 'DIET'">다이어트</p>
      <p v-else-if="challengeStore.challenge.purpose === 'POSTURE_CORRECTION'">
        자세교정
      </p>
      <p v-else>근력강화</p>
      <h2>{{ challengeStore.challenge.title }}</h2>
      <div id="btnContainer">
        <button @click="joinCrew">크루와 함께 도전하기</button>
        <button @click="startChallenge">혼자 도전하기</button>
      </div>
    </div>

    <div id="challenge-Body">
      <div id="challenge-Content-Container">
        <div>
          <div class="challenge-content">
            <span>기간</span>
            <p>{{ challengeStore.challenge.period }}일</p>
            <span>준비물 </span>
            <div v-if="challengeStore.challenge.supplies &&
              challengeStore.challenge.supplies.length == 0
              ">
              <p>없음</p>
            </div>
            <div v-else>
              <div v-for="supply in challengeStore.challenge.supplies" :key="supply.id">
                <p>{{ supply }}</p>
              </div>
            </div>
          </div>
        </div>
        <div>
          <div class="challenge-content">
            <span>평균 운동 시간</span>
            <p>{{ challengeStore.challenge.time }} 분</p>
            <span>난이도</span>
            <p v-if="challengeStore.challenge.level === 1">⭐</p>
            <p v-else-if="challengeStore.challenge.level === 2">⭐⭐</p>
            <p v-else>⭐⭐⭐</p>
          </div>
        </div>
        <div>
          <div class="challenge-content">
            <span>현재 참여자수</span>
            <p>{{ challengeStore.challenge.currentParticipantsCount }} 명</p>
            <span>완주한 사람</span>
            <p>{{ challengeStore.challenge.successParticipantsCount }} 명</p>
          </div>
        </div>
      </div>
      <hr />
      <div>
        <nav>
          <button @click="showVideoList">전체 커리큘럼</button>
          <button @click="showReviewList">챌린지 리뷰</button>
        </nav>
        <div id="videoListContainer" v-if="currentView === 'videos'">
          <div id="one-video" v-for="video in challengeStore.challenge.videos" :key="video.id">
            <img :src="video.imageUrl" alt="" />
            <div class="video-content">
              <p style="font-size: 18px; font-weight: 700">{{ video.title }}</p>
              <div>
                <div v-if="video.supplies && video.supplies.length == 0">
                  <p>준비물 없음</p>
                </div>
                <div v-for="supply in video.supplies" :key="supply.id">
                  <p>준비물 {{ supply }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div v-if="currentView === 'reviews'">
          <div class="reviewContent">
            <div @keyup.enter="createReview">
              <input type="text" id="reviewContent" name="reviewContent" maxlength="280" placeholder="챌린지 리뷰를 등록해주세요"
                v-model="reviewStore.review.content" />
              <button @click="createReview">등록하기</button>
            </div>
            <div class="reviewList">
              <div v-if="reviewList.length == 0">
                <p>아직 등록된 리뷰가 없습니다.</p>
              </div>
              <div v-else>
                <div v-for="review in reviewList" :key="review.id">
                  <div class="review-Content">
                    <p>{{ review.content }}</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useChallengeStore } from "@/stores/challenge";
import { useRouter, useRoute } from "vue-router";
import { onMounted, ref, computed } from "vue";
import { useUserStore } from "@/stores/user";
import { useReviewStore } from "@/stores/review";
import { useKrewStore } from "@/stores/krew";
import { useUserChallengeStore } from "@/stores/userChallenge";

const challengeStore = useChallengeStore();
const userChallengeStore = useUserChallengeStore();

const route = useRoute();
const router = useRouter();

const userStore = useUserStore();
const reviewStore = useReviewStore();

////////혼자챌린지참여

const startChallenge = function () {
  const userId = userStore.loginUserId;
  const challengeId = route.params.id;

  challengeStore.startChallenge(userId, challengeId);
};

////////크루와함께 참여하기
const krewStore = useKrewStore();

const joinCrew = function () {
  const challengeId = route.params.id;
  krewStore.getCrewList(challengeId);
  router.push({ name: "krew", params: { id: challengeId } });
};

// 리뷰 등록
const createReview = function () {
  reviewStore.review.writer = userStore.loginUserId;

  reviewStore.createReview(route.params.id);
};

/////////밑에뜨는 view 전환
const currentView = ref("");
const reviewList = computed(() => {
  return reviewStore.reviewList;
});

// Methods to handle button clicks and fetch data
const showVideoList = () => {
  currentView.value = "videos";
};

const showReviewList = () => {
  currentView.value = "reviews";
};

onMounted(() => {
  //챌린지 번호 어디에 있나
  challengeStore.getChallenge(route.params.id);

  // 리뷰 가지고 오기
  reviewStore.getReview(route.params.id);
  showVideoList();
});
</script>

<style scoped>
#challengeHeader {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #fff1e3;
  margin: 10px;
  padding: 20px;
}

#challengeHeader p {
  display: block;
  justify-self: center;
  width: 100px;
  margin-bottom: 5px;
  border: #ff8911 1px solid;
  border-radius: 50px;
  padding: 10px;
  font-size: 16px;
}

#challengeHeader h2 {
  margin: 15px;
  font-size: 28px;
}

button {
  margin: 0px 20px;
  border: none;
  border-radius: 50px;
  background-color: #ff8911;
  color: aliceblue;
  padding: 10px 15px;
}

hr {
  padding: 0 30%;
}

#btnContainer button:hover {
  background-color: #7f27ff;
}

#challenge-Content-Container {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

#challenge-Body {
  border: 0.5px solid black;
  border-radius: 100px;
  padding: 35px;
  margin: 50px;
}

hr {
  margin: 35px;
}

.challenge-content {
  display: flex;
  justify-content: space-between;
  padding: 0px 50px;
}

.challenge-content span {
  display: block;
  width: 120px;
  text-align: left;
  padding: 10px;
}

.challenge-content p {
  display: block;
  width: 70px;
  text-align: left;
  padding: 10px;
}

.reviewContent,
.review-Content {
  margin: 20px;
  padding: 20px;
  display: flex;
  justify-content: center;
  flex-direction: column;
}

.review-Content {
  border-bottom: 0.5px gray solid;
}

.reviewList {
  display: flex;
  justify-content: center;
  align-items: center;
}

#reviewContent {
  padding: 10px;
  width: 300px;
  border: 0.5px solid gray;
  border-radius: 20px;
}

#videoListContainer {
  margin: 20px;
  padding: 20px;
  display: flex;
  flex-direction: column;
}

#one-video {
  display: flex;
  justify-content: center;
  gap: 100px;
  margin: 10px;
  padding: 20px;
  border-bottom: 0.5px solid gray;
}

#videoListContainer img {
  width: 360px;
  height: 190px;
  object-fit: cover;
  border-radius: 20px;
}

.video-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.video-content p {
  display: block;
  width: 250px;
  margin: 10px;
  padding: 10px;
}
</style>
