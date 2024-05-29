import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";
import { GoogleGenerativeAI } from "@google/generative-ai";
import { useVideoStore } from "./video";

const localhost = `localhost:8080`
const AWS_IP = `3.106.220.75:8080`;
const GOOGLE_GEMINI_API_KEY = "GEMINI API 키";
const REST_VIDEO_API = `http://${AWS_IP}/video`;
export const useAIStore = defineStore("ai", () => {
  const genAI = new GoogleGenerativeAI(GOOGLE_GEMINI_API_KEY);
  const model = genAI.getGenerativeModel({ model: "gemini-pro" });

  const generateDescription = async function (url) {
    const prompt =
      "내가 주는 운동 영상에 대한 140자 정도의 한국어 설명을 만들어줘. 설명에는 이 운동 영상의 목적과 운동 부위, 난이도에 대한 내용이 포함되어야해. 영상의 주소는 다음과 같아." +
      url;

    const result = await model.generateContent(prompt);
    const response = result.response;
    const text = response.text();

    return text;
  };

  const purpose = ref("");
  const generateRecommandVideo = async function (query) {
    const queryForList = {
      key: "purpose",
      word: query.purpose,
    };

    let prompt = "";

    axios
      .get(REST_VIDEO_API, {
        params: queryForList,
      })
      .then((res) => {
        const plainData = JSON.stringify(res.data);
        prompt =
          "video에 대한 정보를 담은 json형식의 텍스트에서 description을 보고 내 요청사항에 어울리는 id 값을 알려줘. 답변을 줄 때는 다른 말은 아무것도 붙이지 말고 숫자값만 보내줘 " +
          query.userRequest +
          "야. 데이터는 다음과 같아" +
          plainData;
        getRecommandVideoId(prompt);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const getRecommandVideoId = async function (prompt) {
    const result = await model.generateContent(prompt);
    const response = result.response;
    const videoId = response.text();

    router.push({ name: "videoDetail", params: { id: videoId } });
  };

  return {
    generateDescription,
    generateRecommandVideo,
    purpose,
  };
});
