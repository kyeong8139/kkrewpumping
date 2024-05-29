<template>
  <div>
    <Carousel :autoplay="3000" :wrap-around="true">
      <Slide v-for="slide in slides" :key="slide">
        <div class="carousel__item">
          <img
            class="slideImg"
            :src="slide.img"
            @click="goToPage(slide.path)"
          />
          <span>{{ slide.title }}</span>
        </div>
      </Slide>

      <template #addons>
        <Pagination />
      </template>
    </Carousel>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { Carousel, Pagination, Slide } from "vue3-carousel";
import "vue3-carousel/dist/carousel.css";

import ChallengeDoing from "@/assets/img/ChallengeDoing.jpg";
import Stretch from "@/assets/img/Stretch.jpg";

import { useRouter } from "vue-router";

///////////카드구현
const slides = ref([
  {
    img: ChallengeDoing,
    title: "현재 진행중인 챌린지",
    path: "/myPage",
  },
  {
    img: Stretch,
    title: "완료한 챌린지",
    path: "/myPage/complete",
  },
]);

const router = useRouter();

const goToPage = (path) => {
  router.push(path);
};
</script>

<style scoped>
.wrapper {
  width: 100%;
  max-width: 800px;
  margin: auto;
}

.carousel__item {
  position: relative;
  width: 100%;
  padding-top: 13%;
  overflow: hidden;
}

.slideImg {
  position: absolute;
  top: 0;
  left: 50%;
  width: 100%;
  height: 100%;
  transform: translate(-50%, 0%);
  object-fit: cover;
  object-position: 50% 30%;
}

.carousel__item span {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 24px; /* 텍스트 크기를 조정하세요 */
  text-align: center;
  text-align: center;
  background-color: black 0.9;
  padding: 5px 10px;
  border-radius: 5px;
  z-index: 1;
}
</style>
