<script setup>
import { onMounted, defineProps } from 'vue';
import Chart from 'chart.js/auto';

const props = defineProps(['depositAmounts']);

onMounted(() => {
  const ctx = document.getElementById('lineChart');

  if (ctx) {
    const chartData = {
      labels: ['8월', '9월', '10월', '11월', '12월'],
      datasets: [{
        data: props.depositAmounts,
        fill: false,
        borderColor: 'rgb(191,17,49)',
        tension: 0.1
      }]
    };

    new Chart(ctx, {
      type: 'line',
      data: chartData,
    });
  }
});
</script>

<template>
  <canvas id="lineChart"></canvas>
</template>