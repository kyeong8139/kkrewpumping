import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";
import { useKrewStore } from "./krew";

const localhost = `localhost:8080`
const AWS_IP = `3.106.220.75:8080`;
const REST_USERCHALLENGE_API = `http://${AWS_IP}/userchallenge`;
const REST_SEARCH_API = `http://${AWS_IP}/search`;

export const useUserChallengeStore = defineStore("userChallenge", () => {
  ///////////유저가 참여하는 챌린지 들고오기
  const doingChallengeList = ref([]);
  const krewStore = useKrewStore();

  const findMyDoingChallenge = function (id) {
    axios
      .get(`${REST_USERCHALLENGE_API}/${id}/current`)
      .then((response) => {
        doingChallengeList.value = response.data;
        krewStore.getKrewRanking();
      })
      .catch((err) => {
        console.log(err);
      });
  };

  ////////내가 등록한 챌린지

  const myCreateChallenge = ref([]);

  const findRegistChallenge = function (id, query) {
    axios
      .get(`${REST_SEARCH_API}/${id}`, {
        params: query,
      })
      .then((response) => {
        myCreateChallenge.value = response.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  /////완료한 챌린지
  const myCompleteChallenge = ref([]);

  const findCompleteChallenge = function (id) {
    axios
      .get(`${REST_USERCHALLENGE_API}/${id}/finish`)
      .then((response) => {
        myCompleteChallenge.value = response.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  //////내가 등록한 영상

  const myCreateVideo = ref([]);

  const findRegistVideo = function (id, query) {
    axios
      .get(`${REST_SEARCH_API}/${id}`, {
        params: query,
      })
      .then((response) => {
        myCreateVideo.value = response.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  ////////내가 등록한 리뷰

  const myCreateReview = ref([]);

  const findRegistReview = function (id, query) {
    axios
      .get(`${REST_SEARCH_API}/${id}`, {
        params: query,
      })
      .then((response) => {
        myCreateReview.value = response.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  /////////오운완 버튼 누르기
  const clickToday = function (userid, chalengeId) {
    axios
      .put(`${REST_USERCHALLENGE_API}/${userid}/${chalengeId}`)
      .then(() => {})
      .catch((err) => {
        console.log(err);
      });
  };

  return {
    doingChallengeList,
    findMyDoingChallenge,
    myCreateVideo,
    findRegistVideo,
    myCreateChallenge,
    findRegistChallenge,
    myCreateReview,
    findRegistReview,
    myCompleteChallenge,
    findCompleteChallenge,
    clickToday,
  };
});
