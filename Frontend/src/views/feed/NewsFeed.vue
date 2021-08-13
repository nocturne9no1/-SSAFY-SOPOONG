<template>
  <div style="display: flex;">
    <profile-box />
    <div class="journalCardList">
      <div class="wrapper">
        <div class="heading">
          <h1>Masonry NewsFeed</h1>
          <!-- <button class="filterButton">Filter</button> -->
        </div>
        <div class="cards" v-if="images.length">
          <feed-journal-card
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
    <FeedFilter />
  </div>
</template>

<script>
import FeedJournalCard from "@/components/feed/FeedJournalCard.vue";
import axios from "axios";
import ProfileBox from '@/views/accounts/ProfileBox.vue';
import FeedFilter from '@/components/FeedFilter.vue';

const DEFAULT_IMAGES_COUNT = 30;

export default {
  name: "",
  components: {
    FeedJournalCard,
    ProfileBox,
    FeedFilter,
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
    if (process.env.NODE_ENV === "development") {
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
  margin-left: 10rem;
  margin-top: 7rem;
  margin-right: 3rem;
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

/* .filterButton {
  position: absolute;
  right: 2rem;
} */
</style>
