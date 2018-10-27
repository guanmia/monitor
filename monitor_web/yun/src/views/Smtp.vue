<template>
    <div class='smtp'>

        <h2>{{generateTitle('smtp')}}</h2>

        <el-form :model="dataForm" ref="dataForm" labelWidth="180px" :rules="rules">
            <el-form-item :label="$t('smtp.smtp_ip')" prop="smtp_ip">
                <el-input v-model="dataForm.smtp_ip"></el-input>
            </el-form-item>
            <el-form-item :label="$t('smtp.smtp_port')" prop="smtp_port">
                <el-input v-model="dataForm.smtp_port"></el-input>
            </el-form-item>
            <el-form-item :label="$t('smtp.is_encrypt')" prop="is_encrypt">
                <el-radio-group v-model="dataForm.is_encrypt">
                  <el-radio :label="0">{{$t('smtp.no')}}</el-radio>
                  <el-radio :label="1">{{$t('smtp.yes')}}</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item :label="$t('smtp.is_verify')" prop="is_verify">
                <el-radio-group v-model="dataForm.is_verify">
                  <el-radio :label="true">{{$t('smtp.need')}}</el-radio>
                  <el-radio :label="false">{{$t('smtp.noneed')}}</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item :label="$t('smtp.username')" prop="username" v-show="dataForm.is_verify === true">
                <el-input v-model="dataForm.username"></el-input>
            </el-form-item>
            <el-form-item :label="$t('smtp.password')" prop="password" v-show="dataForm.is_verify === true">
                <el-input type="password" v-model="dataForm.password"></el-input>
            </el-form-item>
            <el-form-item>
                <p class='blue'>{{$t('smtp.note')}}</p>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="saveForm">{{$t('table.save')}}</el-button>
                <el-button type="info" @click="resetForm">{{$t('table.reset')}}</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<style lang='scss'>
.smtp {
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
import rules from "@/base/rules";
import { getSmtp, smtpSetting } from "@/api/system";
export default {
  name: "smtp",
  components: {},
  data() {
    return {
      dataForm: {
        smtp_ip: null,
        smtp_port: null,
        username: null,
        password: null,
        is_encrypt: 0,
        is_verify: true
      },
      rules: {
        smtp_ip: [
          {
            required: true,
            message: this.$t("smtp.smtp_ipMsg"),
            trigger: "blur"
          },
          { validator: rules.domainIpRule, trigger: "blur" }
        ],
        smtp_port: [
          {
            required: true,
            message: this.$t("smtp.portMsg"),
            trigger: "blur"
          },
          { validator: rules.portRule, trigger: "blur" }
        ],
        username: [
          {
            required: true,
            message: this.$t("user.usernameMsg"),
            trigger: "blur"
          },
          { validator: rules.usernameRule, trigger: "blur" }
        ],
        password: [
          {
            required: true,
            message: this.$t("user.passwordMsg"),
            trigger: "blur"
          },
          //{ validator: rules.passwordRule, trigger: "blur" }
        ],
        is_encrypt: [
          {
            required: true,
            message: this.$t("smtp.is_encryptMsg"),
            trigger: "blur"
          }
        ],
        is_verify: [
          {
            required: true,
            message: this.$t("smtp.is_verifyMsg"),
            trigger: "blur"
          }
        ]
      }
    };
  },
  created() {
    this.getData();
  },
  methods: {
    getData() {
      getSmtp().then(res => {
        if (res.error_code === 0) {
          this.dataForm = res.data;
        }
      });
    },
    //点击修改
    saveForm() {
      this.$refs.dataForm.validate(valid => {
        if (valid) {
          smtpSetting(this.dataForm).then(res => {
            if (res.error_code === 0) {
              this.$notify({
                title: this.$t("table.success"),
                message: this.$t("smtp.updateSmtp"),
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
