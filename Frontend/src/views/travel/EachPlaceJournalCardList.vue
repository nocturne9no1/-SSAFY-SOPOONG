<template>
  <div style="display: flex;">
    <div class="journalCardList">
      <div class="wrapper">
        <div class="heading">
          <h1>{{ travel.travelTitle }}</h1>
          <!-- <button class="filterButton">Filter</button> -->
        </div>
        <div class="cards" v-if="images.length">
          <each-place-journal-card
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
import EachPlaceJournalCard from "@/components/travel/EachPlaceJournalCard.vue";
// import axios from "axios";

export default {
  name: "",
  components: {
    EachPlaceJournalCard,
  },
  data() {
    return {
      sampleData: "",
      travel: null,
      images: [],
    };
  },
  beforeCreate() {},
  async created() {
    for (let x of this.$store.getters['getMyTravelJournal']) {
      if (x.travelIdx === this.$store.getters['getPresentTravelIdx']) {
        this.travel = x
      }
    }
    this.images = this.$store.getters['getTravelDetail']
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
  margin-left: 10rem;
  margin-top: 7rem;
  margin-right: 3rem;
  /* text-align: center; */
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
  grid-template-columns: repeat(4, 300px);
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

/* .filterButton {
  position: absolute;
  right: 2rem;
} */
</style>
