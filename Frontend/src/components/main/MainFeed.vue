<template>
  <div style="display: flex;">
    <div class="journalCardList">
      <div class="wrapper">
        <div class="heading">
          <!-- <h1>Masonry MainFeed</h1> -->
          <!-- <button class="filterButton">Filter</button> -->
        </div>
        <!-- <div class="cards" v-if="images.length"> -->
        <div class="cards">
          <MainFeedJournalCard
            v-for="travel in travelList"
            :key="travel.travelIdx"
            :travel="travel"
          />
        </div>
          <!-- <MainFeedJournalCard
            v-for="image in images"
            :key="image.id"
            :image="image"
          /> -->
          
        <!-- <div class="cards-loading" v-else>
          Loading image....
        </div> -->
      </div>
    </div>
  </div>
</template>

<script>
import MainFeedJournalCard from "@/components/main/MainFeedJournalCard.vue";
import axios from "axios";

const DEFAULT_IMAGES_COUNT = 30;

export default {
  name: "",
  components: {
    MainFeedJournalCard,
  },

  props: {
    travelList: [Object, Array],
  },

  data() {
    return {
      sampleData: "",
      images: [],
    };
  },
  beforeCreate() {},
  async created() {
    // 기본이 development로 설정되어 있고 , 그 외에도 여러 모드가 있다.
    // console.log(process.env.NODE_ENV)
    if (process.env.NODE_ENV === "production") {
      await this.getRandomImages(DEFAULT_IMAGES_COUNT);
    } else {
      await this.getRandomImagesFromLocal();
    }
  },
  beforeMount() {},
  mounted() {},
  beforeUpdate() {},
  updated() {},
  beforeUnmount() {},
  unmounted() {},
  methods: {
    /**
     * @param {number} count
     */
    async getRandomImages(count) {
      try {
        const { data } = await axios.get(
          process.env.VUE_APP_URL + "/photos/random",
          {
            headers: {
              Authorization: "Client-ID " + process.env.VUE_APP_ACCESS_KEY,
            },
            params: {
              count,
            },
          }
        );
        // Binding data to this component data
        this.images = data;
      } catch (error) {
        console.error(error);
      }
    },
    async getRandomImagesFromLocal() {
      try {
        const { default: localData } = await import('@/assets/test_data.json')
        this.images = localData
      } catch (err) {
        console.error(err)
      }
    }
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
