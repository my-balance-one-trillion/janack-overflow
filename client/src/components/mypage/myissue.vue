<template>
  <h3 class="text-3xl text-main-red font-bold mb-7">내가 등록한 에러 목록</h3>
  <div class="border-b-4 border-main-red"></div>
  <p class="text-xl my-4">에러 목록</p>
  <p class="text-[#919090]">작성한 에러를 확인할 수 있습니다.</p>
  <article class="">
    <div class="w-full xl:w-8/12 m-12 xl:mb-0 px-4 mx-auto mt-2 flex flex-col items-center">
      <div class="relative flex flex-col min-w break-words bg-white w-full my-12 shadow-lg">
        <div class="block overflow-x-auto">
          <table class="min-w-full">
            <thead class="bg-white border-b">
              <tr>
                <th scope="col" class="text-m font-medium text-sub-red px-6 py-4 text-left w-1/12">
                  에러명
                </th>
                <th scope="col" class="text-m font-medium text-sub-red px-6 py-4 text-left w-5/12">
                  등록일
                </th>
                <th scope="col" class="text-sm font-medium text-sub-red px-6 py-4 text-left w-2/12">
                  카테고리
                </th>
                <th scope="col" class="text-sm font-medium text-sub-red px-6 py-4 text-left w-2/12">
                  조회수
                </th>
                <th scope="col" class="text-sm font-medium text-sub-red px-6 py-4 text-left w-2/12">
                  해결여부
                </th>
              </tr>
            </thead>
            <tbody>
              <tr class="bg-white border-b" v-for="item in issueList">

                <td class="text-sm text-gray-900 font-light px-6 py-4 whitespace-nowrap">
                  {{ item.title }}
                </td>
                <td class="text-sm text-gray-900 font-light px-6 py-4 whitespace-nowrap">
                  {{ item.createdAt }}

                </td>
                <td class="text-sm text-gray-900 font-light px-6 py-4 whitespace-nowrap">
                  {{ item.category }}
                </td>
                <td class="text-sm text-gray-900 font-light px-6 py-4 whitespace-nowrap">
                  {{ item.views }}
                </td>
                <td class="text-sm text-gray-900 font-light px-6 py-4 whitespace-nowrap">
                  <span v-if="item.status == '01'" class="bg-main-red text-white rounded-lg p-1"> 진행중</span>
                  <span v-if="item.status == '02'" class="bg-yellow-300 text-white rounded-lg p-1"> 포기</span>
                  <span v-if="item.status == '03'" class="bg-main-grn text-white rounded-lg p-1"> 해결</span>
                </td>
              </tr>

            </tbody>
          </table>
        </div>
      </div>
      <nav aria-label="Page navigation example">
        <ul class="inline-flex -space-x-px text-sm">
          <li>
            <div
              class="flex items-center justify-center px-3 h-8 ms-0 leading-tight text-gray-500 bg-white border border-e-0 border-gray-300 rounded-s-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">
              Previous</div>
          </li>
          <li v-for="i in pageInt">
            <div
              class="flex items-center justify-center px-3 h-8 leading-tight text-main-red bg-white border border-gray-300 hover:bg-red-200 hover:text-hover-red dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
              @click="getIssueList(i)" v-if="pageSet.page == i">
              {{ i + 1 }}</div>
            <div
              class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
              @click="getIssueList(i)" v-else>
              {{ i + 1 }}</div>
          </li>

          <li>
            <div href="#"
              class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 rounded-e-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">
              Next</div>
          </li>
        </ul>
      </nav>
    </div>
  </article>
</template>
<script setup>
import { reactive, ref } from 'vue';
import axios from 'axios';
import { useAuthStore } from "@/stores/auth";

const issueList = ref([]);
const pageSet = ref([]);
let pageInt = reactive([]);

getIssueList(0);
async function getIssueList(i) {
  const response = await axios.get(`/mypage/myissue?page=${i}&size=5`, {
    headers: {
      "Authorization": useAuthStore().token
    }
  });
  const { data, pageDTO, pageNumber } = response.data;
  issueList.value = data;
  pageSet.value = pageDTO;
  pageInt = pageNumber;
  console.log(pageInt);
  console.log(response.data);
}

</script>
<style scoped></style>
