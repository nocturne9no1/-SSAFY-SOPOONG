<template>
  <div class="complete-form">
    <div class="form-box">
      <h1>PACKAGING</h1>
      <h2>Title</h2>
      <input v-model="travel.travelTitle" type="text" placeholder=" title">
      <h2>Comment</h2>
      <textarea v-model="travel.travelContent" class="text-box" name="" id="" rows="5" placeholder="content"></textarea>
      <p></p>
      <h2>대표사진 고르기</h2>
      <button @click="clickChoice">사진고르기</button>
      <p></p>
      <div class="public-setting">
        <h2>공개 여부</h2>

        <div
          class="toggle-btn"
          :class="{'active':publicSetting}">
          <input type="checkbox"
            :checked="publicSetting"
            class="cb-value"
            @click="toggle()"
          >
          <span class="round-btn"></span>
        </div>
      </div>
      <p></p>

      <button class="btn">Publish</button>
    </div>

    <div v-if="isChoicePushed" class="side-window" v-click-outside="closeSideWindow">
      <!-- 이부분에 이미지를 띄워준다. -->
      <div v-for="(image, idx) in images" :key="idx" class="image-box">
        <!-- 이미지를 클릭 시에 대표사진으로 설정할 수 있게 한다. -->
        <img @click="setMainImage(image)" :src="image.preview" alt="">
      </div>
    </div>
  </div>
</template>

<script>
import '../../components/css/createJourney/packagejourney.scss'
import vClickOutside from 'v-click-outside'


export default {
  name:'completeForm',
  components: {},

  props: {
    images: Array,
    travel: Object,
  },

  directives: {
    clickOutside: vClickOutside.directive
  },

  data() {
    return {
      sampleData: '',
      publicSetting: true,
      isChoicePushed: false,
      imageList: new Array(),
    };
  },
  beforeCreate() {},
  created() {
  }, 
  beforeMount() {}, 
  mounted() {

  }, 
  beforeUpdate() {},
  updated() {},
  beforeUnmount() {}, 
  unmounted() {},

  computed: {
    renderImage() {
      return (image) => { URL.createObjectURL(image.file) }
    },
  },

  methods: {
    toggle() {
      this.publicSetting = !this.publicSetting
      this.travel.travelIsVisible = !this.travel.travelIsVisible
    },

    clickChoice() {
      this.isChoicePushed = !this.isChoicePushed
    },

    closeSideWindow() {
      this.isChoicePushed = false
    },

    setMainImage(image) {
      this.travel.mainImage = image
      this.travel.travelLat = image.position.lat
      this.travel.travelLng = image.position.lng
      for ( let idx in this.travel.placeList ) {
        for ( let each in this.travel.placeList[idx].fileList ) {
          console.log(this.travel.placeList[idx].fileList[each].file)
          console.log(image)
          if ( this.travel.placeList[idx].fileList[each].file == image.file ) {
            console.log('여기까진 ㅇㅋ')
            this.travel.placeList[idx].fileList[each].isTravelLeader = true
          }
        }
      }
    },

  }

}
</script>