import axios from 'axios'
import router from '@/router'
// import cookies from 'vue-cookies'

const state = {}
const getters = {}
const mutations = {}
const actions = {
  requestEmailAuth(context, authKey) {
    console.log(context)
    const url = '???'
    axios.get(url, authKey)
      .then(res => {
        console.log(res.data)
        router.push('/')
      })
      .catch(err => {
        console.log(err)
      })
  }
}

export default {
  state, getters, mutations, actions
}