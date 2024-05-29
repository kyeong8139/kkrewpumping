import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";

const localhost = `localhost:8080`
const AWS_IP = `3.106.220.75:8080`;
const REST_VIDEO_API = `http://${AWS_IP}/video`; //이거는 수정해야할수도 ?
const REST_SEARCH_API = `http://${AWS_IP}/search`; //이거는 수정해야할수도 ?

const token = sessionStorage.getItem("access-token");

export const useVideoStore = defineStore("video", () => {
  const createVideo = function (video) {
    axios({
      url: REST_VIDEO_API,
      method: "POST",
      data: video,
      headers: { "access-token": token },
    })
      .then((response) => {
        const videoId = response.data.videoId;
        router.push({ name: "videoDetail", params: { id: videoId } });
      })
      .catch((err) => {
        console.log(err);
      });
  };

  //////////비디오 들고오기

  const video = ref({});

  const getVideo = function (id) {
    axios
      .get(`${REST_VIDEO_API}/${id}`)
      .then((response) => {
        video.value = response.data;
      })
      .catch(() => {
        router.push({ name: "error" });
      });
  };

  ///////////////비디오 리스트 만들기
  const videoList = ref([]);

  const searchList = function (query) {
    axios
      .get(REST_SEARCH_API, {
        params: query,
      })
      .then((res) => {
        videoList.value = res.data;

        if (videoList.value.length === 0) {
          alert("검색한 영상이 없습니다!");
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  /////////목적별 비디오 리스트 가져오기
  const videoListForPurpose = ref([]);

  const searchListPurpose = function (query) {
    axios
      .get(REST_VIDEO_API, {
        params: query,
      })
      .then((res) => {
        videoListForPurpose.value = res.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  ////////////비디오 수정

  const updateVideo = function (id) {
    axios.put(`${REST_VIDEO_API}/${id}`, video.value).then(() => {
      router.push({ name: "videoDetail" }).catch((err) => {
        console.log(err);
      });
    });
  };

  return {
    createVideo,
    videoList,
    video,
    getVideo,
    updateVideo,
    searchList,
    videoListForPurpose,
    searchListPurpose,
  };
});
