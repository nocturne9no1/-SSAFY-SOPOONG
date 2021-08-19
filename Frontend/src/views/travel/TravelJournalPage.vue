<template>
  <div style="display:flex;" v-if="test()">
    <profile-box />
    <TravelMap />
    <each-place-journal-card-list v-if="false"/>
  </div>
</template>

<script>
import ProfileBox from '../../views/accounts/ProfileBox.vue';
import EachPlaceJournalCardList from './EachPlaceJournalCardList.vue';
import TravelMap from './TravelMap.vue';

export default {
  name:'',
  components: {
    ProfileBox,
    EachPlaceJournalCardList,
    TravelMap,
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
  methods: {
    test() {
      // 없어짐
      // return "" 
      return "값이 있으니 생김."
    }
  }
}
</script>

<style>

</style>