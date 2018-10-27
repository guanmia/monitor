
const base = {
  state: {
    downloadLoading: false //导出按钮的加载
  },
  mutations: {
    DOWN_LOAD_LOADING: (state, msg) => {
      state.downloadLoading = msg;
    }
  },
  actions: {
    changeDownloadLoading({ commit }, msg) {
      commit('DOWN_LOAD_LOADING', msg);
    }
  }
};

export default base;