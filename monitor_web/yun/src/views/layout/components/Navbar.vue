<template>
    <div class='navheader'>
        <h1 class="logo"><span>{{$t('title')}}</span></h1>
        <div class="navbar">
            <!--面包屑导航-->
            <breadcrumb class="breadcrumb-container"></breadcrumb>
            <ul>
                <li>
                    <el-tooltip effect="dark" :content="$t('navbar.screenfull')" placement="bottom">
                        <screenfull class="screenfull"></screenfull>
                    </el-tooltip>
                </li>
                <li>
                  <el-tooltip effect="dark" :content="$t('navbar.language')" placement="bottom">
                    <lang-select class="international"></lang-select>
                  </el-tooltip>
                </li>
                <li>
                    <el-dropdown trigger="click">
                        <el-button>
                            {{username}} <i class="el-icon-arrow-down el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu slot="dropdown">
                            <!--<el-dropdown-item><router-link to="/home/user">用户管理</router-link></el-dropdown-item>
                            <el-dropdown-item><router-link to="/home/system">系统管理</router-link></el-dropdown-item>
                            <el-dropdown-item><router-link to="/home/logs">操作日志</router-link></el-dropdown-item>-->
                            <el-dropdown-item><router-link to="/user/changepwd">{{$t('navbar.changePassword')}}</router-link></el-dropdown-item>
                            <el-dropdown-item><a href="javascript:;" @click="logout">{{$t('navbar.logOut')}}</a></el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </li>
            </ul>
        </div>
    </div>
</template>

<style lang='scss' scoped>
.navheader {
  display: flex;
  align-items: center;
  .logo {
    background: #f44336;
    height: 60px;
    span {
      display: inline-block;
      width: 240px;
      height: 60px;
      line-height: 60px;
      color: #fff;
      font-size: 24px;
      text-align: center;
    }
  }
  .navbar {
    flex: 1;
    display: flex;
    align-items: center;
    .hamburger-container {
      line-height: 58px;
      height: 50px;
      float: left;
      padding: 0 10px;
    }
    ul {
      margin-left: auto;
      display: flex;
      align-items: center;
      li {
        cursor: pointer;
        padding: 0 10px;
        .screenfull {
        }
        .international {
        }
        .el-button {
          background-color: #f44336;
          color: #fff;
          border: none;
        }
      }
    }
  }
}
</style>

<script>
import { mapGetters } from "vuex"; //mapGetters辅助函数仅仅是将store中的getter映射到局部计算属性
import Breadcrumb from "@/components/Breadcrumb"; //面包屑导航
import Hamburger from "@/components/Hamburger"; //控制导航菜单折叠
import Screenfull from "@/components/Screenfull"; //全屏
import LangSelect from "@/components/LangSelect"; //语言切换
import { removeToken } from "@/utils/auth";
export default {
  name: "navheader",
  components: { Breadcrumb, Hamburger, Screenfull, LangSelect },
  data() {
    return {
      username: this.$store.getters.name //用户名
    };
  },
  created() {},
  computed: {
    //使用对象展开运算符将getter混入computed对象中
    ...mapGetters(["sidebar"])
  },
  methods: {
    //退出系统
    logout() {
      this.$confirm(this.$t("navbar.logOutNote"), this.$t("table.note"), {
        confirmButtonText: this.$t("table.confirm"),
        cancelButtonText: this.$t("table.cancel"),
        type: "warning"
      })
        .then(() => {
          this.$store
            .dispatch("LogOut")
            .then(() => {
              this.$message.success({
                message: this.$t("navbar.logOutSuccess"),
                center: true,
                duration: 1200,
                onClose: () => {
                  this.$router.push({ path: "/login" });
                }
              });
            })
            .catch(() => {
              removeToken();
              this.$router.push({ path: "/login" });
              this.loading = false;
            });
        })
        .catch(() => {});
    }
  }
};
</script>
