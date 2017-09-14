import Vue from 'vue'
import Vuex from 'vuex'
import principal from '@/vues/vuex/modules/principal'

Vue.use(Vuex);
const debug = process.env.NODE_ENV !== 'production'

export default new Vuex.Store({
  modules: {
    principal
  },
  strict: debug,
})
