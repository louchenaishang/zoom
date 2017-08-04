import Vue from 'vue'
import VueRouter from 'vue-router'
import routes from './config'

Vue.use(VueRouter);

const router = new VueRouter({
  mode: 'history',
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path == '/login') {
    let token = JSON.parse(sessionStorage.getItem('token'));
    if (token) {
      next({path: '/'})
    } else {
      next()
    }
  } else {
    let token = JSON.parse(sessionStorage.getItem('token'));
    if (!token) {
      next({path: '/login'})
    } else {
      next()
    }
  }
})

export default router
