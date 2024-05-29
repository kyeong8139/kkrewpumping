import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";

const localhost = `localhost:8080`
const AWS_IP = `3.106.220.75:8080`;
const REST_CHALEENGE_API = `http://${AWS_IP}/challenge`;
const REST_SEARCH_API = `http://${AWS_IP}/search`;
const REST_USER_API = `http://${AWS_IP}/userchallenge`;

const token = sessionStorage.getItem("access-token")

export const useReviewStore = defineStore("review", () => {
  const review = ref({
    writer: "",
    content: "",
  });

  const reviewList = ref([]);

  const getReview = function (challengeId) {
    axios
      .get(`${REST_CHALEENGE_API}/${challengeId}/review`)
      .then((response) => {
        reviewList.value = response.data;
      });
  };

  const createReview = function (challengeId) {
    axios({
      url: `${REST_CHALEENGE_API}/${challengeId}/review`,
      method: "POST",
      data: review.value,
      headers: { 'access-token': token }
    }).then(() => {
      getReview(challengeId);
    });

    review.value.content = "";
  };

  const deleteReview = function(challengeId, reviewId){
    axios.delete(`${REST_CHALEENGE_API}/${challengeId}/review/${reviewId}`)
    .then(()=>{
      router.push({name: "myReview"})
    })
    .catch((err)=>{
      console.log(err)
    })
  }

  return {
    review,
    reviewList,
    getReview,
    createReview,
    deleteReview,
  };
});
