<template>
  <div id="app">
    <Navbar/>
    <router-view/>
  </div>
</template>

<script>
import Navbar from './components/Navbar.vue'
import './components/css/style.scss'

export default ({
  name: 'App',
  components: {
    Navbar,
  }
})
</script>


<style lang="scss">
</style>