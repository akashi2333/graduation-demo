<template>
  <div id="app">
    <Top></Top>
    <router-view />
    <Footer></Footer>
  </div>
</template>

<script>
import Top from '../src/components/Top.vue'
import Footer from '../src/components/Footer.vue'
export default {
  components: { Top, Footer },
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
}
</style>
