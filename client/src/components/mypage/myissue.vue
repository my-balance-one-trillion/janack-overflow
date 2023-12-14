<template>
  <h3 class="text-3xl text-main-red font-bold mb-7">내가 등록한 에러 목록</h3>
  <div class="border-b-4 border-main-red"></div>
  <p class="text-xl my-4">에러 목록</p>
  <p class="text-[#919090]">작성한 에러를 확인할 수 있습니다.</p>
  <article class="">
    <div
      class="w-full xl:w-8/12 m-12 xl:mb-0 px-4 mx-auto mt-2 flex flex-col items-center"
    >
      <div
        class="relative flex flex-col min-w break-words bg-white w-full my-12 shadow-lg"
      >
        <div class="block overflow-x-auto">
          <table class="min-w-full">
            <thead class="bg-white border-b">
              <tr>
                <th
                  scope="col"
                  class="text-m font-medium text-sub-red px-6 py-4 text-left w-1/12"
                >
                  에러명
                </th>
                <th
                  scope="col"
                  class="text-m font-medium text-sub-red px-6 py-4 text-left w-5/12"
                >
                  등록일
                </th>
                <th
                  scope="col"
                  class="text-sm font-medium text-sub-red px-6 py-4 text-left w-2/12"
                >
                  카테고리
                </th>
                <th
                  scope="col"
                  class="text-sm font-medium text-sub-red px-6 py-4 text-left w-2/12"
                >
                  조회수
                </th>
                <th
                  scope="col"
                  class="text-sm font-medium text-sub-red px-6 py-4 text-left w-2/12"
                >
                  해결여부
                </th>
              </tr>
            </thead>
            <tbody>
              <tr class="bg-white border-b" v-for="item in issueList.content">
         
                <td
                  class="text-sm text-gray-900 font-light px-6 py-4 whitespace-nowrap"
                >
                  {{ item.title }}
                </td>
                <td
                  class="text-sm text-gray-900 font-light px-6 py-4 whitespace-nowrap"
                >
                  {{ item.createdAt }}

                </td>
                <td
                  class="text-sm text-gray-900 font-light px-6 py-4 whitespace-nowrap"
                >
                  {{ item.category }}
                </td>
                <td
                  class="text-sm text-gray-900 font-light px-6 py-4 whitespace-nowrap"
                >
                  {{ item.views }}
                </td>
                <td
                  class="text-sm text-gray-900 font-light px-6 py-4 whitespace-nowrap"
                >
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
            <a
              href="#"
              class="flex items-center justify-center px-3 h-8 ms-0 leading-tight text-gray-500 bg-white border border-e-0 border-gray-300 rounded-s-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
              >Previous</a
            >
          </li>
          <li>
            <a
              href="#"
              class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
              >1</a
            >
          </li>
          <li>
            <a
              href="#"
              class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
              >2</a
            >
          </li>
          <li>
            <a
              href="#"
              aria-current="page"
              class="flex items-center justify-center px-3 h-8 text-main-red border border-gray-300 bg-red-100 hover:bg-red-200 hover:text-hover-red dark:border-gray-700 dark:bg-gray-700 dark:text-white"
              >3</a
            >
          </li>
          <li>
            <a
              href="#"
              class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
              >4</a
            >
          </li>
          <li>
            <a
              href="#"
              class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
              >5</a
            >
          </li>
          <li>
            <a
              href="#"
              class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 rounded-e-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
              >Next</a
            >
          </li>
        </ul>
      </nav>
    </div>
  </article>
</template>
<script setup>
import { ref } from 'vue';
import axios from 'axios';
import { useAuthStore } from "@/stores/auth";
 
const issueList = ref([]);

getIssueList();
async function getIssueList(){
  const response = await axios.get("/mypage/myissue",{
    headers:{
      "Authorization": useAuthStore().token
    }
  });
  issueList.value = response.data;
  console.log(issueList.value.content);
}

</script>
<style scoped></style>
