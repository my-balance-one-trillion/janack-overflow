<template>
	<main class="my-10 flex items-start" v-if="authStore.token">
		<aside class="flex flex-row">
			<div class="flex flex-col w-56 bg-white rounded-3xl overflow-hidden border border-sub-grn">
				<div class="mt-6 mx-auto">
					<img id="profileimg" :src="`/images/${authStore.userInfo.profileImage}`" class="rounded-full w-32"
						alt="profile picture" />
				</div>

				<div class="mt-5 mx-auto">
					<h2 id="profilenick" class="text-lg tracking-wide">
						{{ authStore.userInfo.nickname }}
					</h2>
				</div>
				<div class="border-b-4 border-main-grn w-40 mx-auto my-4"></div>

				<div class="mypage-title inline-flex items-center justify-center">
					<span class="text-2xl bold">관리자 페이지</span>
				</div>
				<ul class="flex flex-col py-4">
					<li>
						<div class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-xl hover:text-black-800 cursor-pointer"
							@click="changeComponent('Dashboard')">
							<span class="inline-flex items-center justify-center h-12 w-9 text-lg text-gray-400"><i
									class="bx bx-home"></i></span>
							<span class="text-lg font-medium">대시보드</span>
						</div>
					</li>
					<li>
						<div class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-lg hover:text-gray-800 cursor-pointer"
							@click="changeComponent('AllUsers')">
							<span class="inline-flex items-center justify-center h-12 w-9 text-lg"><i
									class="bx bx-drink"></i></span>
							<span class="text-lg font-medium">전체 유저 목록</span>
						</div>
					</li>
					<li>
						<div class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-xl hover:text-gray-800 cursor-pointer"
							@click="changeComponent('AllIssue')">
							<span class="inline-flex items-center justify-center h-12 w-9 text-lg"><i
									class="bx bx-shopping-bag"></i></span>
							<span class="text-lg font-medium">전체 게시글 목록</span>
						</div>
					</li>

				</ul>
			</div>
		</aside>
		<section class="ml-10 w-full">
			<dashboard v-if="currentComponent === 'Dashboard'" />
			<AllUsers v-if="currentComponent === 'AllUsers'" />
			<AllIssue v-if="currentComponent === 'AllIssue'" />
		</section>
	</main>
	<main class="flex flex-col justify-center h-screen" v-else>
		<div class="flex flex-col justify-center items-center">
			<p>잘못된 접근입니다!</p>
			<p>
				<router-link to="/" class="cursor-pointer text-main-grn underline">첫 화면으로</router-link>
			</p>
		</div>
	</main>
</template>

<script setup>
import { ref } from 'vue';
import { useAuthStore } from '@/stores/auth';
import Dashboard from '@/components/admin/Dashboard.vue';
import AllUsers from '@/components/admin/AllUsers.vue';
import AllIssue from '@/components/admin/AllIssue.vue';

const authStore = useAuthStore();
const currentComponent = ref("Dashboard");

const changeComponent = (components) => {
	currentComponent.value = components;
}

</script>

<style scoped></style>
