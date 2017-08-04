import * as types from '../mutation-types'

// state
const state = {
  moduleName: 'principal',
  moduleData: {}
}

// getters
const getters = {
  principal: state => state.moduleData,
  roles: state => state.moduleData.authorities,
  isAdmin: state => {
    let roles = state.moduleData.authorities
    if(roles){
      let record = roles.find(p => p.authority === 'ROLE_ADMIN')
      return record?true:false
    }else{
      return false
    }
  }
}

// actions
const actions = {
  addToPincipal({commit, state}, principal) {
    commit(types.ADD_TO_PRINCIPAL, principal)
  },
  clearToPrincipal({commit}) {
    commit(types.CLEAR_TO_PRINCIPAL)
  }
}

// mutations
const mutations = {
  [types.ADD_TO_PRINCIPAL](state, principal) {
    state.moduleData = principal
  },

  [types.CLEAR_TO_PRINCIPAL](state) {
    state.moduleData = {}
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
