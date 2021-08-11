import Vue from 'vue'
import VueRouter from 'vue-router'
import PageNotFound from '../views/PageNotFound.vue'

// 없앨 예정
import ModalToWriteJournal from '../components/journals/ModalToWriteJournal.vue'
import test from '../views/test.vue'
import Pintest from '../views/Pintest.vue'
import completeForm from '../views/completeForm.vue'

// modules
import accounts from './accounts.js'
import main from './main.js'
import feed from './feed.js'
import travel from './travel.js'

Vue.use(VueRouter)

const routes = [
  ...accounts,
  ...main,
  ...feed,
  ...travel,

  // 404
  // {
  //   path: '*',
  //   redirect: '/404',
  // },
  {
    path: '/404',
    name: 'PageNotFound',
    component: PageNotFound
  },

  // 개발용 임시 router. 추후 삭제 예정
  {
    path: '/123',
    name: 'ModalToWriteJournal',
    component: ModalToWriteJournal
  },
  {
    path: '/',
    name: 'test',
    component: test,
  },
  {
    path: '/pintest',
    name: 'pintest',
    component: Pintest,
  },
  {
    path: '/last',
    name: 'completeForm',
    component: completeForm,
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
