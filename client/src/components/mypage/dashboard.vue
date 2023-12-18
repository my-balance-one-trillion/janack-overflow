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
						<div class="achive-header mb-5">
							<h4 class="text-lg text-main-red">{{ amount.goalName }}</h4>
							<p class="text-sm">{{ amount.acntAmount }} <span> / </span> {{ amount.goalAmount }}</p>
						</div>
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
import { onMounted, ref } from 'vue';
import Chart from 'chart.js/auto';
import axios from 'axios';

const authStore = useAuthStore();
const myCount = ref({ issues: 0, comments: 0 });
const percent = ref('');
const amount = ref('');
const month = ref('');

// -----------------------------------
// 가입 유지일 구하기
// -----------------------------------

const currentDate = new Date();
const targetDate = new Date(authStore.userInfo.createdAt);
const timeDiff = Math.abs(currentDate.getTime() - targetDate.getTime());
const daysDiff = Math.ceil(timeDiff / (1000 * 3600 * 24));

onMounted(async () => {
	const [myCountResponse, percentResponse, monthResponse] = await Promise.all([
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
		axios.get('/savings/monthly-amount', {
			headers: {
				Authorization: authStore.token,
			},
		})
	]);
	amount.value = percentResponse.data.nowAccount;
	month.value = monthResponse.data
	myCount.value = myCountResponse.data;
	percent.value = ((amount.value.acntAmount / amount.value.goalAmount) * 100).toFixed(0);
	console.log(month.value);

	const goal = [percent.value, 100 - percent.value];

	const ctx = document.getElementById('acquisitions').getContext('2d');

	// -----------------------------------
	// 목표 차트
	// -----------------------------------

	Chart.register({
		id: 'doughnutlabel',
		beforeDraw: function (chart) {
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
		},
	});

	// 도넛 차트에 플러그인 적용하여 생성
	new Chart(ctx, {
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

	// -----------------------------------
	// 월간 차트
	// -----------------------------------
	new Chart(
		document.getElementById('acquisitions2'),
		{
			type: 'bar',
			options: {
				aspectRatio: 2 / 3,
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

<style scoped></style>
