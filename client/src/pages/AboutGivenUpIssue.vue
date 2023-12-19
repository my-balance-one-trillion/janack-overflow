<script setup>
import {FwbBadge} from "flowbite-vue";
import ClipboardJS from 'clipboard';
import javascript from 'highlight.js/lib/languages/javascript';
import 'highlight.js/styles/default.css';
import HighlightJS from 'highlight.js';
import {onMounted, ref} from "vue";
import axios from "axios";
import {useAuthStore} from "../stores/auth";
import {Codemirror} from "vue-codemirror";
import {java} from '@codemirror/lang-java'
import {nord} from "@uiw/codemirror-theme-nord";
import Monaco from "@/components/Monaco.vue";
import dayjs from "dayjs";
import { useRouter, useRoute } from "vue-router";


const currentRoute = useRoute();
const issueId = currentRoute.params.issueId;

const router = useRouter();
const issueCodeBlock = ref(null);
const solutionCodeBlock = ref(null);
const hovered = ref(false);
const issue = ref({});
const keywords = ref([]);
const stackoverflow = ref({});
const createdAt = ref();
const overTime = ref();
const isLoading = ref(true);

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

async function getGivenUpIssue() {
  await axios
      .get('/community/giveup/' + issueId, {
        headers: {
          Authorization: useAuthStore().token,
        }
      })
      .then((response) => {
        console.log("포기 이슈",response.data);
        issue.value = response.data;
        keywords.value = response.data.keyword.split(",");
        createdAt.value = response.data.createdAt;
        isLoading.value = true;
        console.log("포기 이슈2", issue.value);
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
  await getGivenUpIssue();
  updatedTime();
  // setInterval(updatedTime, 30 * 60 * 1000);  // 30분 마다 호출
  setInterval(updatedTime, 10 * 1000);  // 10초 마다 호출
});
</script>

<template>
  <div class="flex flex-col w-10/12 mx-auto">
    <!-- 에러 / 해결 제목박스 -->
    <div
         class="flex items-center justify-center w-full p-2 mt-10 text-3xl text-center rounded-xl bg-bg-grey">
      <div
          class="w-full py-2 mx-1 border-0 rounded-xl bg-main-red text-white font-bold border-b-4 border-white">
        포기한 에러
      </div>
    </div>
  <div v-if="isLoading" class="space-y-14">
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
          <p class="" v-html="issue.content"></p>
          <div>
            <div class="my-5">
              <Monaco :code="issue.code"></Monaco>
          </div>
          </div>
        </div>
      </div>
    </div>

  </div>

  <!--    로딩 -->
  <div v-else class="flex justify-center">
    <svg aria-hidden="true" class="inline w-1/3 my-40 text-gray-200 animate-spin dark:text-gray-600 fill-gray-600 dark:fill-gray-300" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
      <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor"/>
      <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill"/>
    </svg>
  </div>
</div>
</template>

<style scoped>

</style>