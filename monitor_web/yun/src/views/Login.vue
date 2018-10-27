<template>
    <div class='login' :style="{background: bg}">
        <main class="main">
            <el-tooltip effect="dark" :content="$t('navbar.language')" placement="bottom">
                <lang-select class="international"></lang-select>
            </el-tooltip>

            <div class='title'>
              <div class='logo'>
                <img src="../assets/images/logo.png" alt="" />
              </div>
              <h1>{{title}}</h1>
              <p>{{devCo}}</p>
            </div>

            <el-form ref="loginForm" :model="loginForm" :rules='loginRules' labelWidth="100px">
                <el-form-item :label="$t('login.username')+'：'" prop='username'>
                    <el-input v-model="loginForm.username" @keyup.enter.native="login"></el-input>
                </el-form-item>
                <el-form-item :label="$t('login.password')+'：'" prop='password'>
                    <el-input type="password" v-model="loginForm.password" @keyup.enter.native="login"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-checkbox v-model="loginForm.isKeepLogin">{{$t('login.keepLogin')}}</el-checkbox>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="login">{{$t('login.logIn')}}</el-button>
                    <el-button><router-link to='/findbackpwd'>{{$t('login.forgetPwd')}}</router-link></el-button>
                </el-form-item>
            </el-form>
        </main>
    </div>
</template>

<style lang='scss'>
@import "../assets/css/base.scss"; 

/*@keyframes loginBg{
        from { background: #333;}
        to { background: #666;}
    }*/

.login {
  min-height: 100%; 
  position: relative;
  //background: url(../assets/images/loginbg.jpg) no-repeat center bottom;
  display: flex; justify-content:center; align-items:flex-end;
  .main {
    width: 860px; 
    height:420px;
    //border:1px solid #fff;
    position: relative;
    display: flex;
    bottom: 230px;
    .international {
      position: absolute;
      right: 0;
      margin: 18px 10px 0 0;
      color: #fff;
    }
    .title{ color:#fff; flex:1; margin:auto; text-align:center; position:relative; //border:1px solid #fff;
      //.logo{}
      h1{ font-size:40px; font-family:SimSun;}
      p{ width:100%; position:absolute; margin-top:110px;}
    }
    .el-form { flex:1; margin-top:70px;
      .el-form-item {
        &:nth-child(1),
        &:nth-child(2) {
          margin-top: 35px;
        }
      }
      .el-form-item__label,
      .el-checkbox__label {
        color: #fff;
      }
      .el-input {
        width: 90%;
      }
      .el-button--primary {
        background-color: #f44336;
        border-color: #f44336;
      }
    }
  }
}
</style>

<script>
//import { isvalidUsername } from "@/utils/validate";
import rules from "@/base/rules";
import { getAutoLogin } from "@/utils/auth";
import LangSelect from "@/components/LangSelect"; //语言切换
export default {
  name: "login",
  components: { LangSelect },
  data() {
    // const validateUsername = (rule, value, callback) => {
    //   if (!isvalidUsername(value)) {
    //     callback(new Error("Please enter the correct user name"));
    //   } else {
    //     callback();
    //   }
    // };
    // const validatePassword = (rule, value, callback) => {
    //   if (value.length < 6) {
    //     callback(new Error("The password can not be less than 6 digits"));
    //   } else {
    //     callback();
    //   }
    // };
    return {
      loginbg: process.env.loginbg,
      title: process.env.title,
      devCo: process.env.devCo,
      bg: "url("+ require('../assets/images/'+ process.env.loginbg +'') + ") no-repeat center bottom",
      loginForm: {
        username: "",
        password: "",
        isKeepLogin: getAutoLogin()
      },
      loginRules: {
        username: [
          {
            required: true,
            message: this.$t("user.usernameMsg"),
            trigger: "blur"
          },
          { trigger: "blur", validator: rules.usernameRule }
        ],
        password: [
          {
            required: true,
            message: this.$t("user.passwordMsg"),
            trigger: "blur"
          },
          //{ trigger: "blur", validator: rules.passwordRule }
        ]
      }
    };
  },
  created(){
    //console.log(process.env.title);
  },
  methods: {
    login() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          this.$store
            .dispatch("LoginByUsername", this.loginForm)
            .then(() => {
              this.loading = false;
              // sessionStorage.setItem('username',this.loginForm.username);
              //sessionStorage.setItem('isSuper',true);
              this.$router.push({ path: "/home" });
            })
            .catch(() => {
              this.loading = false;
            });
        } else {
          console.log("error submit!");
          return false;
        }
      });
    }
  }
};
</script>
