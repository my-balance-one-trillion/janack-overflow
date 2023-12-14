<script setup>
import { onMounted, defineProps } from 'vue';
import Chart from 'chart.js/auto';

const props = defineProps(['depositCounts']);

onMounted(() => {
  const ctx = document.getElementById('barChart');

  if (ctx) {
    const chartData = {
      labels: ['8월', '9월', '10월', '11월', '12월'],
      datasets: [
        {
          data: props.depositCounts,
          backgroundColor: 'rgb(191,17,49)',
        },
      ],
    };

    const chartOptions = {
      indexAxis: 'y',
      responsive: true,
      scales: {
        x: {
          beginAtZero: true,
          // max: Math.max(...props.depositCounts) + 1,
        },
      },
    };

    new Chart(ctx, {
      type: 'bar',
      data: chartData,
      options: chartOptions,
    });
  }
});
</script>

<template>
  <canvas id="barChart"></canvas>
</template>