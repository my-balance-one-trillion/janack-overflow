<script setup>
import 'highlight.js/styles/default.css';
import HighlightJS from 'highlight.js';
import {defineEmits, onMounted, ref} from "vue";
import axios from "axios";
import {useAuthStore} from "../../stores/auth";
import router from "../../router";

const issueCodeBlock = ref(null);
const solutionCodeBlock = ref(null);
const createdAt = ref();
const step = ref();
const activeTab = ref();
const emit = defineEmits(['step-changed']);

async function giveupIssue() {
  await axios
      .put('/issues/giveup', {}, {
        headers: {
          Authorization: useAuthStore().token,
        }
      })
      .then(() => {
        window.alert("에러를 해결하지 못했습니다.\n마이페이지에서 내역을 확인할 수 있어요.");
        router.push('/');
      })
}

function submitSolution() {
  step.value = 3;
  activeTab.value = 1;
  emit('step-changed', step.value, activeTab.value);
}

</script>

<template>
  <div class="my-10">
    <!--  button  -->
    <div class="flex justify-between">
      <button class="bg-rose-950 hover:bg-rose-900 m-2 px-10 py-5 rounded-xl shadow" @click="giveupIssue()">
        <div class="text-white text-3xl font-bold font-main">에러 포기</div>
      </button>
      <button class="bg-main-grn hover:bg-hover-grn m-2 px-10 py-5 rounded-xl shadow" @click="submitSolution()">
        <div class="text-white text-3xl font-bold font-main">해결 등록</div>
      </button>
    </div>
  </div>
</template>

<style scoped>

</style>