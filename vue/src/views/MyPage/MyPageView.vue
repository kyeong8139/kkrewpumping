<template>
  <div>
    <div id="my-class-container">
      <aside>
        <div class="profile-link">
          <RouterLink :to="{ name: 'challengeDoingList' }" id="headerName">{{ userStore.loginUserName }} 님</RouterLink>
        </div>
        <div class="profile-link">
          <RouterLink :to="{ name: 'challengeCompleteList' }">완료된 챌린지</RouterLink>
        </div>
        <div class="profile-link">
          <RouterLink :to="{ name: 'myVideo' }">등록한 영상</RouterLink>
        </div>
        <div class="profile-link">
          <RouterLink :to="{ name: 'myChallenge' }">등록한 챌린지</RouterLink>
        </div class="profile-link">
        <div class="profile-link">
          <RouterLink :to="{ name: 'myReview' }">등록한 리뷰</RouterLink>
        </div>
        <div class="profile-link">
          <RouterLink :to="{ name: 'myInfo' }">회원정보수정 | 탈퇴</RouterLink>
        </div>
      </aside>

      <main>
        <div>
          <MyProfile />
        </div>
        <hr />
        <RouterView />
      </main>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from "@/stores/user";
import { onMounted } from "vue";
import MyProfile from "@/components/myPage/MyProfile.vue";

const userStore = useUserStore();

onMounted(() => { });
</script>

<style scoped>
#my-class-container {
  display: flex;
  align-items: top;
}

#my-class-container>aside {
  display: flex;
  justify-items: space-between;
  flex-direction: column;
  padding: 20px;
  text-align: left;
  border-right: gray solid 1px;
  border-top: gray solid 1px;
  border-radius: 50px;
  margin-top: 20px;
}

.profile-link {
  margin: 10px;
  padding: 30px;
}

.profile-link a {
  padding: 15px;
  border-radius: 20px;
  text-decoration: none;
  color: rgb(45, 45, 45);
}

.profile-link a:hover {
  color: white;
  background-color: #ff8911;
}

#headerName {
  text-decoration: none;
  font-size: 21px;
  font-weight: 700;
}

main {
  margin: 0px 30px;
  padding: 50px;
  width: 75%;
}
</style>
