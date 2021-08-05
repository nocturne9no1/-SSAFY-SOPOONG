<template>
  <div>
    존재하지 않는 페이지입니다.
    <SignUp />
  </div>
</template>

<script>
import SignUp from '@/views/accounts/SignUp.vue'

export default {
  name:'PageNotFound',
  components: {
    SignUp,
  },
  data() {
    return {
      sampleData: ''
    };
  },
  beforeCreate() {},
  created() {}, 
  beforeMount() {}, 
  mounted() {}, 
  beforeUpdate() {},
  updated() {},
  beforeUnmount() {}, 
  unmounted() {},
  methods: {}
}
</script>

<style>

</style>