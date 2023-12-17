<script setup>
const keywords = ref([]);

import {ref, defineEmits} from "vue";
import axios from "axios";
import {useAuthStore} from "@/stores/auth";

const solution = ref({
  code: '',
  content: '',
  publicStatus: true
});
const emit = defineEmits(['show-modal']);

async function submitSolution() {
  await axios
      .post('/issues/solution', solution.value,
          {
            headers: {
              Authorization: useAuthStore().token,
            }
          }
      )
      .then((response) => {
        emit('show-modal', true, response.data.id);
      })
      .catch((error) => {
      })
}
</script>
<template>
  <div class="flex items-center justify-center mt-2 w-12/12 font-sub">
    <div class="w-full px-8 py-5 mx-auto my-4 text-lg bg-white rounded-lg shadow-md">
      <!--  에러 내용  -->
      <textarea
          v-model="solution.content"
          class="my-5 w-full h-48 p-5 resize-none text-gray-700 text-2xl font-light bg-transparent border-4 rounded-2xl border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-main-grn"
          name="content"
          placeholder="에러에 대한 해결 내용을 작성해주세요."
          required
          type="text"
      />
      <!--에러 코드-->
      <div>
        <input v-model="solution.code" class="border-b-4"
               placeholder="코드"
        >
      </div>

    </div>
  </div>
  <!--동의 여부-->
  <div>
    <label class="flex items-center cursor-pointer relative" for="toggle-checked">
      <input id="toggle-checked" v-model="solution.publicStatus" checked="" class="sr-only" type="checkbox">
      <div class="toggle-bg bg-gray-200 border-2 border-gray-200 h-6 w-11 rounded-full"></div>
      <span class="ml-3 text-gray-900 text-sm">동의할 경우, 작성한 에러가 자동으로 커뮤니티에 공개됩니다.</span>
    </label>
  </div>

  <div class="flex justify-between items-center">
    <div>
      <div>에러가 성공적으로 해결되었다면, 해결 완료 버튼을 눌러주세요.</div>
      <div>시간에 따라 자동으로 적금되어, 당신의 성장을 기록합니다.</div>
    </div>
    <div>
      <button class="bg-main-grn hover:bg-hover-grn m-2 px-10 py-5 rounded-xl shadow" @click="submitSolution()">
        <div class="text-white text-3xl font-bold font-main">에러 해결 완료</div>
      </button>
    </div>
  </div>


</template>

<style scoped>
</style>