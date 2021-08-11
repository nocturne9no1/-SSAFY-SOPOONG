<template>
  <div style="display:flex;">
    <profile-box />
    <each-place-journal-card-list />
  </div>
</template>

<script>
import ProfileBox from '../../views/accounts/ProfileBox.vue';
import EachPlaceJournalCardList from './EachPlaceJournalCardList.vue';


export default {
  name:'',
  components: {
    ProfileBox,
    EachPlaceJournalCardList,
    
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