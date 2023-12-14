<template>
  <div>
    <h3 class="text-3xl text-main-red font-bold mb-7">대시보드</h3>
    <div class="border-b-4 border-main-red"></div>
    <p class="text-xl my-4">대시보드</p>
    <p class="text-[#919090]">활동을 한눈에 확인할 수 있습니다</p>
    <article class="mx-20 my-20">
      <div class="dash-wrap flex gap-10">
        <div class="achievement w-1/4">
          <h4>목표 달성률</h4>
          <div class=" p-5 border border-gray-400 rounded-xl">
            <canvas id="acquisitions"></canvas>
          </div>
        </div>
        <div class="average w-1/4">
          <h4>월간 글쓴 기록</h4>
          <div class="p-5 border border-gray-400 rounded-xl">
            <canvas id="acquisitions2"></canvas>
          </div>
        </div>
        


      </div>

    </article>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import Chart from 'chart.js/auto';

onMounted(() => {
  const data = [
    { month: 7, count: 80 },
    { month: 8, count: 20 },
  ];
  const data2 = [
    { month: 7, count: 33 },
    { month: 8, count: 4 },
    { month: 9, count: 5 },
    { month: 10, count: 67 },
    { month: 11, count: 21 },
    { month: 12, count: 7 },
  ];

  new Chart(
    document.getElementById('acquisitions'),
    {
      type: 'doughnut',
      options: {
        aspectRatio: 2/3,
        cutout: '80%',
        elements: {
          arc: {
            borderRadius: 20,
          },
        },
      },
      data: {
        labels: data.map(row => row.month),
        datasets: [
          {
            label: '월별 게시글 현황',
            data: data.map(row => row.count),
            backgroundColor: [
              'rgba(255, 99, 132, 0.8)',
              'rgba(54, 162, 235, 0.0)',
            ],
          }
        ]
      }
    }
  );
  new Chart(
    document.getElementById('acquisitions2'),
    {
      type: 'bar',
      options: {
        aspectRatio: 2/3,
        elements: {
          rectangle: {
            borderRadius: 20,
          },
        },
        scales: {
          x: {
            grid: {
              display: false // x 축의 배경 그리드를 숨김
            }
          },
          y: {
            grid: {
              display: false // y 축의 배경 그리드를 숨김
            }
          }
        }  
      },
      data: {
        labels: data2.map(row => row.month),
        datasets: [
          {
            label: '월별 게시글 현황',
            data: data2.map(row => row.count),
        
            borderRadius: 20,

          }
        ]
      }
    }
  );
});
</script>

<style scoped>

</style>
