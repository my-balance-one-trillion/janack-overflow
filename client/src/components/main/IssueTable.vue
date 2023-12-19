<template>
  <!-- component -->
  <div class="antialiased font-main">
    <div class="px-4 mx-auto sm:px-8">
      <div class="py-8">
        <div class="px-4 py-4 mx-4 sm:mx-8 sm:px-8">
          <div
            class="flex justify-center mx-auto shadow rounded-lg overflow-y-scroll max-h-[500px]"
          >
            <table class="w-full leading-normal table-auto">
              <tbody v-if="issueList.length == 0" class="flex flex-col items-center w-full">
                <tr class="text-center">
                  <td class="p-3 text-sm text-center">내역이 없습니다.</td>
                </tr>
              </tbody>
              <tbody v-else class="flex flex-col items-center w-full">
                <template v-for="(issue, index) in issueList">
                  <!-- <router-link :to="issue.status=='02'? 'issue/giveup/' +issue.id :'community/detail/' + issue.id"> -->
                    <router-link :to="issue.status == '01' ? url[Number(issue.status.substr(1,2))-1] : url[Number(issue.status.substr(1,2))-1] +issue.id">
                    <tr>
                      <td
                        class="py-5 text-sm bg-white border-b border-gray-200"
                      >
                        <div class="flex items-center">
                          <div class="ml-3">
                            <p class="text-gray-900 whitespace-nowrap">
                              {{ issue.createdAt.substr(0, 10) }}
                            </p>
                          </div>
                        </div>
                      </td>
                      <td
                        class="px-5 text-lg bg-white border-b border-gray-200"
                      >
                        <p
                          class="w-[300px] text-gray-900 line-clamp-1"
                        >
                          {{ issue.title }}
                        </p>
                      </td>
                      <td
                        class="px-5 py-5 border-b border-gray-200 w-[140px] bg-white text-lg"
                      >
                        <p class="text-gray-900 whitespace-nowrap">
                          {{ issue.amount != 0 ? issue.amount : "" }}
                        </p>
                      </td>
                      <td
                        class="flex justify-end px-5 py-5 text-lg bg-white border-b border-gray-200 whitespace-nowrap"
                      >
                        <!-- 진행중: 01 -->
                        <template v-if="issue.status == '01'">
                          <span
                            class="relative inline-block px-3 py-1 leading-tight text-white"
                          >
                            <span
                              class="absolute inset-0 rounded-full bg-main-red"
                            ></span>
                            <span class="relative">진행중</span>
                          </span>
                        </template>
                        <!-- 포기: 02 -->
                        <template v-else-if="issue.status == '02'">
                          <span
                            class="relative inline-block px-3 py-1 leading-tight text-white"
                          >
                            <span
                              aria-hidden
                              class="absolute inset-0 rounded-full bg-sub-red"
                            ></span>
                            <span class="relative px-3">포기</span>
                          </span>
                        </template>
                        <!-- 해결: 03 -->
                        <template v-else>
                          <span
                            class="relative inline-block px-3 py-1 leading-tight text-white"
                          >
                            <span
                              aria-hidden
                              class="absolute inset-0 rounded-full bg-main-grn"
                            ></span>
                            <span class="relative px-3">해결</span>
                          </span>
                        </template>
                      </td>
                    </tr>
                  </router-link>
                </template>
              </tbody>
            </table>
            <!-- <div
              class="flex flex-col items-center px-5 py-1 bg-white border-t xs:flex-row xs:justify-between"
            ></div> -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
// import { defineProps } from "vue";
import {ref} from "vue";

const url = ref(["issue", "issue/giveup/", "community/detail/"])
const p = defineProps({
  issueList: {
    type: Array,
  },
});
console.log("issueList", p.issueList);
</script>
<style></style>
