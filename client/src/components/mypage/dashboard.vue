<template>
	<div>
		<h3 class="text-3xl text-main-red font-bold mb-7">대시보드</h3>
		<div class="border-b-4 border-main-red"></div>
		<p class="text-xl my-4">대시보드</p>
		<p class="text-[#919090]">활동을 한눈에 확인할 수 있습니다</p>
		<article class="flex justify-center gap-10 my-10">
			<div class="signInfo w-60 h-60">
				<h4 class="text-lg mb-5">가입정보</h4>
				<div class="flex flex-col justify-between gap-10 p-7 border border-gray-400 rounded-xl h-80">
					<div class="dayinfo">
						<p class="text-3xl text-main-red">가입일시</p>
						<p class="text-sm">{{ authStore.userInfo.createdAt }} <span class="">( {{ daysDiff }}일차 )</span>
						</p>
					</div>
					<div class="issuecnt">
						<p class="text-3xl text-main-red">작성글 수</p>
						<p class="text-sm">총 <span>{{ myCount[0] }}</span>개 </p>
					</div>
					<div class="issuecnt">
						<p class="text-3xl text-main-red">작성댓글 수</p>
						<p class="text-sm">총 <span>{{ myCount[1] }}</span>개 </p>
					</div>
				</div>
			</div>
			<div class="achievement w-60" v-if="achive != undefined">
				<h4 class="text-lg mb-5">목표 달성률</h4>
				<div class="p-5 border border-gray-400 rounded-xl h-80">
					<div class="achive-header mb-5">
						<h4 class="text-lg text-main-red">{{ achive.goalName }}</h4>
						<p class="text-sm">{{ achive.acntAmount }} <span> / </span> {{ achive.goalAmount }}</p>
					</div>
					<canvas id="achievement"></canvas>
				</div>
			</div>
			<div class="average w-60">
				<h4 class="text-lg mb-5">월간 글쓴 기록</h4>
				<div class="p-5 border border-gray-400 rounded-xl h-80">
					<div class="achive-header mb-5">
						<h4 class="text-lg text-main-red"> {{ currentMonth.month }}월 작성건</h4>
						<p class="text-sm"> {{ currentMonth.count }} 건</p>
					</div>
					<canvas id="average"></canvas>
				</div>
			</div>

		</article>
	</div>
</template>

<script setup>
import { useAuthStore } from '@/stores/auth';
import { onMounted, ref } from 'vue';
import Chart from 'chart.js/auto';
import axios from 'axios';

const authStore = useAuthStore();
const myCount = ref({ issues: 0, comments: 0 });
const percent = ref('');
const achive = ref('');
const month = ref([]);
const currentMonth = ref('');

// -----------------------------------
// 가입 유지일 구하기
// -----------------------------------

const currentDate = new Date();
const targetDate = new Date(authStore.userInfo.createdAt);
const timeDiff = Math.abs(currentDate.getTime() - targetDate.getTime());
const daysDiff = Math.ceil(timeDiff / (1000 * 3600 * 24));

onMounted(async () => {
	try{
		const [myCountResponse, achiveResponse, monthResponse] = await Promise.all([
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
			axios.get('/savings/monthly-count', {
				headers: {
					Authorization: authStore.token,
				},
			})
		]);
		myCount.value = myCountResponse.data;
		achive.value = achiveResponse.data.nowAccount;
		month.value = monthResponse.data
		currentMonth.value = monthResponse.data[0];
	}catch(error){
		alert("대시보드 생성중 오류가 발생했습니다.");
    router.push('/');
	}

	const goalChartCtx = document.getElementById('achievement').getContext('2d');
	const monthChartCtx = document.getElementById('average');


	// -----------------------------------
	// 목표 차트
	// -----------------------------------
	if (achive.value) {
		percent.value = ((achive.value.acntAmount / achive.value.goalAmount) * 100).toFixed(0);
		const goal = [percent.value, 100 - percent.value];

		Chart.register({
			id: 'doughnutlabel',
			beforeDraw: function (chart) {
				if (chart.canvas.id === 'achievement') {
					const width = chart.width;
					const height = chart.height;
					const ctx = chart.ctx;

					ctx.restore();
					const fontSize = (height / 114).toFixed(2);
					ctx.font = `${fontSize}em 'D2Coding', sans-serif`;
					ctx.textBaseline = 'middle';

					const text = `${percent.value}%`;
					const textX = Math.round((width - ctx.measureText(text).width) / 2);
					const textY = height / 2;

					ctx.fillStyle = '#BF1131';
					ctx.fillText(text, textX, textY);
					ctx.save();
				}
			},
		});

		// 도넛 차트에 플러그인 적용하여 생성
		new Chart(goalChartCtx, {
			type: 'doughnut',
			data: {
				datasets: [
					{
						data: goal,
						backgroundColor: [
							'#BF1131',
							'#eeeeee',
						],
					}
				]
			},
			options: {
				cutout: '50%',
				aspectRatio: 1 / 1,
				plugins: {
					doughnutlabel: {}, // 플러그인 적용
				},
			}
		});
	}
	// -----------------------------------
	// 월간 차트
	// -----------------------------------

	new Chart(monthChartCtx,
		{
			type: 'bar',
			options: {
				aspectRatio: 1 / 1,
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
				},
				plugins: {} // 플러그인 비활성화
			},
			data: {
				labels: month.value.map(row => row.month),
				datasets: [
					{
						label: '월별 게시글 현황',
						data: month.value.map(row => row.count),
						borderRadius: 20,
						backgroundColor:
							'#BF1131',
					}
				]
			}
		}
	);


});
</script>

<style scoped></style>
