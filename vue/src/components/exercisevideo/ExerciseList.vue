<template>
  <div>
    <h4>운동 영상 목록</h4>
    <hr />
    <div v-if="currentVideoList.length > 0">
      <div v-for="video in currentVideoList" :key="video.id">
        <ul>
          <li id="videoList" @click="goDetail(video.id)">
            <img :src="video.imageUrl" alt="" />
            <p>{{ video.title }}</p>
            <p v-if="video.purpose === 'DIET'">다이어트</p>
            <p v-else-if="video.purpose === 'POSTURE_CORRECTION'">자세교정</p>
            <p v-else>근력강화</p>
            <div v-if="video.supplies && video.supplies.length == 0">
              <p>준비물 없음</p>
            </div>
            <div v-for="supply in video.supplies" :key="supply.id">
              <p>준비물 :{{ supply }}</p>
            </div>
          </li>
        </ul>
      </div>
    </div>

    <div v-else>
      <h2>찾는 운동 영상이 없습니다.</h2>
    </div>

    <nav aria-label="Page navigation">
      <ul class="pagination">
        <li class="page-item" :class="{ disabled: currentPage <= 1 }">
          <a class="page-link" @click.prevent="prevPage" href="#">
            < </a>
        </li>
        <li class="page-item" :class="{ active: currentPage === page }" v-for="page in pageCount" :key="page">
          <a class="page-link" href="#" @click.prevent="clickPage(page)">{{ page }}</a>
        </li>
        <li class="page-item" :class="{ disabled: currentPage >= pageCount }">
          <a class="page-link" @click.prevent="nextPage" href="#"> > </a>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script setup>
import router from "@/router";
import { useVideoStore } from "@/stores/video";
import { ref, computed } from "vue";

const store = useVideoStore();

const goDetail = function (id) {
  router.push({ name: "videoDetail", params: { id: id } });
};

const perPage = 5;
const currentPage = ref(1);

const pageCount = computed(() => {
  return Math.ceil(store.videoList.length / perPage);
});

const clickPage = function (page) {
  currentPage.value = page;
};

const prevPage = function () {
  if (currentPage.value > 1) {
    currentPage.value--;
  }
};

const nextPage = function () {
  if (currentPage.value < pageCount.value) {
    currentPage.value++;
  }
};

const currentVideoList = computed(() => {
  return store.videoList.slice(
    (currentPage.value - 1) * perPage,
    currentPage.value * perPage
  );
});
</script>

<style scoped>
.page-link {
  position: relative;
  display: block;
  padding: 0.5rem 0.75rem;
  margin-left: -1px;
  line-height: 1.25;
  color: #4e4e4e;
  border: 1px solid #ff8911;
  text-align: center;
}

.page-link:hover {
  z-index: 2;
  color: #fff;
  text-decoration: none;
  background-color: #e57c10;
  border-color: #e57c10;
}

.page-item.active .page-link {
  z-index: 3;
  color: #fff;
  background-color: #e57c10;
  border-color: #e57c10;
}

.page-item.disabled .page-link {
  color: #6c757d;
  pointer-events: none;
  background-color: #fff;
  border-color: #dee2e6;
}

.pagination {
  display: flex;
  justify-content: center;
  padding-left: 0;
  list-style: none;
}

.page-item:first-child .page-link {
  margin-left: 0;
  border-top-left-radius: 0.25rem;
  border-bottom-left-radius: 0.25rem;
}

.page-item:last-child .page-link {
  border-top-right-radius: 0.25rem;
  border-bottom-right-radius: 0.25rem;
}

#videoList {
  display: flex;
  justify-content: space-evenly;
  list-style-type: none;
  text-align: center;
  align-items: center;
  margin: 10px 0;
}

#videoList img {
  display: block;
  width: 200px;
  height: 110px;
  object-fit: cover;
  border-radius: 20px;
}

#videoList p {
  margin: 5px 0;
  display: block;
  width: 120px;
}

h2 {
  margin: 10px;
  padding: 10px;
}
</style>
