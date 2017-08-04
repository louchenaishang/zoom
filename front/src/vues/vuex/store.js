import Vue from 'vue'
import Vuex from 'vuex'
import principal from './modules/principal'

Vue.use(Vuex);
const debug = process.env.NODE_ENV !== 'production'

export default new Vuex.Store({
  modules: {
    principal
  },
  strict: debug,
})
