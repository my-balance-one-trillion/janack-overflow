<script setup>
import {ref} from "vue";
import DoughnutChart from "@/components/Saving/component/DoughnutChart.vue";

// 더보기
const showCount = ref(4);
</script>

<template>
  <div class="w-full">
    <!-- 나의 적금 -->
    <div class="my-10 text-3xl text-main-red">적금 기록</div>
    <div class="p-14 bg-gray-100 rounded-3xl shadow">
      <div class="grid grid-cols-2 gap-10">
        <DoughnutChart
            acnt-name="스프링 적금"
            anct-amount="3,440,000원"
            status="02"
            :progress-amount="1000"
            :goal-amount="10000"
        />
        <DoughnutChart
            acntName="스프링 적금"
            anctAmount="3,440,000원"
            status="03"
            :progressAmount="10000"
            :goalAmount="10000"
        />
        <DoughnutChart
            acntName="스프링 적금"
            anctAmount="3,440,000원"
            status="03"
            :progressAmount="10000"
            :goalAmount="10000"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>
