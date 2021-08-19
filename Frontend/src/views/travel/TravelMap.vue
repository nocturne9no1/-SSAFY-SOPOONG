<template>
  <div class="travelmap">
    <GmapMap
        ref="mapRef"
        :center="path[0]"
        :zoom="14"
        :options="options"
        class="google-map"
    >
      <div v-for="(nowPlace, idx) in marekrs" :key="idx">
        <!-- 현재 카테고리에 따른 icon 랜더링 -->
        <div v-if="nowPlace.category.main === 'hotel'">
          <gmap-marker
            :position="nowPlace.position"
            :clickable="true"
            :icon="icons.hotel"
            @click="openModal(nowPlace)"
          />
        </div>
        <div v-else-if="nowPlace.category.main === 'res'">
          <gmap-marker
            :position="nowPlace.position"
            :clickable="true"
            :icon="icons.res"
            @click="openModal(nowPlace)"
          />
        </div>
        <div v-else-if="nowPlace.category.main === 'activity'">
          <gmap-marker
            :position="nowPlace.position"
            :clickable="true"
            :icon="icons.activity"
            @click="openModal(nowPlace)"
          />
        </div>
        <!-- 아직 아무 카테고리도 선택 안했을 때 -->
        <div v-else>
          <gmap-marker
            :position="nowPlace.position"
            :clickable="true"
            :icon="icons.basic"
            @click="openModal(nowPlace)"
          />
        </div>
      </div>

      <div v-for="(eachPath, idx) in paths" :key="idx">
        <EachPolyline
          :eachPath="eachPath"
        />
      </div>
      <gmap-polyline v-bind:path.sync="path" v-bind:options="{ strokeColor:'#000000'}"/> 
    </GmapMap>
  </div>
</template>
<script>
import * as VueGoogleMaps from 'vue2-google-maps'
import Vue from 'vue'
import '../../components/css/travelmap.scss'
// import vClickOutside from 'v-click-outside'

Vue.use(VueGoogleMaps, {
  load: {
    key: "AIzaSyBwPv3o2CKXGTbC5qPIjTCdU5F1ToEExio",
    libraries: "places",
    map_id: '6d451fecd5c294f9',
  }
})

export default {
  name:'TravelMap',
  components: {},
  data() {
    return {
      travel: '',
      images: '',

      options : {
        mapId: "6d451fecd5c294f9"
      },

      icons:  {
        basic: {
          url: require("../../assets/pins/basicpin.png"),
          scaledSize: {width: 28, height: 28},
          labelOrigin: {x: 16, y: -10},
        },
        hotel: {
          url: require("../../assets/pins/hotel-pin.png"),
          scaledSize: {width: 28, height: 28},
          labelOrigin: {x: 16, y: -10},
        },
        res: {
          url: require("../../assets/pins/restaurant-pin.png"),
          scaledSize: {width: 28, height: 28},
          labelOrigin: {x: 16, y: -10},
        },
        activity: {
          url: require("../../assets/pins/activity-pin.png"),
          scaledSize: {width: 28, height: 28},
          labelOrigin: {x: 16, y: -10},
        },
      },

      path: [],

      center: '',

      markers: [],
    };
  },
  beforeCreate() {},
  created() {
    for (let x of this.$store.getters['getMyTravelJournal']) {
      if (x.travelIdx === this.$store.getters['getPresentTravelIdx']) {
        this.travel = x
      }
    }
    this.images = this.$store.getters['getTravelDetail']
    
    // 경로 보여주기
    for (let image of this.images) {
      const position = new Object()
      position.lat = image.placeLat
      position.lng = image.placeLong
      this.path.push(position)

      const marker = new Object()
      marker.title = image.placeTitle
      marker.transport = image.placeTransport
      marker.visitDate = image.placeVisitDate
      marker.position = position
      marker.category = image.placeCategory
      marker.comment = image.placeComment

      this.markers.push(marker)
    }

    // 중심 좌표
    this.center = this.path[0]
  },
  beforeMount() {}, 
  mounted() {}, 
  beforeUpdate() {},
  updated() {},
  beforeUnmount() {}, 
  unmounted() {},
  methods: {}
}
</script>