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
const hovered = ref(false);
const issue = ref({});
const keywords = ref([]);
const stackoverflow = ref({});
const createdAt = ref();
const overTime = ref();
const step = ref(2);
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
    <!--  에러 내역  -->
    <div class="flex items-center justify-center mt-2 w-12/12 font-sub">
      <div class="w-full px-8 py-5 mx-auto my-4 text-lg bg-white rounded-lg shadow-md">
        <div class="w-full">
          <h1 class="py-4 text-3xl font-bold">{{ issue.title }}</h1>
          <div class="flex justify-between mb-4">
            <div class="flex">
              <fwb-badge v-for="keyword in keywords" :key="keyword"
                         class="px-2 mx-1 font-bold text-black rounded bg-badge-bg">{{ keyword }}
              </fwb-badge>
            </div>
            <div class="w-fit">
              <fwb-badge class="px-4 mx-1 text-lg font-bold text-white rounded-full bg-main-red">{{ issue.category }}
              </fwb-badge>
            </div>
          </div>
          <p class="">{{ issue.content }}</p>
          <div>
            <div class="p-3 border-0 rounded-xl bg-bg-grey">
              <div class="flex justify-end">
                <i :class="['p-2', 'fa-lg', 'fa-clipboard', 'issueCodeBlock', 'hover:cursor-pointer', hovered ? 'fa-solid' : 'fa-regular']"
                   @click="copyToClipboard" @mouseout="hovered = false" @mouseover="hovered = true"></i>
                <!-- <i class=" fa-solid fa-copy"></i> -->
              </div>
              <code ref="issueCodeBlock" v-highlightjs class="javascript">{{ issue.code }}</code>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- stopwatch -->
    <div class="my-10 flex justify-between items-center">
      <div class="w-1/3 py-3 px-5 h-auto flex-col justify-between items-center inline-flex bg-gray-100 border border-gray-200 rounded-3xl">
        <div class="w-full py-3 px-6 m-auto bg-white rounded-3xl border border-gray-300 justify-between items-center inline-flex">
          <div :class="{'bg-blue-500': overTime<30, 'bg-gray-100': !(overTime < 30)}"
               class="w-11 h-11 rounded-full shadow-inner border-4 border-gray-400"></div>
          <div :class="{'bg-green-500': overTime>=30 && overTime < 60, 'bg-gray-100': !(overTime>=30 && overTime < 60)}"
               class="w-11 h-11 rounded-full shadow-inner border-4 border-gray-400"></div>
          <div
              :class="{'bg-yellow-300': overTime>=60 && overTime < 180, 'bg-gray-100': !(overTime>=60 && overTime < 180)}"
              class="w-11 h-11 rounded-full shadow-inner border-4 border-gray-400"></div>
          <div :class="{'bg-red-500': overTime>=180, 'bg-gray-100': !(overTime>=180)}"
               class="w-11 h-11 rounded-full shadow-inner border-4 border-gray-400"></div>
        </div>
      </div>
      <div class="space-y-2">
        <div class="text-2xl">에러 등록 후, 해결 시간에 따라 색상이 바뀌어요!</div>
        <div class="text-lg text-gray-500">파란색은 30분 이내, 초록색은 1시간 이내, 노란색은 3시간 이내, 그 이상은 빨강색이에요.</div>
      </div>
    </div>

  </div>
</template>

<style scoped>

</style>