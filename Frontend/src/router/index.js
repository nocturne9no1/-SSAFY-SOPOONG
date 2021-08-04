import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import PageNotFound from '../views/PageNotFound.vue'

// 없앨 예정
import ModalToWriteJournal from '../components/journals/ModalToWriteJournal.vue'

// modules
import accounts from './accounts.js'

Vue.use(VueRouter)

const routes = [
  ...accounts,
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  // 404
  {
    path: '*',
    redirect: '/404',
  },
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
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
