<template>
  <codemirror :value="code" @input="updateCode" placeholder="// 코드를 입력하세요" :style="{ height: '200px' }" :autofocus="true" :font-size="20"
              :dark="true" :indent-with-tab="true" :tab-size="4" :extensions="extensions"  />
<!--              @ready="handleReady" @change="log('change', $event)" @focus="log('focus', $event)" @blur="log('blur', $event)"-->
</template>

<script setup>
import { ref, shallowRef, defineProps, defineEmits } from 'vue'
import { Codemirror } from 'vue-codemirror'
import { java } from '@codemirror/lang-java'
import { html } from '@codemirror/lang-html'
import { oneDark } from '@codemirror/theme-one-dark'
import { nord, nordInit } from '@uiw/codemirror-theme-nord';

const { modelValue } = defineProps(['modelValue']);
const emit = defineEmits();

const code = ref(modelValue);

function updateCode(value) {
  console.log(code);
  console.log(modelValue);

  console.log(value);
  console.log(value.data);
  emit('update:modelValue', value);
}

const extensions = [html(), nord]

// Codemirror EditorView instance ref
const view = shallowRef()
const handleReady = (payload) => {
  view.value = payload.view
}

// Status is available at all times via Codemirror EditorView
const getCodemirrorStates = () => {
  const state = view.value.state
  const ranges = state.selection.ranges
  const selected = ranges.reduce((r, range) => r + range.to - range.from, 0)
  const cursor = ranges[0].anchor
  const length = state.doc.length
  const lines = state.doc.lines
  // more state info ...
  // return ...
}

const log = console.log;
// console.log(code.value);
</script>
