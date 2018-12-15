<template>
  <div>
    <b-navbar sticky toggleable="md" type="dark" variant="info">
      <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>
      <b-navbar-brand :to="{path: '/'}">Smart Planner</b-navbar-brand>
      <b-navbar-nav class="ml-auto">
        <b-nav-item v-if="isAuthenticated" active-class="active font-weight-bold" @click="logout()">Logout</b-nav-item>
        <b-nav-item-dropdown v-bind:text="$store.getters.language" right>
          <b-dropdown-item v-on:click="$store.commit('setLanguage', 'pl')">pl</b-dropdown-item>
          <b-dropdown-item v-on:click="$store.commit('setLanguage', 'en')">en</b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-navbar>

    <b-navbar fixed="bottom" toggleable="md" type="dark" variant="info">
        <b-navbar-brand class="footer">Copyright&copy; 2018 by GKN. All rights reserved.</b-navbar-brand>
    </b-navbar>
     <router-view></router-view>
  </div>
</template>

<script>
export default {
  data() {
    return {
      lang: String,
      //TODO sk: remove when database will be ready
      username: '',
      password: '',
    }
  },
  computed: {
    isAuthenticated() {
      return this.$store.getters.isAuthenticated;
    }
  },
  created() {
    this.$store.commit('setLanguage', 'pl');
  },
  methods: {
    logout() {
      this.$store.commit("logout");
      this.$root.$router.push({path: "/Login"});
    }
  }
}
</script>

<style lang="css">
  .footer {
    font-style: italic;
    opacity: 0.25;
  }
</style>
