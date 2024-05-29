import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";

const YOUTUBE_API_KEY="유튜브 API 키"
const YOUTUBE_REST_API="https://www.googleapis.com/youtube/v3"
export const useYoutubeStore = defineStore("youtube", () => {
    
    const youtubeVideoList = ref([])

    const searchYoutubeVideo = function(keyword) {
        axios({
            url : `${YOUTUBE_REST_API}/search`,
            method : "GET",
            params : {
                part : "snippet",
                q : keyword,
                type : "video",
                maxResults : 3,
                key : YOUTUBE_API_KEY
            }
        })
        .then((response) => {
            youtubeVideoList.value = response.data.items
            console.log(youtubeVideoList.value);
        })
    }

  return {
    youtubeVideoList,
    searchYoutubeVideo
  };
});
