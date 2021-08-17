<template>
  <div class="complete-form">
    <div class="form-box">
      <h1>PACKAGING</h1>
      <div class="title-wrap">
        <h2>Title</h2>
        <input v-model="travel.travelTitle" type="text" placeholder=" title">
      </div>
      <div class="comment-wrap">
        <h2>Comment</h2>
        <textarea v-model="travel.travelContent" class="text-box" name="" id="" rows="5" placeholder="content"></textarea>
      </div>
      <p></p>
      <div class="pick-wrap">
        <h2>대표사진 고르기</h2>
        <button class="pick-btn" @click="clickChoice">PICK</button>
      </div>
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

      <button @click="onTrans" class="btn">Publish</button>
    </div>

    <div v-if="isChoicePushed" class="side-window" v-click-outside="closeSideWindow">
      <!-- 이부분에 이미지를 띄워준다. -->
      <div v-for="(image, idx) in images" :key="idx" class="image-box">
        <!-- 이미지를 클릭 시에 대표사진으로 설정할 수 있게 한다. -->
        <img @click="setMainImage(image)" :src="image.preview" alt="">
      </div>
    </div>
    <div v-if="mainImage">
      <img class="main-image" :src="mainImage.preview" alt="">
    </div>
  </div>
</template>

<script>
import '../../components/css/createJourney/packagejourney.scss'
import vClickOutside from 'v-click-outside'
import axios from 'axios'

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
      mainImage: '',
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
      this.mainImage = image
      this.travel.travelLat = image.position.lat
      this.travel.travelLng = image.position.lng
      for ( let idx in this.travel.placeList ) {
        for ( let each in this.travel.placeList[idx].imageList ) {
          console.log(this.travel.placeList[idx].imageList[each].file)
          console.log(image)
          if ( this.travel.placeList[idx].imageList[each].file == image.file ) {
            console.log('여기까진 ㅇㅋ')
            this.travel.placeList[idx].imageList[each].isTravelLeader = true
          }
        }
      }
    },

onTrans() {
      const travel = new FormData()
      travel.append('travelTitle', this.travel.travelTitle)
      travel.append('userId', 'wpffl3333')
      travel.append('travelContent', this.travel.travelContent)
      travel.append('travelIsVisible', this.travel.travelIsVisible)
      travel.append('travelLat', this.travel.travelLat)
      travel.append('travelLng', this.travel.travelLng)

      for ( let idx in this.travel.placeList ) {
        travel.append(`placeList[${idx}].title`, this.travel.placeList[idx].title)
        travel.append(`placeList[${idx}].comment`, this.travel.placeList[idx].comment)
        travel.append(`placeList[${idx}].category.main`, this.travel.placeList[idx].category.main)
        travel.append(`placeList[${idx}].category.sub`, this.travel.placeList[idx].category.sub)
        travel.append(`placeList[${idx}].rates.rate1`, this.travel.placeList[idx].rates.rate1)
        travel.append(`placeList[${idx}].rates.rate2`, this.travel.placeList[idx].rates.rate2)
        travel.append(`placeList[${idx}].rates.rate3`, this.travel.placeList[idx].rates.rate3)
        travel.append(`placeList[${idx}].transport`, this.travel.placeList[idx].transport)
        const date = new Date(this.travel.placeList[idx].visitDate).toUTCString()
        travel.append(`placeList[${idx}].visitDate`, date)
        travel.append(`placeList[${idx}].position.lat`, this.travel.placeList[idx].position.lat)
        travel.append(`placeList[${idx}].position.lng`, this.travel.placeList[idx].position.lng)

        for ( let i in this.travel.placeList[idx].imageList ) {
          travel.append(`placeList[${idx}].imageList[${i}].file`, this.travel.placeList[idx].imageList[i].file)
          travel.append(`placeList[${idx}].imageList[${i}].position.lat`, this.travel.placeList[idx].imageList[i].position.lat)
          travel.append(`placeList[${idx}].imageList[${i}].position.lng`, this.travel.placeList[idx].imageList[i].position.lng)
          if(this.travel.placeList[idx].imageList[i].isPlaceLeader == true) {
            travel.append(`placeList[${idx}].imageList[${i}].isPlaceLeader`, 1)
          } else {
            travel.append(`placeList[${idx}].imageList[${i}].isPlaceLeader`, 0)
          }
          if(this.travel.placeList[idx].imageList[i].isTravelLeader == true) {
            travel.append(`placeList[${idx}].imageList[${i}].isTravelLeader`, 1)
          } else {
            travel.append(`placeList[${idx}].imageList[${i}].isTravelLeader`, 0)
          }
        }
      }

      console.log(travel)
      axios.post('http://localhost:8080/api/auth/travel/create', travel, {
        headers: {
          'Content-Type' : 'multipart/form-data'
        }
      })
        .then(res => {
          console.log(res)
          console.log("와!")
        })
        .catch(err => {
          console.log(err)
        })
    },

  }

}
</script>