import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";

const localhost = `localhost:8080`
const AWS_IP = `3.106.220.75:8080`;
const REST_CHALEENGE_API = `http://${AWS_IP}/challenge`;
const REST_SEARCH_API = `http://${AWS_IP}/search`;
const REST_USERCHALLENGE_API = `http://${AWS_IP}/userchallenge`;

const token = sessionStorage.getItem("access-token")

export const useChallengeStore = defineStore("challenge", () => {
  const createChallenge = function (challenge) {
    axios({
      url: REST_CHALEENGE_API,
      method: "POST",
      data: challenge,
      headers: { 'access-token': token }
    })
      .then((response) => {
        const chalengeId = response.data.challengeId;
        router.push({ name: "challengeDetail", params: { id: chalengeId } });
      })
      .catch((err) => {
        router.push({name : "error"});
      });
  };

  /////////챌린지 불러오기
  const challenge = ref({});

  const getChallenge = function (id) {
    axios
      .get(`${REST_CHALEENGE_API}/${id}`)
      .then((response) => {
        challenge.value = response.data;
      })
      .catch((err) => {
        router.push({name : "error"});
      });
  };

  //////전체 챌린지 리스트 가져오기
  const allChallengeList = ref([]);

  const getAllChallenge = function () {
    axios
      .get(REST_CHALEENGE_API)
      .then((response) => {
        allChallengeList.value = response.data;
      })
      .catch((err) => {
        router.push({name : "error"});
      });
  };

  /////////검색챌린지 리스트 만들기
  const challengeList = ref([]);

  const searchList = function (query) {
    axios
      .get(REST_SEARCH_API, {
        params: query,
      })
      .then((response) => {
        challengeList.value = response.data;

        if (challengeList.value.length === 0) {
          alert("검색한 챌린지가 없습니다!");
        }
      })
      .catch((err) => {
        router.push({name : "error"});
      });
  };

  /////////챌린지 혼자참여하기
  const startChallenge = function (userId, challengeId) {
    axios
      .post(`${REST_USERCHALLENGE_API}/${userId}/${challengeId}`, {}, // POST 데이터가 없으면 빈 객체 전달
      {
        headers: { 'access-token': token } // 헤더 설정
      })
      .then(() => {
        router.push({ name: "challengeDoingList" });
      })
      .catch((err) => {
        router.push({name : "error"});
      });
  };

////////////챌린지리뷰

    /////오운완
    const getMyChallenge = function (userid, challengeId) {
      axios
        .get(`${REST_USERCHALLENGE_API}/${userid}/${challengeId}`)
        .then((response) => {
          challenge.value = response.data;
        })
        .catch((err) => {
          router.push({name : "error"});
        });
    };
  
    const clickToday = function (userid, challengeId, index) {
      axios
        .put(`${REST_USERCHALLENGE_API}/${userid}/${challengeId}/${index}`)
        .then((response) => {
          challenge.value = response.data.challenge;
        })
        .catch((err) => {
          router.push({name : "error"});
        });
    };


  return {
    createChallenge,
    allChallengeList,
    getAllChallenge,
    challenge,
    getChallenge,
    challengeList,
    searchList,
    startChallenge,
    clickToday,
    getMyChallenge,

  };
});
