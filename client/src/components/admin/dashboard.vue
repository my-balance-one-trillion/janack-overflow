<template>
	<h3 class="text-3xl text-main-grn font-bold mb-7">사이트 대시보드</h3>
	<div class="border-b-4 border-main-grn"></div>
	<p class="text-xl my-4">대시보드</p>
	<p class="text-[#919090]">사이트 전체의 정보 요약</p>

	<article class="flex justify-center gap-10 my-10">
		<div class="signInfo w-60 h-60">
			<h4 class="text-lg mb-5">가입정보</h4>
			<div class="flex flex-col justify-between gap-10 p-7 border border-gray-400 rounded-xl h-80">

				<div class="issuecnt">
					<p class="text-3xl text-main-grn">누적 가입 수</p>
					<p class="text-sm">총 <span>{{ allCount[0] }}</span>건 </p>
				</div>
				<div class="issuecnt">
					<p class="text-3xl text-main-grn">전체 작성글</p>
					<p class="text-sm">총 <span>{{ allCount[1] }}</span>개 </p>
				</div>
				<div class="issuecnt">
					<p class="text-3xl text-main-grn">전체 댓글 수</p>
					<p class="text-sm">총 <span>{{ allCount[2] }}</span>개 </p>
				</div>
			</div>
		</div>
		<div class="achievement w-60">
			<h4 class="text-lg mb-5">유저수 증가 추이</h4>
			<div class="p-5 border border-gray-400 rounded-xl h-80">
				<div class="achive-header mb-5">
					<h4 class="text-lg text-main-grn">누적대비 이번달 유저증가</h4>
					<p class="text-sm"><span> {{ CurrentUserTrend.userCount }}</span> / <span>{{ allCount[0] }}</span> </p>
				</div>
				<canvas id="user-trend"></canvas>
			</div>
		</div>
		<!-- <div class="average w-60">
			<h4 class="text-lg mb-5">월간 글쓴 기록</h4>
			<div class="p-5 border border-gray-400 rounded-xl h-80">
				<div class="achive-header mb-5">
				</div>
			</div>
		</div> -->

	</article>
</template>

<script setup>
import { useAuthStore } from '@/stores/auth';
import axios from 'axios';
import { onMounted, ref } from 'vue';
import Chart from 'chart.js/auto';

const allCount = ref([]);
const userTrend = ref([]);
const CurrentUserTrend = ref('');

// -----------------------------------
// 각종 수치
// -----------------------------------
onMounted(async () => {
	const [countResponse, userTrendResponse] = await Promise.all([
		axios.get('/admin/count', {
			headers: {
				"Authorization": useAuthStore().token
			}
		}),
		axios.get('/admin/signtrend', {
			headers: {
				"Authorization": useAuthStore().token
			}
		}),

	])
	allCount.value = countResponse.data;
	userTrend.value = userTrendResponse.data;
	CurrentUserTrend.value = userTrend.value[0]


	// -----------------------------------
	// 월간 차트
	// -----------------------------------
	new Chart(document.getElementById('user-trend'),
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
				labels: userTrend.value.map(row => row.month),
				datasets: [
					{
						label: '월별 게시글 현황',
						data: userTrend.value.map(row => row.userCount),
						borderRadius: 20,
						backgroundColor:
							'#025939',
					}
				]
			}
		}
	);
})


</script>

<style scoped></style>