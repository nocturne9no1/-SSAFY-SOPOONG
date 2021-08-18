<template>
  <div style="display: flex;">
    <div class="journalCardList">
      <div class="wrapper">
        <div class="heading">
          <h1>Masonry MainFeed</h1>
          <!-- <button class="filterButton">Filter</button> -->
        </div>
        <div class="cards" v-if="images.length">
          <main-feed-journal-card
            v-for="image in images"
            :key="image.id"
            :image="image"
          />
        </div>
        <div class="cards-loading" v-else>
          Loading image....
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MainFeedJournalCard from "@/components/main/MainFeedJournalCard.vue";
// import axios from "axios";

export default {
  name: "",
  components: {
    MainFeedJournalCard,
  },
  data() {
    return {
      sampleData: "",
      images: [],
    };
  },
  beforeCreate() {},
  async created() {
    this.$store.dispatch('travelJournalList', this.$store.getters['getUserProfile'].userId)
    this.images = this.$store.getters['getMyTravelJournal']
    console.log(this.images)
  },
  beforeMount() {},
  mounted() {},
  beforeUpdate() {},
  updated() {},
  beforeUnmount() {},
  unmounted() {},
  methods: {
  },
};
</script>

<style scoped>
/* .journalCardList {
  display: block;
  position: inherit;
} */

.wrapper {
  /* margin-left: 10rem;
  margin-top: 7rem;
  margin-right: 3rem; */
}
.heading h1 {
  text-align: center;
  padding: 0 1rem;
}
.wrapper > div {
  margin: 3rem auto;
}
.wrapper .cards {
  display: grid;
  column-gap: 10px;
  row-gap: 7px;
  grid-template-columns: repeat(3, 400px);
  justify-content: center;
  max-width: var(--card-container-width);
}
.cards-loading {
  font-size: 1.25rem;
  text-align: center;
}

.heading {
  position: relative;
}

h1 {
  font-family: 'Do Hyeon';
}
/* .filterButton {
  position: absolute;
  right: 2rem;
} */
</style>
