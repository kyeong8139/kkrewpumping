import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";
import { useUserChallengeStore } from "./userChallenge";
import { useUserStore } from "./user";

const localhost = `localhost:8080`
const AWS_IP = `3.106.220.75:8080`;
const REST_KREW_API = `http://${AWS_IP}/krew`;

const token = sessionStorage.getItem("access-token")

export const useKrewStore = defineStore("krew", () => {
  ////////해당 챌린지의 크루 확인
  const crewList = ref([]);

  const getCrewList = function (id) {
    axios
      .get(`${REST_KREW_API}/${id}`)
      .then((response) => {
        crewList.value = response.data;
        router.push({ name: "krew" });
      })
      .catch((err) => {
        console.log(err);
      });
  };

  ////////////해당 챌린지 크루 가입하기
  const krewTogether = function (krewid, user) {
    axios
      .put(`${REST_KREW_API}/${krewid}`, user)
      .then(() => {})
      .catch((err) => {
        console.log(err);
      });
  };

  //////////해당 챌린지 크루 만들기
  const userStore = useUserStore();

  const user = ref({
    id: userStore.loginUserId,
  });
  
  const makeNewKrew = function (challengeId, krewData) {
    axios
      .post(`${REST_KREW_API}/${challengeId}`, krewData, {
        headers: { 'access-token': token } // 헤더 설정
      })
      .then((res) => {
        krewTogether(res.data.krewId, user.value);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  ////////해당챌린지 참여 유저 들고오기
  const krewUser = ref([]);
  const ranking = ref("");
  const userChallengeStore = useUserChallengeStore();
  const getKrewRanking = function() {
    userChallengeStore.doingChallengeList.forEach((challenge) => {
      if(challenge.crewId !== -1) {
        axios.get(`${REST_KREW_API}/krew/${challenge.crewId}`)
        .then((res) => {
          krewUser.value = res.data;
          challenge.ranking = krewUser.value.users.findIndex(user => user.userId === sessionStorage.getItem("id"))
          challenge.ranking = challenge.ranking + 1;
          })
          .catch((err) => {
            console.log(err)
          })
      } 

    })
  }

  return {
    crewList,
    getCrewList,
    makeNewKrew,
    krewTogether,
    krewUser,
    getKrewRanking,
    ranking
  };
});
