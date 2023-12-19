<script setup>
import {Codemirror} from "vue-codemirror";
import {ref, defineEmits} from "vue";
import axios from "axios";
import {useAuthStore} from "@/stores/auth";
import {java} from "@codemirror/lang-java";
import {nord} from "@uiw/codemirror-theme-nord";

const solution = ref({
  code: '',
  content: '',
  publicStatus: true
});
const emit = defineEmits(['show-modal']);
const extensions = [java(), nord];

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
          class="my-5 w-full h-48 p-5 resize-none text-gray-700 text-xl font-light bg-transparent border-4 rounded-2xl border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-main-grn"
          name="content"
          placeholder="에러에 대한 해결 내용을 작성해주세요."
          required
          type="text"
      />
      <!--에러 코드-->
      <div>
        <codemirror v-model="solution.code" :autofocus="true" :extensions="extensions"
                    :font-size="20" :indent-with-tab="true" :style="{ height: '300px' }" :tab-size="4" class="text-lg" placeholder="// 코드를 입력하세요"/>
      </div>

    </div>
  </div>
  <!--동의 여부-->
  <div class="my-5">
    <label class="relative inline-flex items-center me-5 cursor-pointer">
      <input type="checkbox" v-model="solution.publicStatus" class="sr-only peer" checked>
      <div class="w-11 h-6 bg-gray-200 rounded-full peer peer-focus:ring-4 peer-focus:ring-green-300  peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-0.5 after:start-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-green-600"></div>
      <span class="ms-3 text-sm font-medium text-gray-900">동의할 경우, 작성한 에러가 자동으로 커뮤니티에 공개됩니다.</span>
    </label>
  </div>

  <div class="flex justify-between items-center my-7">
    <div class="space-y-3">
      <div class="text-lg">에러가 성공적으로 해결되었다면, 해결 완료 버튼을 눌러주세요.</div>
      <div class="text-md">시간에 따라 자동으로 적금되어, 당신의 성장을 기록합니다.</div>
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