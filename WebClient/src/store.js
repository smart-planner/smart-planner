import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
      user: {
          username: "",
          token: "",
      },
      appData: {
          language: ""
      }
  },
  getters: {
    isAuthenticated: state => state.user.token != "",
    language: state => state.appData.language,
  },
  mutations: {
      login(state, data) {
          state.user.token = data.accessToken;
          state.user.username = data.username;
      },
      logout(state) {
          state.user.token = "";
          state.user.username = "";
      },
      setLanguage(state, language) {
          state.appData.language = language;
      }
  }
})