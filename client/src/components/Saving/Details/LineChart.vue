<script setup>
import { onMounted } from 'vue';
import Chart from 'chart.js/auto';
import axios from "axios";
import {useAuthStore} from "../../../stores/auth";

const getMonthlyAmount = async () => {
  await axios
      .get('/savings/monthly-amount',{
        headers: {
          Authorization: useAuthStore().token,
        },
      })
      .then((response) => {
        const data = response.data;
        console.log(data);

        /*// 차트 데이터
        const chartData = {
          labels: data.map(item => item.month + '월'),
          datasets: [
            {
              data: data.map(item => item.count),
              backgroundColor: 'rgb(191,17,49)',
            },
          ],
        };

        // 차트 옵션
        const chartOptions = {
          indexAxis: 'y',
          responsive: true,
          scales: {
            x: {
              beginAtZero: true,
              // max: Math.max(...data.map(item => item.count)) + 1,
            },
          },
        };

        // 차트 생성
        const ctx = document.getElementById('barChart');
        if (ctx) {
          new Chart(ctx, {
            type: 'bar',
            data: chartData,
            options: chartOptions,
          });
        }*/
      })
      .catch((error) => {
        console.log(error);
      })
}

onMounted(() => {
  getMonthlyAmount();
});
</script>

<template>
  <canvas id="lineChart"></canvas>
</template>