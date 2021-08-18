// import axios from 'axios'
// import router from '@/router'

const state = {
  alarmList: null,
}

const getters = {
  isAlarm: state => !!state.alarmList
}
const mutations = {
  SET_ALARMLIST(state, alarmData) {
    state.alarmList = alarmData
    if ( alarmData.length > 0 ) {
      state.isAlarm = true
    }
  }
}

const actions = {
  // 알람 데이터 받아옴
  getAlarm(context, alarmData) {
    console.log(alarmData)
    context.commit('SET_ALARMLIST', alarmData)
  },
  removeAlarmList() {
    console.log('리무브 알람')
  }
}


export default {
  state, getters, mutations, actions
}