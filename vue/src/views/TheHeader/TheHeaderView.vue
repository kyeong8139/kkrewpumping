<template>
  <div id="myHeader">
    <RouterLink :to="{ name: 'home' }" id="my-logo-link"><img src="../../assets/icons/크크루펌핑로고.png" alt="" id="my-logo" />
    </RouterLink>
    <div id="searchForm">
      <select name="findVideo" id="findVideo" v-model="searchInfo.key">
        <option value="challenge">챌린지</option>
        <option value="video">운동 영상</option>
      </select>
      <input type="text" v-model="searchInfo.word" @keyup.enter="searchList" />
      <button style="background-color: white;" @click="searchList"><svg xmlns="http://www.w3.org/2000/svg" width="16"
          height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
          <path
            d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0" />
        </svg></button>
    </div>
    <button style="background-color: white;" @click="openMessage"><svg xmlns="http://www.w3.org/2000/svg" width="24"
        height="24" fill="currentColor" class="bi bi-bell" viewBox="0 0 16 16">
        <path
          d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2M8 1.918l-.797.161A4 4 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4 4 0 0 0-3.203-3.92zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5 5 0 0 1 13 6c0 .88.32 4.2 1.22 6" />
      </svg></button>
    <Teleport to="body">
      <div v-if="messageOpen" class="modal">
        <div v-if="messageStore.messageList.length === 0">
          <p id="noContent">도착한 메세지가 없어요!</p>
        </div>
        <div v-else>
          <div v-for="message in messageStore.messageList">
            <div id="messageblock">
              <div class="messageTitle">
                <p style="color: rgb(187, 187, 187)">{{ message.fromUser }}</p>
                <p style="color: rgb(187, 187, 187)">{{ message.fromDateTime }}</p>
              </div>
              <p>{{ message.content }}</p>
            </div>
          </div>
        </div>
        <button id="closeModal" @click="messageOpen = false">닫기</button>
      </div>
    </Teleport>
    <div v-if="userStore.loginUserId == null">
      <RouterLink :to="{ name: 'login' }">로그인</RouterLink>
    </div>
    <div v-else>
      <RouterLink to="/myPage"> {{ userStore.loginUserName }}님</RouterLink>
      <button style="background-color: white;" @click="logout"><svg xmlns="http://www.w3.org/2000/svg" width="21"
          height="21" fill="currentColor" class="bi bi-door-open" viewBox="0 0 16 16">
          <path d="M8.5 10c-.276 0-.5-.448-.5-1s.224-1 .5-1 .5.448.5 1-.224 1-.5 1" />
          <path
            d="M10.828.122A.5.5 0 0 1 11 .5V1h.5A1.5 1.5 0 0 1 13 2.5V15h1.5a.5.5 0 0 1 0 1h-13a.5.5 0 0 1 0-1H3V1.5a.5.5 0 0 1 .43-.495l7-1a.5.5 0 0 1 .398.117M11.5 2H11v13h1V2.5a.5.5 0 0 0-.5-.5M4 1.934V15h6V1.077z" />
        </svg></button>
    </div>
  </div>
</template>

<script setup>
import { useVideoStore } from "@/stores/video";
import { useUserStore } from "@/stores/user";
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useChallengeStore } from "@/stores/challenge";
import { useMessageStore } from "@/stores/message";

/////////로그인 유저되면 바뀌기///////
const userStore = useUserStore();

const logout = function () {
  userStore.userLogout();
};

//////////밑은 검색처리///////////
const videoStore = useVideoStore();
const challengeStore = useChallengeStore();

const searchInfo = ref({
  key: "challenge",
  word: "",
});

const router = useRouter();

const searchList = function () {
  const query = {
    key: searchInfo.value.key,
    word: searchInfo.value.word,
  };

  if (query.key === "challenge") {
    challengeStore.searchList(query);
    router.push({ name: "challengeList", query });
  } else if (query.key === "video") {
    videoStore.searchList(query);
    router.push({ name: "videoList", query });
  } else {
    //여기는 유저로 보내기
  }
};

// 쪽지 구현
const messageStore = useMessageStore();

const messageOpen = ref(false);
const openMessage = function () {
  messageStore.getMessage(userStore.loginUserId);
  messageOpen.value = true;
};
</script>

<style scoped>
a {
  text-decoration: none;
}

#my-logo-link {
  display: inline-block;
  width: 250px;
}

#my-logo {
  width: 100%;
  height: auto;
}

#findVideo,
input[type="text"],
button {
  margin: 5px;
}

button {
  padding: 3px;
  margin: 5px;
  background-color: white;
}

#searchForm {
  display: flex;
  align-items: center;
  border: 2px solid #ccc;
  border-radius: 50px;
  padding: 10px;
}

#findVideo,
input[type="text"],
button {
  margin: 0;
  border: none;
}

input[type="text"] {
  flex-grow: 1;
  padding: 5px;
  outline: none;
}

#myHeader {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0.5em 3em 0.5em;
}

.modal {
  display: flex;
  justify-content: space-around;
  flex-direction: column;
  background-color: rgba(255, 255, 255, 0.95);
  position: fixed;
  z-index: 1;
  top: 10%;
  left: 75%;
  width: 400px;
  height: 70%;
  margin-left: -150px;
  border-radius: 10px;
  padding: 3%;
  box-shadow: 3px 3px 15px gray;
}

.messageTitle {
  display: flex;
  justify-content: space-evenly;
}

#messageblock {
  display: flex;
  flex-direction: column;
  justify-content: center;
  border-bottom: 0.5px solid gray;
  padding: 5px;
}

#messageblock p {
  margin: 5px;
  padding: 5px;
  line-height: 180%;
}

#noContent {
  text-align: center;
}

#closeModal {
  background-color: rgba(255, 255, 255, 1);
  padding: 20px;
  color: #ff8911;
  font-size: 14px;
}
</style>
