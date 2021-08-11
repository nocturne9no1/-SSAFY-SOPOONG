<template>
  <div class="complete-form">
    <div class="form-box">
      <h1>PACKAGING</h1>
      <h2>Title</h2>
      <input type="text" placeholder=" title">
      <h2>Comment</h2>
      <textarea class="text-box" name="" id="" rows="5" placeholder="content"></textarea>
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

    <div v-show="isChoicePushed" class="photo-container">

    </div>

    <input type="checkbox" id="sidewindow">
    <div>
      <label for="sidewindow" class="menu">ㅇ</label>
    </div>

    <div class="container">
      asdf
      <img src="" alt="">
    </div>

    <div class="sidewindow_content">
      <ul>
        <li>1</li>
        <li>2</li>
        <li>3</li>
        <li>4</li>
      </ul>
      <label for="sidewindow"></label>
    </div>
    <label for="sidewindow"></label>
  </div>
</template>

<script>
import '../components/css/completeform.scss'

export default {
  name:'completeForm',
  components: {},
  data() {
    return {
      sampleData: '',
      publicSetting: false,
      isChoicePushed: false,
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
    toggle() {
      this.publicSetting = !this.publicSetting
    },
    clickChoice() {
      this.isChoicePushed = !this.isChoicePushed
    },
  }
}
</script>