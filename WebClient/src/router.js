import Vue from 'vue'
import VueRouter from 'vue-router'
import { routes } from './routes'
import store from './store'
Vue.use(VueRouter)

const router = new VueRouter({
    mode: 'history',
    routes
  })

  router.beforeEach((to, from, next) => {
    if(!store.getters.isAuthenticated && to.path !== '/Login')
        next('/Login');
    else if(store.getters.isAuthenticated && to.path =='/Login')
        next('/');
    else
        next();
  });
  
  export default router;