<template>
	<h3 class="text-3xl text-main-grn font-bold mb-7">전체 유저 관리</h3>
	<div class="border-b-4 border-main-grn"></div>
	<p class="text-xl my-4">유저 관리</p>
	<p class="text-[#919090]">유저 권한 강등, 차단 등</p>
	<article class="">
		<div class="w-full xl:w-10/12 m-12 xl:mb-0 px-4 mx-auto mt-2 flex flex-col items-center">
			<div class="relative flex flex-col min-w break-words bg-white w-full my-12 shadow-lg">
				<div class="block overflow-x-auto">
					<table class="min-w-full">
						<thead class="bg-white border-b">
							<tr>
								<th scope="col" class="text-m font-medium text-sub-red px-6 py-4 text-left w-5/12">
									유저이메일
								</th>
								<th scope="col" class="text-m font-medium text-sub-red px-6 py-4 text-left w-2/12">
									가입일
								</th>
								<th scope="col" class="text-sm font-medium text-sub-red px-6 py-4 text-left w-2/12">
									회원상태
								</th>
								<th scope="col" class="text-sm font-medium text-sub-red px-6 py-4 text-left w-3/12">
									상태변경
								</th>
							</tr>
						</thead>
						<tbody>
							<tr class="bg-white border-b" v-for="item in userList">

								<td class="text-sm text-gray-900 font-light px-6 py-4 whitespace-nowrap">
									{{ item.email }}
								</td>
								<td class="text-sm text-gray-900 font-light px-6 py-4 whitespace-nowrap">
									{{ item.createdAt }}

								</td>
								<td class="text-sm text-gray-900 font-light px-6 py-4 whitespace-nowrap">
									<span v-if="item.status == '01'" class="bg-main-grn text-white rounded-lg p-1">정상</span>
									<span v-if="item.status == '02'" class="bg-main-red text-white rounded-lg p-1">탈퇴</span>
									<span v-if="item.status == '03'" class="bg-yellow-300 text-white rounded-lg p-1">정지</span>
								</td>
								<td class="text-sm text-gray-900 font-light px-6 py-4 whitespace-nowrap">
									<select :id="'userStatus_' + item.id" v-model="item.status"
										@change="userStatusUpdate(item, pageSet.page)"
										class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
										<option value="01">정상</option>
										<option value="03">정지</option>
										<option value="02">탈퇴</option>
									</select>
								</td>
							</tr>

						</tbody>
					</table>
				</div>
			</div>
			<nav aria-label="Page navigation example">
				<ul class="inline-flex -space-x-px text-sm">
					<li>
						<div
							class="flex items-center justify-center px-3 h-8 ms-0 leading-tight text-gray-500 bg-white border border-e-0 border-gray-300 rounded-s-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white cursor-pointer"
							@click="pageForward(pageSet.page)">
							Previous</div>
					</li>
					<li v-for="i in pageInt">
						<div
							class="flex items-center justify-center px-3 h-8 leading-tight text-main-grn bg-green-200 border border-gray-300 hover:bg-green-200 hover:text-hover-grn dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
							v-if="pageSet.page == i + 1">
							{{ i + 1 }}</div>
						<div
							class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white cursor-pointer"
							@click="getUserList(i)" v-else>
							{{ i + 1 }}</div>
					</li>

					<li>
						<div
							class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 rounded-e-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white cursor-pointer"
							@click="pageNext(pageSet.page)">
							Next</div>
					</li>
				</ul>
			</nav>
		</div>
	</article>
</template>

<script setup>
import { reactive, ref } from 'vue';
import axios from 'axios';
import { useAuthStore } from "@/stores/auth";

const userList = ref([]);
const pageSet = ref([]);
let pageInt = reactive([]);

// -------------------------------------
// 유저 리스트 출력
// -------------------------------------

getUserList(0);
async function getUserList(i) {
	const response = await axios.get(`/admin/users?page=${i}&size=5`, {
		headers: {
			"Authorization": useAuthStore().token
		}
	});
	const { data, pageDTO, pageNumber } = response.data;
	userList.value = data;
	pageSet.value = pageDTO;
	pageInt = pageNumber;
}

// -------------------------------------
// 유저 권한 수정
// -------------------------------------

async function userStatusUpdate(users, page) {
	const statusUpdate = {
		status: users.status
	}
	try {
		await axios.put(`/admin/users/${users.id}`, statusUpdate, {
			headers: {
				"Authorization": useAuthStore().token
			}
		});
		getUserList(page - 1);
	} catch (error) {
		console.log(error);
	}
}

function pageForward(i) {

	let warpForward = i - 6;
	if (warpForward <= 0) {
		getUserList(0);
	} else {
		getUserList(warpForward);

	}
}

function pageNext(i) {
	let warpNext = i + 4;
	if (warpNext > pageSet.value.totalPages - 1) {
		getUserList(pageSet.value.totalPages - 1);
	} else {
		getUserList(warpNext);
	}
}

</script>

<style scoped></style>