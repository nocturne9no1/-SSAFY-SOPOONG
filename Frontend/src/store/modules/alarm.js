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
  }
}

const actions = {
  // 알람 데이터 받아옴
  getAlarm(context, alarmData) {
    console.log(alarmData)
    context.commit('SET_ALARMLIST', alarmData)
  },
}

export default {
  state, getters, mutations, actions
}