import { ref, computed, watch } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";

const localhost = `localhost:8080`
const AWS_IP = `3.106.220.75:8080`;
const REST_USER_API = `http://${AWS_IP}/user`;

export const useUserStore = defineStore("user", () => {
  const loginUserId = ref(null);
  const loginUserName = ref("");
  const mySentence = ref(""); //내한마디

  const isLoggedIn = ref(!!sessionStorage.getItem("access-token"));

  //////////////////로그인처리///////////////////
  const userLogin = function (id, password) {
    axios
      .post(`${REST_USER_API}/login`, {
        id: id,
        password: password,
      })
      .then((res) => {
        sessionStorage.setItem("access-token", res.data["access-token"]);

        loginUserId.value = res.data.id;
        loginUserName.value = res.data.nickname; //닉네임받을부분

        sessionStorage.setItem("id", loginUserId.value);
        // localStorage.setItem("id", loginUserId.value);
        sessionStorage.setItem("nickname", loginUserName.value);
        // localStorage.setItem("nickname", loginUserName.value);

        isLoggedIn.value = true;

        router.push({ name: "home" });
      })
      .catch((err) => {
        if (err.response && err.response.status === 401) {
          alert("아이디나 비밀번호가 틀렸습니다");
        } else if (err.response && err.response.status === 500) {
          alert("아이디나 비밀번호가 틀렸습니다");
        }
      });
  };

  /////////////////로그아웃처리/////////////////
  const userLogout = function () {
    axios({
      url: `${REST_USER_API}/logout`,
      method: "GET",
      params: {
        id: loginUserId.value,
      },
    }).then(() => {
      // 클라이언트 로그아웃 처리
      sessionStorage.removeItem("access-token");
      sessionStorage.removeItem("id");
      sessionStorage.removeItem("nickname");
      //로그인원복
      loginUserId.value = null;
      loginUserName.value = "";

      isLoggedIn.value = false;

      // 로그아웃 후 리디렉션
      router.push({ name: "home" });
    });
  };

  ///////////////로그인 유지/////////////////
  const loginStatus = function () {
    const tokenString = sessionStorage.getItem("access-token");

    if (tokenString) {
      loginUserId.value = sessionStorage.getItem("id");
      loginUserName.value = sessionStorage.getItem("nickname");
      isLoggedIn.value = true;
    }
  };

  loginStatus();

  ////////////회원가입처리/////////////
  const userIdCheckResult = ref(false); // ID 중복 체크 결과
  // const nicknameCheckResult = ref(false); // 닉네임 중복 체크 결과

  // ID 중복 체크
  const checkUserId = function (query) {
    // 아이디가 비어있을 경우 중복 확인을 수행하지 않고 반환
    if (!query.word) {
      return;
    }

    userIdCheckResult.value = false; //초기화 다시 진행

    axios
      .get(`${REST_USER_API}/check`, {
        params: query,
      })
      .then(() => {})
      .catch((err) => {
        if (err.response.status === 409) {
          userIdCheckResult.value = true;
        }
      });
  };

  // // 닉네임 중복 체크
  // const checkUserNickname = function (query) {
  //   if (!query.word) {
  //     return;
  //   }

  //   nicknameCheckResult.value = false; //초기화 다시 진행
  //   axios
  //     .get(`${REST_USER_API}/check`, {
  //       params: query,
  //     })
  //     .then(() => {})
  //     .catch((err) => {
  //       if (err.response.status === 409) {
  //         nicknameCheckResult.value = true;
  //       }
  //     });
  // };

  /////회원가입

  const joinUser = function (newUser) {

    if(newUser.nickname === "") {
      newUser.nickname = "닉네임이 없어요"
    }

    axios
      .post(`${REST_USER_API}/regist`, newUser)
      .then(() => {})
      .catch((err) => {
        if (err.response.status === 409) {
          alert("ID 중복을 확인해주세요.");
        } else if (err.response.status === 500) {
          alert("다시 시도해주세요!");
        }
      });
  };

  //// 회원정보 수정
  const updateMyInfo = function (userid, user) {
    mySentence.value = user.introduction; //정보 저장
    console.log(user.nickname)
    axios
      .put(`${REST_USER_API}/${userid}`, user)
      .then(() => {
        alert("수정 완료!");
        loginUserName.value = user.nickname;
        sessionStorage.setItem("nickname", loginUserName.value);
        router.push({ name: "challengeDoingList" });
        location.reload();
      })
      .catch((err) => {
        console.log(err);
      });
  };

  ///회원탈퇴
  const deleteMe = function (id) {
    axios
      .delete(`${REST_USER_API}/${id}`)
      .then(() => {
        // 클라이언트 탈퇴 처리
        sessionStorage.removeItem("access-token");

        loginUserId.value = null;
        loginUserName.value = "";
        alert("다음에 또 만나길 바라요!");
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // 비밀번호 확인
  const isAuthorized = ref(false);

  const checkPassword = function (id, password) {
    axios({
      method: "POST",
      url: `${REST_USER_API}/${id}`,
      data: {
        id,
        password,
      },
    })
      .then(() => {
        isAuthorized.value = true;
        setTimeout(function () {
          isAuthorized.value = false;
        }, 1000 * 60 * 5);
      })
      .catch((err) => {
        if (err.response.status === 409) {
          alert("비밀번호가 틀립니다");
        }
      });
  };

  //// 카카오 로그인
  const doKaKaoLogin = function () {
    Kakao.Auth.authorize({
      redirectUri: "http://kkrewpumping.kro.kr:80/kakao",
      scope: "profile_nickname",
      prompt: "login",
    });
  };

  const postKakaoUser = function (token) {
    axios
      .post(`${REST_USER_API}/login/kakao`, {
        code: token,
      })
      .then((res) => {
        sessionStorage.setItem("access-token", res.data["access-token"]);

        loginUserId.value = res.data.id;
        loginUserName.value = res.data.nickname; //닉네임받을부분

        sessionStorage.setItem("id", loginUserId.value);
        sessionStorage.setItem("nickname", loginUserName.value);
        sessionStorage.setItem("social", "kakao");

        isLoggedIn.value = true;

        router.push({ name: "home" });
      })
      .catch((err) => {
        console.log(err);
        router.push({ name: "login" });
      });
  };

  /  //// 비밀번호 재설정

  const sendEmail = ref(false);
  const assertCode = ref(null);
  const sendCode = function (email) {
    axios({
      url: `${REST_USER_API}/sendemail`,
      method: "GET",
      params: {
        email,
      },
    })
      .then((response) => {
        assertCode.value = response.data.code;
        sendEmail.value = true;
        alert("메일이 정상적으로 전송되었습니다");
      })
      .catch((err) => {
        alert("등록된 아이디가 아닙니다.");
      });
  };

  const setNewPassword = function (user) {
    axios
      .put(`${REST_USER_API}/setPassword`, user)
      .then(() => {
        alert("수정 완료!");
        sendEmail.value = ref(false);
        assertCode.value = null;
        router.push({name : "login"})
      })
      .catch((err) => {
        console.log(err);
      });
  };

  ////////유저 정보 들고오기
  const user = ref({});

  const getInfo = function (id) {
    axios
      .get(`${REST_USER_API}/${id}`)
      .then((res) => {
        user.value = res.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return {
    isLoggedIn,
    mySentence,
    loginUserId,
    userLogin,
    loginUserName,
    userLogout,
    loginStatus,
    userIdCheckResult,
    checkUserId,
    joinUser,
    isAuthorized,
    checkPassword,
    doKaKaoLogin,
    postKakaoUser,
    updateMyInfo,
    deleteMe,
    assertCode,
    sendCode,
    setNewPassword,
    sendEmail,
    user,
    getInfo,
  };
});
