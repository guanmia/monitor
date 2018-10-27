<template>
    <div class='findbackpwd'>
        <h2>找回密码</h2>

        <el-steps :active='active' finish-status='success' process-status='wait' align-center>
            <el-step :title="$t('findbackpwd.step1')"></el-step>
            <el-step :title="$t('findbackpwd.step2')"></el-step>
            <el-step :title="$t('findbackpwd.step3')"></el-step>
        </el-steps>
        
        <div class='resetForm'>

            <el-form :model="resetForm" ref="resetForm" :rules="rules" labelWidth="120px" v-if='active !== 3'>

                <div v-show='active === 1'>
                    <el-form-item  label="E-mail：" prop="email">
                        <el-input v-model="resetForm.email" :placeholder="$t('findbackpwd.emailNote')"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button @click="next" >{{$t('table.submit')}} <i class='fa fa-hand-o-right fa-lg'></i></el-button>
                    </el-form-item>
                </div>

                <div v-show='active === 2'>
                    <el-form-item :label="$t('findbackpwd.code')+'：'" prop="code">
                        <el-input v-model="resetForm.code" :placeholder="$t('findbackpwd.codeNote')"></el-input>
                    </el-form-item>
                    <el-form-item :label="$t('findbackpwd.pass')+'：'" prop="pass">
                        <el-input v-model="resetForm.pass" type="password"></el-input>
                    </el-form-item>
                    <el-form-item :label="$t('findbackpwd.checkPass')+'：'" prop="checkPass">
                        <el-input v-model="resetForm.checkPass" type="password"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button @click="next">{{$t('table.submit')}} <i class='fa fa-hand-o-right fa-lg'></i></el-button>
                    </el-form-item>
                </div>
                
            </el-form>

            <div class='success' v-else>
                <h2>{{$t('findbackpwd.updateSuccess')}}</h2>
                <p>{{$t('findbackpwd.tip')}}</p>
                <el-button type='text'><router-link to='/login'>{{$t('findbackpwd.clickJump')}}</router-link></el-button>
            </div>

        </div>
    </div>
</template>

<style lang='scss'>
.findbackpwd {
  width: 800px;
  margin: auto;
  padding-top: 50px;
  h2 {
    font-weight: normal;
    text-align: center;
  }
  .el-steps {
    margin: 50px 0 150px 0;
  }

  @keyframes right {
    from {
      transform: translateX(10px);
      opacity: 1;
    }
    to {
      transform: translateX(0);
      opacity: 0.2;
    }
  }
  .resetForm {
    .el-form {
      width: 400px;
      margin: auto;
      .el-button {
        background-color: #f60;
        color: #fff;
        border: 1px solid #f60;
      }
      i {
        animation: right 1s linear 0s infinite alternate;
      }
    }
  }
  .success {
    text-align: center;
    h1 {
    }
    p {
      color: #666;
    }
  }
}
</style>

<script>
import { sendEmail, newUserPwd } from "@/api/user";

export default {
  name: "findbackpwd",
  components: {},
  data() {
    let eml = (rule, value, callback) => {
      let valReg = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
      if (!valReg.test(value)) {
        callback(new Error("输入E-mail格式有误！"));
      } else {
        callback();
      }
    };
    let validatePass2 = (rule, value, callback) => {
      if (value !== this.resetForm.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      active: 1, //进度条当前步骤
      visible: false,
      resetForm: { email: "", code: "", pass: "", checkPass: "" },
      rules: {
        email: [
          { required: true, message: "E-mail不能为空！", trigger: "blur" },
          { validator: eml, trigger: "blur" }
        ],
        code: [
          { required: true, message: "验证码不能为空！", trigger: "blur" }
        ],
        pass: [
          { required: true, message: "新密码不能为空！", trigger: "blur" }
        ],
        checkPass: [
          { required: true, message: "请再次输入密码！", trigger: "blur" },
          { validator: validatePass2, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    next() {
      switch (this.active) {
        case 1:
          this.$refs.resetForm.validateField("email", valid => {
            if (!valid) {
              sendEmail({email: this.resetForm.email}).then(res => {
                if (res.error_code === 0) {
                  this.active++;
                }
              });
            }
          });
          break;
        case 2:
          this.$refs.resetForm.validateField("code", valid => {
            if (!valid) {
              let params = {
                email: this.resetForm.email,
                code: this.resetForm.code,
                password: this.resetForm.pass
              }
              newUserPwd(params).then(res => {
                if (res.error_code === 0) {
                  this.active++;
                  setTimeout(() => {
                    this.$router.push({ path: "/login" });
                  }, 3000);
                }
              });
            }
          });
          break;
        default:
          break;
      }
    }
  }
};
</script>
