<template>
  <div class="mainContent">
    <Header></Header>
    <div class="content">
      <router-view/>
    </div>
  </div>
</template>

<script>
import Header from "../components/nav/Header.vue";

export default {
  name: "Home",
  components: {Header}
}
</script>

<style scoped>
.mainContent {
  display: flex;
  flex-direction: column;
}

.content {
  width: 100%;
  display: flex;
  flex-direction: column;
}
</style>