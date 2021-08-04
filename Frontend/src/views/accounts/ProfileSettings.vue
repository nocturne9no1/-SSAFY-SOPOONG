<template>
  <div>
    <ProfileBox/>
    <div class="settings">
      <div class="container">

        <div class="logo" id="logo">
          <h1 class="logo-font">SO<br/>POONG</h1>
          <img class="logo-img" src="../../assets/logo.png" alt="logo img">
        </div>

        <div class="leftbox">
          <nav>
            <a @click="clickCategory('user')"
            id="user" :class="{'active':nowSelect.user}"><i class="fa fa-user"></i>
            </a>
            <a @click="clickCategory('passwordChange')"
            id="passwordChange" :class="{'active':nowSelect.passwordChange}"><i class="fas fa-key"></i>
            </a>
            <a @click="clickCategory('alarmSetting')"
            id="alarmSetting" :class="{'active':nowSelect.alarmSetting}"><i class="fas fa-bell"></i>
            </a>
            <a @click="clickCategory('accountRelease')"
            id="accountRelease" :class="{'active':nowSelect.accountRelease}"><i class="fas fa-unlock-alt"></i>
            </a>
            <a @click="clickCategory('deleteAccount')"
            id="deleteAccount" :class="{'active':nowSelect.deleteAccount}"><i class="fas fa-trash-alt"></i>
            </a>
          </nav>
        </div>
        <div class="rightbox">

          <div v-if="nowSelect.user" class="profile">
            <h1>Personal Info</h1>
            <h2>Nickname</h2>
            <p><input type="text" v-model="user.nickname"></p>
            <h2>Email</h2>
            <p>{{user.email}}</p>
            <h2>Introduce</h2>
            <p><input type="textarea" v-model="user.introduce"></p>
            <button class="btn">Change</button>
          </div>
          
          <div v-if="nowSelect.passwordChange" class="payment">
            <h1>Passwrod Change</h1>
            <h2>Now Password</h2>
            <p><input type="password"
            v-model="passwordChange.nowPassword"
            placeholder="Enter your password"></p>
            <h2>Change Password</h2>
            <p><input type="password" 
            v-model="passwordChange.changePassword"
            placeholder="Enter change password"></p>
            <h2>Confirm Password</h2>
            <p><input type="password" 
            v-model="passwordChange.passwordConfirm"
            placeholder="Enter password again"></p>
            <br>
            <button class="btn">Update</button>
          </div>

          <div v-if="nowSelect.alarmSetting" class="subscription">
            <h1>Alarm Setting</h1>
            <h2>Someone Like My Journey</h2>
            <div
              class="toggle-btn"
              :class="{'active':alarmSetting.someoneLike}">
              <input type="checkbox"
                :checked="alarmSetting.someoneLike"
                class="cb-value"
                @click="clickAlarmSetting('someoneLike')"
              >
              <span class="round-btn"></span>
            </div>
            
            <h2>Someone Followed Me</h2>
            <div
              class="toggle-btn"
              :class="{'active':alarmSetting.someoneFollow}">
              <input type="checkbox"
                :checked="alarmSetting.someoneFollow"
                class="cb-value"
                @click="clickAlarmSetting('someoneFollow')"
              >
              <span class="round-btn"></span>
            </div>
            <h2>Follower Submit Journey</h2>
            <div
              class="toggle-btn"
              :class="{'active':alarmSetting.followerSubmit}">
              <input type="checkbox"
                :checked="alarmSetting.followerSubmit"
                class="cb-value"
                @click="clickAlarmSetting('followerSubmit')"
              >
              <span class="round-btn"></span>
            </div>
            <button class="btn">Update</button>
          </div>

          <div v-if="nowSelect.accountRelease" class="privacy">
            <h1>Account Release Setting</h1>
            <h2>Do you want set your account to public?</h2>
            <div
              class="toggle-btn"
              :class="{'active':accountRelease}">
              <input type="checkbox"
                :checked="accountRelease"
                class="cb-value"
                @click="clickAccountRelease()"
              >
              <span class="round-btn"></span>
            </div>
          </div>

          <div v-if="nowSelect.deleteAccount" class="settings">
            <h1>Delete Account</h1>
            <h2>Do you want delete your account?</h2>
            <p><input type="password" placeholder="Enter your password"></p>
            <button class="btn">Delete</button>
          </div>

        </div>
      </div>

    </div>
  </div>
</template>


<script>
import '../../components/css/profilesetting.scss'
import ProfileBox from './ProfileBox.vue'

export default {
  name:'',
  components: {
    ProfileBox,
  },
  data() {
    return {
      // 아래 데이터 들은 향후 백엔드 완성 시에
      // 모두 서버의 대이터로 대체되어야 합니다.
      nowSelect: {
        user: true,
        passwordChange: false,
        alarmSetting: false,
        accountRelease: false,
        deleteAccount: false,
      },
      user: {
        nickname: 'deriyakki',
        email: 'nocturne9no1@gmail.com',
        introduce: 'hi',
      },
      passwordChange: {
        nowPassword: '',
        changePassword: '',
        passwordConfirm: '',
      },
      alarmSetting: {
        someoneLike: true,
        someoneFollow: true,
        followerSubmit: true,
      },
      accountRelease: true,
    };
  },
  methods: {
    clickCategory(category) {
      this.nowSelect[category] = true;
      for (let key in this.nowSelect) {
        if (key != category) { this.nowSelect[key] = false }
      }
    },
    clickAlarmSetting(category) {
      this.alarmSetting[category] = !this.alarmSetting[category];
    },
    clickAccountRelease() {
      this.accountRelease = !this.accountRelease
    }
  }
}
</script>