<template>
  <div>
    <div id="challengeHeader">
      <p v-if="myChallenge.purpose === 'DIET'">다이어트</p>
      <p v-else-if="myChallenge.purpose === 'POSTURE_CORRECTION'">
        자세교정
      </p>
      <p v-else>근력강화</p>
      <h2>{{ myChallenge.title }}</h2>
    </div>

    <div id="challenge-Body">
      <div id="challenge-Content-Container">
        <div>
          <div class="challenge-content">
            <span>기간</span>
            <p>{{ myChallenge.period }}일</p>
            <span>준비물 </span>
            <div v-if="myChallenge.supplies.length == 0">
              <p>없음</p>
            </div>
            <div v-else>
              <div v-for="supply in myChallenge.supplies" :key="supply.id">
                <p>{{ supply }}</p>
              </div>
            </div>
          </div>
        </div>
        <div>
          <div class="challenge-content">
            <span>평균 운동 시간</span>
            <p>{{ myChallenge.time }} 분</p>
            <span>난이도</span>
            <p v-if="myChallenge.level === 1">⭐</p>
            <p v-else-if="myChallenge.level === 2">⭐⭐</p>
            <p v-else>⭐⭐⭐</p>
          </div>
        </div>
        <div>
          <div class="challenge-content">
            <span>현재 참여자수</span>
            <p>{{ myChallenge.currentParticipantsCount }} 명</p>
            <span>완주한 사람</span>
            <p>{{ myChallenge.successParticipantsCount }} 명</p>
          </div>
        </div>
      </div>
      <hr />
      <div>
        <nav>
          <button @click="showVideoList">전체 커리큘럼</button>
          <button @click="showReviewList">챌린지 리뷰</button>
        </nav>
        <div id="myvideoListContainer" v-if="currentView === 'videos'">
          <div id="my-one-video" v-for="(video, index) in myChallenge.videos" :key="video.id">
            <img class="my-video-img" :src="video.imageUrl" alt="" @click="goDetail(video.id)" />
            <div class="video-content">
              <div>
                <p class="content" style="font-size: 18px; font-weight: 700">{{ video.title }}</p>
              </div>
              <div>
                <div v-if="video.supplies && video.supplies.length == 0">
                  <p class="content">준비물 없음</p>
                </div>
                <div v-for="supply in video.supplies" :key="supply.id">
                  <p class="content">준비물 {{ supply }}</p>
                </div>
              </div>
              <div>
                <div v-if="!video.isComplete">
                  <div class="completeToday">
                    <span>오늘 운동하러 가볼까요?</span>
                    <button @click="isCompleted(index)">✔</button>
                  </div>
                </div>
                <div v-else>
                  <div class="completeToday">
                    <span>운동한 당신, 멋져요!</span>
                    <img class="goodJob" src="/src/assets/img/STAMP.png" alt="" />
                  </div>
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
import { onMounted, ref, computed, onBeforeMount, onBeforeUpdate, watch } from "vue";
import { useUserStore } from "@/stores/user";
import { useReviewStore } from "@/stores/review";
import { useKrewStore } from "@/stores/krew";

const challengeStore = useChallengeStore();

const route = useRoute();
const router = useRouter();

const userStore = useUserStore();
const reviewStore = useReviewStore();

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

const myChallenge = computed(() => { return challengeStore.challenge.challenge });

///////오운완

const isCompleted = function (index) {
  const userId = userStore.loginUserId;
  const challengeId = route.params.id;

  challengeStore.clickToday(userId, challengeId, index);
  router.push({ name: "myChallengeDetail", params: { id: challengeId } });
  location.reload();
}

const goDetail = function (id) {
  router.push({ name: "videoDetail", params: { id: id } })
}

onBeforeMount(() => {

  const func = async function() {
    //챌린지 번호 어디에 있나
    await challengeStore.getMyChallenge(userStore.loginUserId, route.params.id);
    reviewStore.getReview(challengeStore.challenge.challenge.id);
  }

  func();

  // 리뷰 가지고 오기
  showVideoList();
});

// 리뷰 등록
const createReview = function () {
  reviewStore.review.writer = userStore.loginUserId;

  reviewStore.createReview(myChallenge.value.id);
};

onBeforeUpdate(() => {
  if (userStore.isLoggedIn) {
    // 챌린지 번호 어디에 있나
    challengeStore.getMyChallenge(userStore.loginUserId, route.params.id);
  }
})

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

#myvideoListContainer {
  margin: 20px;
  padding: 20px;
  display: flex;
  flex-direction: column;
}

#my-one-video {
  display: flex;
  justify-content: space-evenly;
  list-style-type: none;
  text-align: center;
  align-items: center;
  margin: 10px 0;
}

#myvideoListContainer img {
  display: block;
  width: 200px;
  height: 110px;
  object-fit: cover;
  border-radius: 20px;
}

#myvideoListContainer p {
  margin: 5px 0;
  display: block;
  width: 120px;
}


.video-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.video-content p {
  display: block;
  width: 300px;
  margin: 10px;
  padding: 10px;
}

.completeToday {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

#my-one-video .my-video-img {
  width: 360px;
  height: 200px;
  border-radius: 20px;
  object-fit: cover;
}

.content {
  display: inline-block;
  width: 300px;
}
</style>
