import Vue from 'vue';
import Vuex from 'vuex';
import app from './modules/app';
import tagsView from './modules/tagsView';
import permission from './modules/permission';
import user from './modules/user';
import base from './modules/base';
import getters from './getters';

Vue.use(Vuex);

const store = new Vuex.Store({
    modules: {
        app,
        permission,
        tagsView,
        user,
        base
    },
    getters
});

export default store;
