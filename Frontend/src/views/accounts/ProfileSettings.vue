<template>
  <div>
    <p
      v-if="['ChangePassword', 'DeleteAccount'].indexOf($route.name) == -1"
      @click="test"
    >
      비밀번호 변경하기
    </p>
    <router-view v-if="sampleData == ''"></router-view>
  </div>
</template>
<script>
export default {
  name: "",
  components: {},
  data() {
    return {
      sampleData: "",
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
  methods: {
    test() {
      console.log('I am in !')
      console.log(this.$route);
    },
  },
};
</script>
