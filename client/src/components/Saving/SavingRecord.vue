<script setup>
import {onMounted, ref} from "vue";
import DoughnutChart from "@/components/Saving/component/DoughnutChart.vue";
import axios from "axios";
import {useAuthStore} from "../../stores/auth";
import dayjs from "dayjs";

const accountList = ref([]);

onMounted(async () => {
  try {
    const response = await axios.get('/savings', {
      headers: {
        Authorization: useAuthStore().token,
      }
    });
    accountList.value = response.data;
    console.log(response.data);
  } catch (error) {
    console.error(error);
  }
});

// 더보기
const showCount = ref(4);

</script>

<template>
  <div class="w-full">
    <!-- 나의 적금 -->
    <div class="my-10 text-3xl text-main-red">적금 기록</div>
    <div class="p-14 bg-gray-100 rounded-3xl shadow">
      <div class="grid grid-cols-2 gap-10">
        <doughnut-chart v-for="(account, index) in accountList" :key="index"
            :goal-amount="account.goalAmount"
            :progress-amount="account.acntAmount"
            :acnt-name="account.acntName"
            :anct-amount="Number(account.acntAmount).toLocaleString()+'원'"
            :status="account.status"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>
