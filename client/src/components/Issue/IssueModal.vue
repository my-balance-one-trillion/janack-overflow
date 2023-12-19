<script setup>
import axios from "axios";
import {useAuthStore} from "../../stores/auth";
import {defineProps, onMounted, ref} from "vue";
import dayjs from "dayjs";
const issue = ref({})
const solution = ref({})
const diffFormatted = ref();
const props = defineProps(['solutionId']);

async function getSolution() {
  await axios
      .get(`/issues/solution/${props.solutionId}`, {
        headers: {
          Authorization: useAuthStore().token,
        }
      })
      .then((response) => {
        const data = response.data;
        issue.value = data.issue;
        solution.value = data.solution;
        const diffMinutes = dayjs(data.solution.createdAt).diff(data.issue.createdAt, 'minute', true);
        diffFormatted.value = dayjs().startOf('day').add(diffMinutes, 'minute').format('HH:mm');
      })
      .catch((error) => {
      })
}


onMounted(async () => {
  await getSolution();
});
</script>

<template>
  <div class="fixed left-0 top-0 flex h-full w-full items-center justify-center bg-black bg-opacity-50 py-10">
    <div class="max-h-full w-full max-w-xl overflow-y-auto sm:rounded-2xl bg-white">
      <div class="w-full">
        <div class="m-8 my-20 max-w-[450px] mx-auto">
          <div class="">
            <div class="flex mt-20 items-center">
              <div class="mr-2 text-3xl font-bold">에러 해결 성공</div>
              <img alt="" src="/images/emojiSmile.svg">
            </div>
            <div class="mt-5 space-y-2">
              <div class="text-gray-600 text-xl">에러 해결이 성공적으로 완료되었습니다.</div>
              <div class="text-gray-600 text-xl">지정한 목표 금액에 따라 자동으로 적금되었습니다.</div>
            </div>
          </div>
          <div class="flex justify-evenly my-8">
            <div class="flex">
              <div class="mr-6 text-main-grn font-bold text-xl">걸린 시간</div>
              <div class="text-xl">{{ diffFormatted }}</div>
            </div>
            <div class="flex">
              <div class="mr-6 text-main-grn font-bold text-xl">적금한 금액</div>
              <div class="text-xl">{{ Number(issue.amount).toLocaleString() }}원</div>
            </div>
          </div>
          <div class="flex justify-between">
            <router-link to="/" class="bg-gray-500 hover:bg-gray-400 px-6 py-3 rounded-xl shadow">
              <div class="text-white text-xl font-bold font-main">메인으로 이동</div>
            </router-link>
            <router-link :to='`/community/detail/${issue.id}`' class="bg-main-grn hover:bg-hover-grn px-6 py-3 rounded-xl shadow">
              <div class="text-white text-xl font-bold font-main">상세페이지로 이동</div>
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>