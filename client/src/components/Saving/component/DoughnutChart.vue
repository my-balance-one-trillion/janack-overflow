<script setup>
import { onMounted, defineProps } from 'vue';
import Chart from 'chart.js/auto';

const props = defineProps(['acntName', 'anctAmount', 'status', 'progressAmount', 'goalAmount']);
console.log(props);

const status = props.status;
const progressRate = Math.ceil(props.progressAmount / props.goalAmount * 100);

const canvasId = `doughnut-${Math.ceil(Math.random() * 1000)}`;

onMounted(() => {
  const data = progressRate >= 100
      ? [{ rate: progressRate }]
      : [{ rate: progressRate }, { rate: 100 - progressRate }];

  const backgroundColor =
      progressRate >= 100 ?
          'rgb(2, 89, 57)' : status === '01'
              ? ['rgb(191,177,17)', 'rgb(240, 242, 242)'] : ['rgb(191, 17, 49)', 'rgb(240, 242, 242)'];

  const chartData = {
    datasets: [
      {
        data: data.map(row => row.rate),
        backgroundColor,
      },
    ],
  };

  const ctx = document.getElementById(canvasId);

  if (ctx) {
    const chart = new Chart(ctx, {
      type: 'doughnut',
      data: chartData,
      options: {
        cutoutPercentage: '50%',
        animation: {
          animateRotate: false,
        },
        elements: {
          arc: {
            borderRadius: 0,
          },
        },
        plugins:{
          doughnutLabel : [
            {
              text: `${progressRate}%`,
            },
          ],
        },
      },
    });

    /*chart.options.plugins.afterDraw = (chart) => {
      const ctx = chart.ctx;
      const canvasCenterX = chart.canvas.width / 2;
      const canvasCenterY = chart.canvas.height / 2;

      ctx.fillStyle = 'black';
      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      ctx.font = '24px bold Arial';

      ctx.fillText(`${progressRate}%`, canvasCenterX, canvasCenterY);
    };*/

  }
});
</script>

<template>
  <div class="w-auto">
    <div class="p-5 border bg-white rounded-xl flex flex-col items-center">
      <div class="w-full p-5 flex items-center justify-between gap-7">
        <div>
          <div class="text-2xl font-bold">{{ acntName }}</div>
          <div class="text-xl">{{ anctAmount }}</div>
        </div>
        <div v-if="status === '01'" class="px-6 py-2 bg-yellow-100 rounded-2xl text-yellow-700 text-2xl font-bold shadow justify-center inline-flex">진행중</div>
        <div v-if="status === '02'" class="px-6 py-2 bg-red-100 rounded-2xl text-main-red text-2xl font-bold shadow justify-center inline-flex">포기</div>
        <div v-if="status === '03'" class="px-6 py-2 bg-green-100 rounded-2xl text-main-grn text-2xl font-bold shadow justify-center inline-flex">완료</div>
      </div>
      <div class="w-1/2 relative">
        <canvas :id="canvasId"></canvas>
        <div v-if="status === '01'" class="absolute pt-2 top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-xl text-yellow-700 font-bold">{{ progressRate }}
          <span class="text-xs text-black">%</span>
        </div>
        <div v-if="status === '02'" class="absolute pt-2 top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-xl text-main-red font-bold">{{ progressRate }}
          <span class="text-xs text-black">%</span>
        </div>
        <div v-if="status === '03'" class="absolute pt-2 top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-xl text-main-grn font-bold">{{ progressRate }}
          <span class="text-xs text-black">%</span>
        </div>
      </div>
    </div>
  </div>
</template>
