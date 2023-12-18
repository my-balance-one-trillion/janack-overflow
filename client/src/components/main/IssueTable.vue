<template>
  <!-- component -->
  <body class="antialiased font-main">
    <div class="mx-auto px-4 sm:px-8">
      <div class="py-8">
        <div class="mx-4 sm:mx-8 px-4 sm:px-8 py-4">
          <div
            class="inline-block min-w-fit overflow-x-auto shadow rounded-lg overflow-y-scroll h-[500px]"
          >
            <table class="leading-normal">
              <tbody>
                <template v-for="(issue, index) in issueList">
                    <router-link :to="'community/detail/'+issue.id">
                    <tr>
                      <td
                        class="px-5 py-5 border-b border-gray-200 bg-white text-lg"
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
                        class="px-5 py-5 border-b border-gray-200 bg-white text-2xl"
                      >
                        <p
                          class="w-[300px] text-gray-900 whitespace-nowrap overflow-hidden"
                        >
                          {{ issue.title }}
                        </p>
                      </td>
                      <td
                        class="px-5 py-5 border-b min-w-[100px] border-gray-200 bg-white text-2xl"
                      >
                        <p class="text-gray-900 whitespace-nowrap">
                          {{ issue.amount != 0 ? issue.amount : "" }}
                        </p>
                      </td>
                      <td
                        class="flex px-5 py-5 border-b border-gray-200 bg-white text-2xl justify-end whitespace-nowrap"
                      >
                        <!-- 진행중: 01 -->
                        <template v-if="issue.status == '01'">
                          <span
                            class="relative inline-block px-3 py-1 text-white leading-tight"
                          >
                            <span
                              class="absolute inset-0 bg-main-red rounded-full"
                            ></span>
                            <span class="relative">진행중</span>
                          </span>
                        </template>
                        <!-- 포기: 02 -->
                        <template v-else-if="issue.status == '02'">
                          <span
                            class="relative inline-block px-3 py-1 text-white leading-tight"
                          >
                            <span
                              aria-hidden
                              class="absolute inset-0 bg-sub-grn rounded-full"
                            ></span>
                            <span class="relative px-3">포기</span>
                          </span>
                        </template>
                        <!-- 해결: 03 -->
                        <template v-else>
                          <span
                            class="relative inline-block px-3 py-1 text-white leading-tight"
                          >
                            <span
                              aria-hidden
                              class="absolute inset-0 bg-main-grn rounded-full"
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
              class="px-5 py-1 bg-white border-t flex flex-col xs:flex-row items-center xs:justify-between"
            ></div> -->
          </div>
        </div>
      </div>
    </div>
  </body>
</template>
<script setup>
import { defineProps } from "vue";

const p = defineProps({
  issueList: {
    type: Array,
  },
});
console.log("issueList", p.issueList);
</script>
<style></style>
