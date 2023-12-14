<script setup>
import Header from "@/components/Header.vue";
import { onMounted } from 'vue'
    import { initFlowbite } from 'flowbite'
    // initialize components based on data attribute selectors
    onMounted(() => {
        initFlowbite();
    })
</script>

<template>
    <div class="overflow-hidden h-max page-wrapper">
        <Header></Header>
        <Router-View />
    </div>
</template>

<style scoped>
@import "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css";

.test {
    width: 100px;
    height: 100px;
    background-color: blue;
    margin: 0 auto;
}
</style>
