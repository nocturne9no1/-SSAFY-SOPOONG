<template>
  <div style="display:flex;" v-if="test()">
    <profile-box/>
    <TravelMap />
    <each-place-journal-card-list />
  </div>
</template>

<script>
// import PersonalProfileBox from '../../components/personal/PersonalProfileBox.vue';
import ProfileBox from '../../views/accounts/ProfileBox.vue';
import EachPlaceJournalCardList from './EachPlaceJournalCardList.vue';
import TravelMap from './TravelMap.vue';
import router from '@/router';

export default {
  name:'',
  components: {
    ProfileBox,
    EachPlaceJournalCardList,
    TravelMap,
    // PersonalProfileBox,
  },
  data() {
    return {
      sampleData: '',
      a: false,
    };
  },
  beforeCreate() {},
  created() {
    console.log(router.history)
  },
  beforeMount() {},
  mounted() {},
  beforeUpdate() {},
  updated() {},
  beforeUnmount() {},
  unmounted() {},
  methods: {
    test() {
      // 없어짐
      // return "" 
      console.log(this.image)
      return "값이 있으니 생김."
    }
  }
}
</script>

<style>

</style>