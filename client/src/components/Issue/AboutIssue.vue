<script setup>
import {FwbBadge} from "flowbite-vue";
import 'highlight.js/styles/default.css';
import {onMounted, ref} from "vue";
import axios from "axios";
import {useAuthStore} from "../../stores/auth";
import dayjs from "dayjs";
import {Codemirror} from "vue-codemirror";
import {java} from '@codemirror/lang-java'
import {nord} from "@uiw/codemirror-theme-nord";

const issueCodeBlock = ref(null);
const solutionCodeBlock = ref(null);
const issue = ref({});
const keywords = ref([]);
const stackoverflow = ref({});
const createdAt = ref();
const overTime = ref();
const isLoading = ref(true);
const extensions = ref([java(), nord]);

async function getIssue() {
  await axios
      .get('/issues', {
        headers: {
          Authorization: useAuthStore().token,
        }
      })
      .then((response) => {
        issue.value = response.data.issue;
        keywords.value = response.data.issue.keyword.split(",");
        createdAt.value = response.data.issue.createdAt;
        stackoverflow.value = response.data.stackOverflowResults;
        console.log(stackoverflow.value.length);
        issue.value.content = issue.value.content.split('\n').join('<br>');
        isLoading.value = true;
      })
      .catch((error) => {
        console.log(error);
      })
}

function updatedTime() {
  const currentTime = dayjs();
  overTime.value = currentTime.diff(createdAt.value, 'second');
}

onMounted(async () => {
  await getIssue();
  updatedTime();
  // setInterval(updatedTime, 30 * 60 * 1000);  // 30분 마다 호출
  setInterval(updatedTime, 1 * 1000);  // 10초 마다 호출
});
</script>

<template>
  <div v-if="isLoading" class="space-y-14">
    <!--  에러 내역  -->
    <div class="flex items-center justify-center mt-2 w-12/12 font-sub">
      <div class="w-full px-8 py-5 mx-auto my-4 bg-white rounded-lg shadow-md">
        <div class="w-full ">
          <div class="py-4 text-3xl font-bold break-words">{{ issue.title }}</div>
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
          <div class="my-5">
            <codemirror v-model="issue.code" :autofocus="true" :extensions="extensions" :font-size="20"
                        :indent-with-tab="true" :tab-size="4" class="text-lg" disabled
                        placeholder="// 코드를 입력하세요"/>
          </div>
        </div>
      </div>
    </div>

    <!-- stopwatch 신호등 -->
    <div class="my-10 flex justify-between items-center">
      <div
          class="w-1/3 py-3 px-5 h-auto flex-col justify-between items-center inline-flex bg-gray-100 border border-gray-200 rounded-3xl">
        <div class="w-full py-3 px-6 m-auto bg-white rounded-3xl border border-gray-300 justify-between items-center inline-flex">
