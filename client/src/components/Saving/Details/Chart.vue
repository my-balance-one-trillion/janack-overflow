<script setup>
import { onMounted } from 'vue';
import Chart from 'chart.js/auto';
import axios from "axios";
import { useAuthStore } from "../../../stores/auth";

const getData = async () => {
  try {
    const countResponse = await axios.get('/savings/monthly-count', {
      headers: {
        Authorization: useAuthStore().token,
      },
    });

    const amountResponse = await axios.get('/savings/monthly-amount', {
      headers: {
        Authorization: useAuthStore().token,
      },
    });

    const countData = countResponse.data.reverse();  // 월별 적금 횟수
    const amountData = amountResponse.data;  // 월별 적금 금액

    // amountData의 각 월에 대한 데이터를 확인하고,
    // countMap에서 해당 월의 count 값을 가져와 데이터를 생성
    // 만약 해당 월에 대한 amount 데이터가 없을 경우에는 amount = 0으로 설정

    // 월별 데이터 매핑
    const amountMap = amountData.reduce((map, item) => {
      map[item.month] = item.amount;
      return map;
    }, {});


    // 차트 데이터
    const chartData = {
      labels: countData.map(item => item.month + '월'),
      datasets: [
        {
          label: '월별 적금 횟수',
          type: 'bar',
          data: countData.map(item => item.count),
          borderColor: '#f53e52',
          backgroundColor: 'rgba(254,163,170,0.4)',
          hoverBackgroundColor: 'rgba(254,163,170,0.8)',
          borderWidth: 1,
          yAxisID: 'y1',
          barThickness: 'flex',
          maxBarThickness: 80,
        },
        {
          label: '월별 적금 금액',
          type: 'line',
          data: countData.map(item => amountMap[item.month] || 0),
          borderColor: 'rgb(191, 17, 49)',
          yAxisID: 'y2',
          tension: 0.1,
        },
      ],
    };

    // 차트 옵션
    const chartOptions = {
      indexAxis: 'x',
      responsive: true,
      scales: {
        y1: {
          type: 'linear',
          display: true,
          position: 'left',
          beginAtZero: true,
          suggestedMin: 0,
          ticks: { // y축 단위 설정
            // stepSize: 1,
            fontSize: 18,
          },
        },
        y2: {
          type: 'linear',
          display: true,
          position: 'right',
          beginAtZero: true,
          suggestedMin: 0,
          ticks: { // y축 단위 설정
            fontSize: 18,
            callback: function(value, index, values) {
              Number(value).toLocaleString();
              return value + '원';
            }
          },
          grid: {
            color: 'transparent',
          },
        },
      },
      plugins: {
        legend: {
          labels: {
            font: {
              size: 18,
            }
          }
        }
      }
    };

    // 차트 생성
    const ctx = document.getElementById('mixedChart');
    if (ctx) {
      new Chart(ctx, {
        type: 'bar',
        data: chartData,
        options: chartOptions,
      });
    }
  } catch (error) {
    console.log(error);
  }
};

onMounted(() => {
  getData();
});
</script>

<template>
  <canvas id="mixedChart"></canvas>
</template>