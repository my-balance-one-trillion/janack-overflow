<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import {useAuthStore} from "@/stores/auth";
import SubmitIssue from "@/components/Issue/SubmitIssue.vue";
import AboutIssue from "@/components/Issue/AboutIssue.vue";
import Solving from "../components/Issue/Solving.vue";
import SubmitSolution from "../components/Issue/SubmitSolution.vue";
import IssueModal from "../components/Issue/IssueModal.vue";

const activeTab = ref(0);
const step = ref(4);
const modal = ref(false);
const tabs = ref([
  "에러",
  "해결",
]);

onMounted(async () => {
  await getIssue();
});

async function getIssue() {
  await axios
      .get('/issues', {
        headers: {
          Authorization: useAuthStore().token,
        }
      })
      .then((response) => {
        step.value=2;
      })
      .catch((error) => {
        if(error.response.status!==200){
          step.value=1;
        }
      })
  console.log('modal: '+modal.value);

}

const stepChanged = (newStep) => {
  step.value = newStep;
  console.log('modal: '+modal.value);

};
const stepAndTabChanged = (newStep, newTab) => {
  step.value = newStep;
  activeTab.value = newTab;
  console.log('modal: '+modal.value);

}
const showModal = (modalState) => {
  modal.value = modalState;
  console.log('modal: '+modal.value);
}
</script>
<template>
  <div class="flex flex-col w-10/12 mx-auto">
    <!-- 에러 / 해결 제목박스 -->
    <div v-if="step !== 3" class="flex items-center justify-center w-full p-2 mt-10 text-3xl text-center rounded-xl bg-bg-grey">
      <div class="w-full py-2 mx-1 border-0 cursor-pointer rounded-xl bg-main-red text-white font-bold border-b-4 border-white">에러</div>
    </div>

    <div v-if="step === 3" class="flex items-center justify-center w-full p-2 mt-10 text-3xl text-center rounded-xl bg-bg-grey">
      <div
          :class="activeTab === 0 ? 'bg-main-red text-white font-bold border-b-4 border-white' : 'font-bold text-white hover:bg-hover-red hover:ease-in hover:transition hover:duration-150'"
          class="w-3/6 py-2 mx-1 border-0 cursor-pointer rounded-xl" @click="activeTab = 0">
        {{ tabs[0] }}
      </div>
      <div
          :class="activeTab === 1 ? 'bg-main-grn text-white font-bold border-b-4 border-white' : 'font-bold text-white hover:bg-hover-grn hover:ease-in hover:transition hover:duration-150'"
          class="w-3/6 py-2 mx-1 border-0 cursor-pointer rounded-xl" @click="activeTab = 1">
        {{ tabs[1] }}
      </div>
    </div>

    <div v-show="activeTab === 0" class="w-full">
      <submit-issue v-if="step === 1" @step-changed="stepChanged"></submit-issue>
      <about-issue v-else-if="step === 2 || step === 3" @step-changed="stepChanged"></about-issue>
      <solving v-if="step===2" @step-changed="stepAndTabChanged"></solving>
    </div>

    <div v-show="activeTab === 1" class="w-full">
      <submit-solution v-if="step === 3" @show-modal="showModal"></submit-solution>
    </div>

    <issue-modal v-if="modal"></issue-modal>
  </div>

</template>

<style scoped>
</style>