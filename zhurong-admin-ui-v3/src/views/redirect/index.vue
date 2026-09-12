<template>
  <div />
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const path = route.params.path as string
const query = route.query

if (path) {
  router.replace({ path: '/' + path, query })
} else {
  router.replace('/dashboard/workbench')
}
</script>