<!--          <div :class="{'bg-blue-500': overTime<30, 'bg-gray-100': !(overTime < 30)}"
               class="w-11 h-11 rounded-full shadow-inner border-4 border-gray-400"></div>
          <div :class="{'bg-green-500': overTime>=30 && overTime < 60, 'bg-gray-100': !(overTime>=30 && overTime < 60)}"
               class="w-11 h-11 rounded-full shadow-inner border-4 border-gray-400"></div>
          <div :class="{'bg-yellow-300': overTime>=60 && overTime < 180, 'bg-gray-100': !(overTime>=60 && overTime < 180)}"
              class="w-11 h-11 rounded-full shadow-inner border-4 border-gray-400"></div>
          <div :class="{'bg-red-500': overTime>=180, 'bg-gray-100': !(overTime>=180)}"
               class="w-11 h-11 rounded-full shadow-inner border-4 border-gray-400"></div>-->
          <div :class="{'bg-blue-500': overTime<10, 'bg-gray-100': !(overTime < 10)}"
               class="w-11 h-11 rounded-full shadow-inner border-4 border-gray-400"></div>
          <div :class="{'bg-green-500': overTime>=10 && overTime < 20, 'bg-gray-100': !(overTime>=10 && overTime < 20)}"
               class="w-11 h-11 rounded-full shadow-inner border-4 border-gray-400"></div>
          <div :class="{'bg-yellow-300': overTime>=20 && overTime < 30, 'bg-gray-100': !(overTime>=20 && overTime < 30)}"
               class="w-11 h-11 rounded-full shadow-inner border-4 border-gray-400"></div>
          <div :class="{'bg-red-500': overTime>=30, 'bg-gray-100': !(overTime>=30)}"
               class="w-11 h-11 rounded-full shadow-inner border-4 border-gray-400"></div>
        </div>
      </div>
      <div class="space-y-2">
        <div class="text-2xl">에러 등록 후, 해결 시간에 따라 색상이 바뀌어요!</div>
        <div class="text-lg text-gray-500">
          <span class="text-blue-500">파란색</span>은 30분 이내,
          <span class="text-green-500">초록색</span>은 1시간 이내,
          <span class="text-yellow-300">노란색</span>은 3시간 이내,
          그 이상은 <span class="text-red-500">빨강색</span>이에요.
        </div>
        <div class="text-gray-400 flex"><div class="w-14">{{ overTime }} </div>[ 파란색: 10초 / 초록색 : 20초 / 노란색 : 30초 / 그 이후 빨간색 ]</div>
      </div>
    </div>

    <!--  stackoverflow  -->
    <div class="my-14 mb-10">
      <div class="">
        <div class="text-2xl font-bold">이런 방법은 어떠세요?</div>
        <div class="text-gray-500">키워드와 관련된 <span class="text-orange-500">StackOverflow</span> 질문과 답변이 여기에 표시됩니다.</div>
        <div class="text-gray-500">유용한 정보를 찾아서 에러를 빠르게 해결하세요.</div>
      </div>
      <div class="flex justify-evenly p-5 gap-5 mt-5 border-t-4 border-orange-500 bg-bg-grey">
        <div v-if="stackoverflow.length!==0" v-for="(result, index) in stackoverflow" :key="index" class="w-2/6 p-4 bg-white rounded-lg shadow-md">
          <a :href="result.link" target="_blank">
            <div class="h-20 font-sub font-bold text-lg text-orange-700 line-clamp-3">{{ result.title }}</div>
            <div class="flex flex-wrap content-start gap-2 my-2">
              <div v-for="tag in result.tags" :key="tag">
                <div class="inline-block relative py-1 text-xs">
                  <div class="absolute inset-0 text-orange-200 flex">
                    <svg height="100%" viewBox="0 0 50 100">
                      <path
                          d="M49.9,0a17.1,17.1,0,0,0-12,5L5,37.9A17,17,0,0,0,5,62L37.9,94.9a17.1,17.1,0,0,0,12,5ZM25.4,59.4a9.5,9.5,0,1,1,9.5-9.5A9.5,9.5,0,0,1,25.4,59.4Z"
                          fill="currentColor"/>
                    </svg>
                    <div class="flex-grow h-full -ml-px bg-orange-200 rounded-md rounded-l-none"></div>
                  </div>
                  <span class="relative text-orange-500 uppercase pr-px">
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>{{ tag }}<span>&nbsp;</span>
			            </span>
                </div>
              </div>
            </div>
          </a>
        </div>
        <div v-else class="p-10 text-gray-600">StackOverflow 연관글이 없습니다.</div>
      </div>
    </div>

  </div>

  <!--    로딩 -->
  <div v-else class="flex justify-center">
    <svg aria-hidden="true"
         class="inline w-1/3 my-40 text-gray-200 animate-spin dark:text-gray-600 fill-gray-600 dark:fill-gray-300"
         fill="none" viewBox="0 0 100 101" xmlns="http://www.w3.org/2000/svg">
      <path
          d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
          fill="currentColor"/>
      <path
          d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
          fill="currentFill"/>
    </svg>
  </div>
</template>

<style scoped>

</style>