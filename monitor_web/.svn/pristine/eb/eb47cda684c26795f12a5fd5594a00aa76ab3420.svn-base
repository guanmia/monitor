<template>
    <div class='change-pwd'>

        <h2>{{generateTitle('changepwd')}}</h2>

        <el-form :model="dataForm" ref="dataForm" :rules='rules' labelWidth="150px">
            <el-form-item :label="$t('changepwd.newPwd')" prop="newPwd">
                <el-input type="password" v-model="dataForm.newPwd"></el-input>
            </el-form-item>
            <el-form-item :label="$t('changepwd.newPwdRep')" prop="newPwdRep">
                <el-input type="password" v-model="dataForm.newPwdRep"></el-input>
            </el-form-item>
            <el-form-item :label="$t('changepwd.password')" prop="password">
                <el-input type="password" v-model="dataForm.password"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="saveForm">{{$t('table.submit')}}</el-button>
                <el-button type="info" @click="resetForm">{{$t('table.reset')}}</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<style lang='scss'>
.change-pwd {
  h2 {
    font-weight: normal;
    text-align: center;
    margin: 50px 0;
  }
  .el-form {
    width: 600px;
    margin: auto;
  }
}
</style>

<script>
import { changePwd } from "@/api/user";
import { encrypt, decrypt } from "@/utils/encrypt";
export default {
  name: "change-pwd",
  components: {},
  data() {
    let newPwdRepRule = (rule, value, callback) => {
      if (value !== this.dataForm.newPwd) {
        callback(new Error(this.$t("changepwd.newPwdRepRule")));
      } else {
        callback();
      }
    };
    let passwordRule = (rule, value, callback) => {
      if (value === this.dataForm.newPwd) {
        callback(new Error(this.$t("changepwd.passwordRule")));
      } else {
        callback();
      }
    };
    return {
      dataForm: { newPwd: "", newPwdRep: "", password: "" },
      rules: {
        newPwd: [
          {
            required: true,
            message: this.$t("changepwd.newPwdMsg"),
            trigger: "blur"
          }
        ],
        newPwdRep: [
          {
            required: true,
            message: this.$t("changepwd.newPwdRepMsg"),
            trigger: "blur"
          },
          { validator: newPwdRepRule, trigger: "blur" }
        ],
        password: [
          {
            required: true,
            message: this.$t("changepwd.passwordMsg"),
            trigger: "blur"
          },
          { validator: passwordRule, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    //点击修改密码
    saveForm() {
      this.$refs.dataForm.validate(valid => {
        if (valid) {
          let pwddata = {
            newPwd: encrypt(this.dataForm.newPwd.trim()),
            password: encrypt(this.dataForm.password.trim())
          };
          //console.log(pwddata);

          changePwd(pwddata).then(res => {
            if (res.error_code === 0) {
              this.$notify({
                title: "成功",
                message: "修改密码成功",
                type: "success",
                duration: 2000
              });
            }
          });
        }
      });
    },
    //重置
    resetForm() {
      this.$refs.dataForm.resetFields();
    }
  }
};
</script>
