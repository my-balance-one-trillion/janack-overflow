<template>
  <LoggedInMain
    v-if="isLoggedin && Object.keys(nowAccount).length > 0"
    :nowAccount="nowAccount"
    :weeklyIssues="weeklyIssues"
  />
  <LoggedOutMain v-else :isLoggedin="isLoggedin" />
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useAuthStore } from "../stores/auth";
import LoggedInMain from "@/components/main/LoggedInMain.vue";
import LoggedOutMain from "@/components/main/LoggedOutMain.vue";
import axios from "axios";

const isLoggedin = ref();
const nowAccount = ref({});
const weeklyIssues = ref([]);

isLoggedin.value = localStorage.getItem("token");
console.log("로그인 여부", isLoggedin.value);

onMounted(async () => {
  if (isLoggedin.value) {
    await axios
      .get("/main/login", {
        headers: {
          authorization: useAuthStore().token,
        },
      })
      .then((response) => {
        console.log("상태", response.status);
        if (response.data.nowAccount != undefined) {
          nowAccount.value = response.data.nowAccount;
          console.log("계좌 정보", response.data.nowAccount);
          console.log("이슈 정보", response.data.weeklyIssues.length);
        }
        if (response.data.weeklyIssues) {
          weeklyIssues.value = response.data.weeklyIssues;
        }
        console.log("통신 후 값", nowAccount.value, weeklyIssues.value);
        console.log("길이", Object.keys(nowAccount.value).length);
      })
      .catch((error) => {
        console.error("에러 발생", error.response);
      });
  }
});
</script>

<style scoped></style>
