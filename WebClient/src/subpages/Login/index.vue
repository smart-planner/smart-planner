<template>
  <div>
    <b-modal ref="registerModalRef" id="register" header-bg-variant="info" header-text-variant="white" centered hide-footer size="lg" :title="form.headers.registerButton">
      <register :isModal="true" block @registered="onRegistered" v-if="Register.drawComponent"></register>
    </b-modal>
    <b-container>
      <b-row class="text-left align-items-center">
        <b-col cols="4" class="mt-4 ml-auto mr-auto">
          <b-card>
            <b-form @submit="onSubmit">
              <b-form-group :label="form.headers.username" label-for="username">
                <b-form-input id="login_username" type="text" v-model="form.inputs.username" required :placeholder="form.headers.enterUsername">
                </b-form-input>
              </b-form-group>
              <b-form-group :label="form.headers.password" label-for="password">
                <b-form-input id="login_password" type="password" v-model="form.inputs.password" required :placeholder="form.headers.enterPassword">
                </b-form-input>
              </b-form-group>
                <b-button type="submit" variant="primary">{{form.headers.loginButton}}</b-button>
                <b-button variant="link" class="float-right" @click="drawModal" v-b-modal.register>{{form.headers.registerButton}}</b-button>
            </b-form>
          </b-card>
          <b-alert class="mt-2" dismissible fade :show="alert.dismissCountDown" :variant="alert.type" @dismissed="alert.dismissCountDown=0" @dismiss-count-down="countDownChanged">{{this.alert.msg}}</b-alert>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import Register from "../Register/"
import listener from "../../utilities/listeners"

export default {
  data() {
    return {
      Register: {
        drawComponent: true
      },
      form: {
        inputs: {
          username: '',
          password: ''
        },
        headers: {
          username: '',
          enterUsername: '',
          password: '',
          enterPassword: '',
          loginButton: '',
          registerButton:'',
          errorMsg: '',
          createdUser: '',
          badLogin: ''
        }
      },
      alert: {
        dismissCountDown: 0,
        dismissSecs: 3,
        type: '',
        msg: '',
      }
    }
  },
  mounted() {
    this.chooseLanguage(this.language);
    listener.addValidityListeners(document, ['login_username', 'login_password'], this.form.headers.emptyInput);
  },
  computed: {
    language() {
      return this.$store.getters.language;
    }
  },
  watch: {
    language (newVal, oldVal) {
    listener.removeValidityListeners(document, ['login_username', 'login_password'], this.form.headers.emptyInput);
    this.chooseLanguage(newVal);
    listener.addValidityListeners(document, ['login_username', 'login_password'], this.form.headers.emptyInput);
    }
  },
  methods: {
    chooseLanguage(lang) {
      switch(lang) {
        case "pl":
              this.form.headers.username = "Nazwa Użytkownika";
              this.form.headers.enterUsername = "Podaj nazwę";
              this.form.headers.password = "Hasło";
              this.form.headers.enterPassword = "Podaj hasło";
              this.form.headers.loginButton = "Zaloguj";
              this.form.headers.registerButton = "Zarejestruj";
              this.form.headers.emptyInput = "Uzupełnij to pole.";
              this.form.headers.createdUser = "Utworzono użytkownika.";
              this.form.headers.badLogin = "Błędna nazwa użytkownika lub hasło.";
        break;
        case "en":
              this.form.headers.username = "Username";
              this.form.headers.enterUsername = "Enter username";
              this.form.headers.password = "Password";
              this.form.headers.enterPassword = "Enter password";
              this.form.headers.loginButton = "Login";
              this.form.headers.registerButton = "Register";
              this.form.headers.emptyInput = "Fill this field.";
              this.form.headers.createdUser = "User created succesfully.";
              this.form.headers.badLogin = "Wrong username or password.";
        default:
        break;
      }
    },
    onSubmit(evt) {
      evt.preventDefault();
      var data = {
          // TODO sk: load access token from web api when available
          accessToken: "1",
          username: this.form.username,
      }
      // TODO sk: validate with data from database when available
      if(this.form.inputs.username == this.$root.$data.username && this.form.inputs.password == this.$root.$data.password)
      {
        this.$store.commit('login', data);
        this.$router.push({path: '/'});
      }
      else
      {
        this.alert.type = "danger";
        this.alert.msg = this.form.headers.badLogin;
        this.alert.dismissCountDown = this.alert.dismissSecs;
      }
    },

    onRegistered() {
      this.$refs.registerModalRef.hide();
      this.alert.type = "success";
      this.alert.msg = this.form.headers.createdUser;
      this.alert.dismissCountDown = this.alert.dismissSecs;
    },

    countDownChanged (dismissCountDown) {
      this.alert.dismissCountDown = dismissCountDown
    },

    drawModal() {
      var vm = this;
      vm.Register.drawComponent=false;
      this.$root.$nextTick(function() {
        vm.Register.drawComponent = true;
      });
    },
  },

  components: {
    Register
  }
};
</script>
