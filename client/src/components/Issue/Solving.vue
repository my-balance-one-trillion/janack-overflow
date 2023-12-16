<script setup>
import {FwbBadge} from "flowbite-vue";
import ClipboardJS from 'clipboard';
import javascript from 'highlight.js/lib/languages/javascript';
import 'highlight.js/styles/default.css';
import HighlightJS from 'highlight.js';
import {defineEmits, onMounted, ref} from "vue";
import axios from "axios";
import {useAuthStore} from "../../stores/auth";
import dayjs from "dayjs";
import router from "../../router";

const issueCodeBlock = ref(null);
const solutionCodeBlock = ref(null);
const issue = ref({});
const keywords = ref([]);
const stackoverflow = ref({});
const createdAt = ref();
const overTime = ref();
const step = ref();
const activeTab = ref();
const emit = defineEmits(['step-changed']);

// 클립보드 복사
let clipboardInstance = null; // ClipboardJS 인스턴스를 저장할 변수

function copyToClipboard(event) {
  const className = '.' + event.target.classList[3];
  console.log(className);

  let targetElement = null;
  if (className === '.solutionCodeBlock') {
    targetElement = solutionCodeBlock.value;
  } else {
    targetElement = issueCodeBlock.value;
  }

  if (clipboardInstance) {
    clipboardInstance.destroy();
  }

  clipboardInstance = new ClipboardJS(className, {
    text: function () {
      return targetElement.innerText;
    }
  });

  clipboardInstance.on('success', function (e) {
    alert('텍스트가 클립보드에 복사되었습니다.');
  });

  clipboardInstance.on('error', function (e) {
    console.error('클립보드 복사 중 오류가 발생했습니다:', e);
  });

}

async function getIssue() {
  await axios
      .get('/issues', {
        headers: {
          Authorization: useAuthStore().token,
        }
      })
      .then((response) => {
        console.log(response.data)
        issue.value = response.data.issue;
        keywords.value = response.data.issue.keyword.split(",");
        createdAt.value = response.data.issue.createdAt;
        stackoverflow.value = response.data.stackOverflowResults;
      })
      .catch((error) => {
        console.log(error);
      })
}

async function giveupIssue() {
  await axios
      .put('/issues/giveup', {},{
        headers: {
          Authorization: useAuthStore().token,
        }
      })
      .then(() => {
        router.push('/');
      })
}

function submitSolution() {
  step.value = 3;
  activeTab.value = 1;
  emit('step-changed', step.value, activeTab.value);
}

function updatedTime() {
  const currentTime = dayjs();
  overTime.value = currentTime.diff(createdAt.value, 'minute');
}

onMounted(async () => {
  await getIssue();
  updatedTime();
  // setInterval(updatedTime, 30 * 60 * 1000);  // 30분 마다 호출
  setInterval(updatedTime, 10 * 1000);  // 10초 마다 호출
});
</script>

<template>
  <div class="space-y-14">
    <!--  stackoverflow  -->
    <div class="mt-14" v-if="stackoverflow">
      <div class="">
        <div class="text-2xl font-bold">이런 방법은 어떠세요?</div>
        <div class="text-gray-500">키워드와 관련된 <span class="text-orange-500">StackOverflow</span> 질문과 답변이 여기에 표시됩니다.</div>
        <div class="text-gray-500">유용한 정보를 찾아서 에러를 빠르게 해결하세요.</div>
      </div>
      <div class="flex justify-evenly p-5 gap-5 mt-5 border-t-4 border-orange-500 bg-bg-grey">
        <div v-for="(result, index) in stackoverflow" :key="index" class="w-2/6 p-4 bg-white rounded-lg shadow-md">
          <a :href="result.link">
            <div class="h-auto font-sub font-bold text-lg text-orange-700 line-clamp-3">{{ result.title }}</div>
            <div class="flex flex-wrap content-start gap-2 my-2">
              <div v-for="tag in result.tags" :key="tag" class="text-sm bg-orange-100 py-1 px-3 rounded-2xl shadow">
                {{ tag }}
              </div>
            </div>
          </a>
        </div>
      </div>
    </div>


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