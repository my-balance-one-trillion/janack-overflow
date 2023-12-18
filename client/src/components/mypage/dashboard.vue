<template>
  <div>
    <h3 class="text-3xl text-main-red font-bold mb-7">대시보드</h3>
    <div class="border-b-4 border-main-red"></div>
    <p class="text-xl my-4">대시보드</p>
    <p class="text-[#919090]">활동을 한눈에 확인할 수 있습니다</p>
    <article class="flex flex-col justify-center items-center">
      <div class="dash-wrap flex justify-between gap-10 my-11">
        <div class="signInfo w-60">
          <h4>가입정보</h4>
          <div class="flex flex-col justify-between gap-10 p-5 border border-gray-400 rounded-xl">
            <div class="dayinfo">
              <p class="text-3xl text-sub-red">가입일시</p>
              <p class="text-sm">{{ authStore.userInfo.createdAt }} <span class="">( {{ daysDiff }}일차 )</span></p>
            </div>
            <div class="issuecnt">
              <p class="text-3xl text-sub-red">작성글 수</p>
              <p class="text-sm">총 <span>{{ myCount[0] }}</span>개 </p>
            </div>
            <div class="issuecnt">
              <p class="text-3xl text-sub-red">작성댓글 수</p>
              <p class="text-sm">총 <span>{{ myCount[1] }}</span>개 </p>
            </div>
          </div>
        </div>
        <div class="achievement w-60">
          <h4>목표 달성률</h4>
          <div class="p-5 border border-gray-400 rounded-xl">
            <canvas id="acquisitions"></canvas>
          </div>
        </div>
        <div class="average w-60">
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
import { useAuthStore } from '@/stores/auth';
import { onMounted, reactive, ref } from 'vue';
import Chart from 'chart.js/auto';
import axios from 'axios';

const authStore = useAuthStore();
let myCount = reactive([]);
const percent = ref('');

// -----------------------------------
// 가입 유지일 구하기
// -----------------------------------

const currentDate = new Date();
const targetDate = new Date(authStore.userInfo.createdAt);
const timeDiff = Math.abs(currentDate.getTime() - targetDate.getTime());
const daysDiff = Math.ceil(timeDiff / (1000 * 3600 * 24));

onMounted(async () => {
  const [myCountResponse, percentResponse] = await Promise.all([
    axios.get('/mypage/mycount', {
      headers: {
        authorization: authStore.token,
      }
    }),
    axios.get('/main/login', {
      headers: {
        authorization: authStore.token,
      },
    }),
  ]);

  myCount = myCountResponse.data;
  percent.value = ((percentResponse.data.nowAccount.acntAmount / percentResponse.data.nowAccount.goalAmount) * 100).toFixed(0);

  const goal = [percent.value, 100 - percent.value]

  const ctx = document.getElementById('acquisitions').getContext('2d');

  // 도넛 차트 그리기
  new Chart(ctx, {
    type: 'doughnut',
    data: {
      datasets: [
        {
          data: goal,
          backgroundColor: [
            '#BF1131',
            'rgba(54, 162, 235, 0.1)',
          ],
        }
      ]
    },
    options: {
      cutout: '60%',
      aspectRatio: 2 / 3,
      plugins: {
        legend: false, // 범례 숨기기
      },
    }
  });

  // 가운데에 텍스트 추가
  ctx.textAlign = 'center';
  ctx.textBaseline = 'middle';
  ctx.fillStyle = '#000'; // 텍스트 색상
  ctx.font = '20px Arial'; // 텍스트 폰트 및 크기
  ctx.fillText(`${percent.value}%`, ctx.canvas.width / 2, ctx.canvas.height / 2);
  console.log(percent.value);
});
</script>

<style scoped></style>
