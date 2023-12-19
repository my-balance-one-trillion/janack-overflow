<template>
    <Doughnut :data="data" :options="options" />
   
</template>


<script setup>
import { onMounted, ref } from 'vue';
import { defineProps } from "vue";
import { Chart as ChartJS, ArcElement, Tooltip, Legend} from 'chart.js'
import { Doughnut } from 'vue-chartjs'

const p  = defineProps({
  goalAmount:{
    type: Number,
  },
  acntAmount:{
    type: Number,
  }
})

console.log("차트", p.goalAmount, p.acntAmount);
const percent = ((p.acntAmount / p.goalAmount) * 100).toFixed(0);
console.log("percent", percent);

ChartJS.register(ArcElement, Legend);
// ChartJS.register({
//     id: 'doughnutlabel',
//     beforeDraw: function (chart) {
//       const width = chart.width;
//       const height = chart.height;
//       const ctx = chart.ctx;

//       ctx.restore();
//       const fontSize = (height / 114).toFixed(2);
//       ctx.font = `${fontSize}em 'D2Coding', sans-serif`;
//       ctx.textBaseline = 'middle';

//       const text = `${percent}%`;
//       const textX = Math.round((width - ctx.measureText(text).width) / 2);
//       const textY = height / 2;

//       ctx.fillStyle = '#025939'; // Set the color of the text
//       ctx.fillText(text, textX, textY);
//       ctx.save();
//     },
//   });


const data = {
  datasets: [
    {
      labels: ['목표'],
      backgroundColor: ['#025939', '#F0F2F2'],
      data: [p.acntAmount, p.goalAmount - p.acntAmount]
    }
  ],
  
}
const options = {
  legend : {
    display : false
  },
  responsive: false,
  cutout: '90%',
  maintainAspectRatio: false
}
</script>