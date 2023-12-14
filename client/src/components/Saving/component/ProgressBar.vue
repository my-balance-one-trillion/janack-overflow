<template>
  <div class="progress-bar flex">
    <div
        v-for="(step, index) in steps"
        :key="index"
        :class="{
        'bg-main-red': index < currentStep,
        'bg-gray-300': index >= currentStep
      }"
        class="flex h-3 my-5 mx-3 w-full rounded-2xl"
    ></div>
  </div>
</template>

<script>
import { computed } from "vue";

export default {
  props: {
    totalSteps: {
      type: Number,
      required: true
    },
    currentStep: {
      type: Number,
      required: true
    }
  },
  setup(props) {
    const steps = computed(() => Array(props.totalSteps).fill(null));

    return {
      steps
    };
  }
};
</script>

<style scoped>

</style>
