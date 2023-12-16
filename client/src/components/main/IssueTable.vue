<template>
<!-- component -->
<body class="antialiased font-main">
    <div class="px-4 mx-auto sm:px-8">
        <div class="py-8">
            <div class="px-4 py-4 -mx-4 sm:-mx-8 sm:px-8">
                <div class="inline-block min-w-full shadow rounded-lg overflow-y-scroll h-[500px]" >
                    <table class="min-w-full leading-normal">
                        <tbody>
                            
                            <tr v-for="(issue, index) in issueList">
                                <td class="px-5 py-5 text-lg bg-white border-b border-gray-200">
                                    <div class="flex items-center">
                                        <div class="ml-3">
                                            <p class="text-gray-900 whitespace-nowrap">
                                                {{ issue.createdAt }}
                                            </p>
                                        </div>
                                    </div>
                                </td>
                                <td class="px-5 py-5 text-2xl bg-white border-b border-gray-200 ">
                                    <p class="w-[300px] text-gray-900 whitespace-nowrap overflow-hidden">{{ issue.title }}</p>
                                </td>
                                <td class="px-5 py-5 text-2xl bg-white border-b border-gray-200">
                                    <p class="text-gray-900 whitespace-nowrap">
                                        {{ issue.amount!=0 ?  issue.amount : "" }}
                                    </p>
                                </td>
                                <td class="flex justify-center px-5 py-5 text-2xl bg-white border-b border-gray-200">
                                    <!-- 진행중: 01 -->
                                    <template v-if="issue.status == '01'">
                                    <span
                                        class="relative inline-block px-3 py-1 leading-tight text-white">
                                        <span aria-hidden
                                            class="absolute inset-0 rounded-full bg-main-red"></span>
                                        <span class="relative">진행중</span>
                                    </span>
                                    </template>
                                    <!-- 포기: 02 -->
                                    <template v-else-if="issue.status == '02'">
                                    <span
                                        class="relative inline-block px-3 py-1 leading-tight text-white">
                                        <span aria-hidden
                                            class="absolute inset-0 rounded-full bg-sub-grn"></span>
                                        <span class="relative px-3">포기</span>
                                    </span>
                                    </template>
                                    <!-- 해결: 03 -->
                                    <template v-else>
                                    <span
                                        class="relative inline-block px-3 py-1 leading-tight text-white">
                                        <span aria-hidden
                                            class="absolute inset-0 rounded-full bg-main-grn"></span>
                                        <span class="relative px-3">해결</span>
                                    </span>
                                    </template>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div
                        class="flex flex-col items-center px-5 py-1 bg-white border-t xs:flex-row xs:justify-between">
                        
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</template>
<script setup>
// import { defineProps } from "vue";

const p = defineProps({
    issueList:{
        type: Array,
    }
})
console.log("issueList", p.issueList);
</script>
<style>

</style>