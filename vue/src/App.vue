<script setup>
import TheHeaderView from "./views/TheHeader/TheHeaderView.vue";
import TheFooterView from "./views/TheFooter/TheFooterView.vue";
</script>

<template>
  <div>
    <TheHeaderView />

    <body>
      <RouterView />
    </body>

    <TheFooterView />
  </div>
</template>

<style scoped>
body {
  padding: 1em 3em 1em;
  text-align: center;
  min-height: 700px;
}
</style>
