import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import VideoView from "@/views/VideoView.vue";
import LoginView from "@/views/Login/LoginView.vue";
import MyPageView from "@/views/MyPage/MyPageView.vue";
import JoinView from "@/views/Login/JoinView.vue";
import ChallengeView from "@/views/Challenge/ChallengeView.vue";
import ChallengeReviewView from "@/views/Challenge/ChallengeReviewView.vue";

import KrewView from "@/views/Krew/KrewView.vue";
import KrewCreate from "@/views/Krew/KrewCreate.vue";

import ExerciseCreate from "@/components/exercisevideo/ExerciseCreate.vue";
import ExerciseDetail from "@/components/exercisevideo/ExerciseDetail.vue";
import ExerciseList from "@/components/exercisevideo/ExerciseList.vue";
import ExerciseUpdate from "@/components/exercisevideo/ExerciseUpdate.vue";

import ChallengeList from "@/components/challenge/ChallengeList.vue";
import ChallengeCreate from "@/components/challenge/ChallengeCreate.vue";
import ChallengeDetail from "@/components/challenge/ChallengeDetail.vue";
import ChallengeUpdate from "@/components/challenge/ChallengeUpdate.vue";

import ChallengeDoingList from "@/components/myPage/ChallengeDoingList.vue";
import ChallengeCompleteList from "@/components/myPage/ChallengeCompleteList.vue";
import MyVideo from "@/components/myPage/MyVideo.vue";
import MyChallenge from "@/components/myPage/MyChallenge.vue";
import MyReview from "@/components/myPage/MyReview.vue";
import MyInfo from "@/components/myPage/MyInfo.vue";
import MyChallengeDetail from "@/components/challenge/MyChallengeDetail.vue";
import KakaoView from "@/views/Login/KakaoView.vue";
import ResetPasswordView from "@/views/Login/ResetPasswordView.vue";
import ErrorView from "@/views/ErrorView.vue";

const checkLogin = function () {
  return !!sessionStorage.getItem("access-token");
};

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/video",
      name: "Video",
      component: VideoView,
      children: [
        {
          path: "",
          name: "videoList",
          component: ExerciseList,
        },
        {
          path: ":id",
          name: "videoDetail",
          component: ExerciseDetail,
        },
        {
          path: "create",
          name: "videoCreate",
          component: ExerciseCreate,
        },
        {
          path: ":id/update",
          name: "videoUpdate",
          component: ExerciseUpdate,
        },
      ],
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
    },
    {
      path: "/myPage",
      name: "myPage",
      component: MyPageView,
      children: [
        {
          path: "",
          name: "challengeDoingList",
          component: ChallengeDoingList,
          beforeEnter(to, from, next) {
            if (checkLogin() === true) {
              next();
            } else {
              next({ name: "login" });
            }
          },
        },
        {
          path: "complete",
          name: "challengeCompleteList",
          component: ChallengeCompleteList,
          beforeEnter(to, from, next) {
            if (checkLogin() === true) {
              next();
            } else {
              next({ name: "login" });
            }
          },
        },
        {
          path: "video",
          name: "myVideo",
          component: MyVideo,
        },
        {
          path: "challenge",
          name: "myChallenge",
          component: MyChallenge,
        },
        {
          path: "review",
          name: "myReview",
          component: MyReview,
        },
        {
          path: "info",
          name: "myInfo",
          component: MyInfo,
        },
      ],
    },
    {
      path: "/mydetail/:id",
      name: "myChallengeDetail",
      component: MyChallengeDetail,
    },
    {
      path: "/join",
      name: "join",
      component: JoinView,
    },
    {
      path: "/challenge",
      name: "challenge",
      component: ChallengeView,
      children: [
        {
          path: "",
          name: "challengeList",
          component: ChallengeList,
        },
        {
          path: "create",
          name: "challengeCreate",
          component: ChallengeCreate,
        },
        {
          path: ":id",
          name: "challengeDetail",
          component: ChallengeDetail,
          children: [
            {
              path: "review",
              name: "challengeReview",
              component: ChallengeReviewView,
            },
          ],
        },
        {
          path: "update",
          name: "challengeUpdate",
          component: ChallengeUpdate,
        },
      ],
    },
    {
      path: "/krew/:id",
      name: "krew",
      component: KrewView,
      children: [
        {
          path: "create",
          name: "createKrew",
          component: KrewCreate,
        },
      ],
    },
    {
      path: "/kakao",
      name: "kakao",
      component: KakaoView,
    },
    {
      path: "/resetPassword",
      name: "resetPassword",
      component: ResetPasswordView,
    },
    {
      path: "/:pathMatch(.*)*",
      name: "error",
      component: ErrorView,
    },
  ],
});

export default router;
