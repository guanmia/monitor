<template>
    <div class='user'>

        <div class="dataForm">
            <el-dialog :title="operate === 0 ? $t('user.createUser') : $t('user.updateUser')" :visible.sync="visible" :closeOnClickModal="false" :beforeClose="handleClose">
                <el-form :model="dataForm" ref="dataForm" :rules="rules" labelWidth="150px">
                    <!-- <el-form-item :label="$t('user.id')" v-show="dataForm.id">
                        <p>{{dataForm.id}}</p>
                    </el-form-item> -->
                    <el-form-item :label="$t('user.username')" prop="username">
                        <el-input v-model="dataForm.username"></el-input>
                    </el-form-item>
                    <el-form-item :label="$t('user.password')" prop="password" v-if='operate === 0'>
                        <el-input v-model="dataForm.password" type='password'></el-input>
                    </el-form-item>
                    <el-form-item :label="$t('user.email')" prop="email">
                        <el-input v-model="dataForm.email"></el-input>
                    </el-form-item>
                    <el-form-item :label="$t('user.phone')" prop="phone">
                        <el-input v-model="dataForm.phone"></el-input>
                    </el-form-item>
                    <el-form-item :label="$t('user.roles')" prop="roles">
                        <el-radio-group v-model="dataForm.roles[0]">
                            <el-radio label="admin">{{$t('user.admin')}}</el-radio>
                            <el-radio label="user">{{$t('user.user')}}</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item :label="$t('user.introduction')" prop="introduction">
                        <el-input type='textarea' v-model='dataForm.introduction' :placeholder="$t('user.enterIntro')" :rows='4'></el-input>
                    </el-form-item>
                    <el-form-item >
                        <el-button type="primary" @click="saveForm" :loading='saveLoading'>{{$t('table.save')}}</el-button>
                        <el-button type="info" @click="resetForm">{{$t('table.cancel')}}</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
        </div>

        <el-dialog :title='detailTitle' :visible.sync='detailVisible'>
          <p>{{detail}}</p>
        </el-dialog>

        <div class="title">
            <h2>{{generateTitle('users')}} <el-tag type="warning">{{$t('user.superAdmin')}} </el-tag></h2>
            <div class="search">
                <el-input v-model="search" :placeholder="$t('user.search')" :title="$t('user.search')" prefixIcon="el-icon-search" @keyup.enter.native="handleFilter" clearable></el-input>
                <el-button v-waves type="primary" @click="handleFilter" icon="el-icon-search">{{$t('table.search')}}</el-button>
                <el-button v-waves type="primary" icon="el-icon-download" :loading='$store.getters.downloadLoading' @click="handleDownload(list[0],list,$t('title')+'-'+generateTitle('users'))">{{$t('table.export')}}</el-button>
            </div>
            <el-button v-waves type="primary" @click="handleCreate" icon="el-icon-plus" v-if='checkRole()'>{{$t('table.add')}}</el-button>
        </div>

        <main class="tableLists">
            <el-table :data="currentTableLists" border style="width:100%" v-loading="loading">
                <!-- <el-table-column prop="id" :label="$t('user.id')" width='60'></el-table-column> -->
                <el-table-column prop="username" :label="$t('user.username')"></el-table-column>
                <el-table-column prop="email" :label="$t('user.email')" min-width="200"></el-table-column>
                <el-table-column prop="phone" :label="$t('user.phone')"></el-table-column>
                <el-table-column prop="roles" :label="$t('user.roles')">
                    <template slot-scope="scope">
                        <el-tag :type="scope.row.roles | rolesFilter">{{scope.row.roles[0] === 'admin' ? $t('user.admin') : $t('user.user')}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="introduction" :label="$t('user.introduction')">
                    <template slot-scope='scope'>
                        <el-button @click='detailClick(scope.row.id)'>{{$t('user.detail')}}</el-button>
                    </template>
                </el-table-column>
                <el-table-column :label="$t('user.actions')" width="300" v-if='checkRole()'>
                    <template slot-scope="scope" v-if="scope.row.username !== 'admin'">
                        <el-button type="primary" size="mini" @click="editRow(scope.row.id)" icon="el-icon-edit">{{$t('table.edit')}}</el-button>
                        <el-button type="danger" size="mini" @click.native.prevent="deleteRow(scope.row.id)" icon="el-icon-delete">{{$t('table.delete')}}</el-button>
                        <el-button type="info" size="mini" @click.native.prevent="resetPwd(scope.row.id)">{{$t('table.resetPwd')}}</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </main>

        <div class="page">
            <el-pagination
                background
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page.sync="listQuery.page"
                :page-sizes="[10,20,50,100]"
                :page-size="listQuery.limit"
                layout="total,sizes,prev,pager,next,jumper"
                :total="total">
            </el-pagination>
        </div>

    </div>
</template>

<style lang='scss'>
.user {
}
</style>

<script>
import rules from "@/base/rules";
import { parseTime } from "@/utils";
import {
  getUserList,
  createUser,
  getUserInfo,
  updateUser,
  resetUserPwd,
  deleteUser
} from "@/api/user";
export default {
  name: "user",
  components: {},
  data() {
    return {
      list: null, //表格数据
      total: null, //数据总数
      //表单数据
      dataForm: {
        id: null,
        username: "",
        password: "",
        email: "",
        phone: "",
        roles: ["user"],
        introduction: ""
      },
      visible: false, //表单显示与隐藏
      editId: 0, //点击修改的是哪个id
      operate: 0, //判断用户是添加（0）还是修改数据（1）
      loading: true, //数据加载
      detailVisible: false, //用户详情的显示与隐藏
      detailTitle: "", //用户详情的标题
      detail: "", //用户详情
      saveLoading: false, //保存按钮加载
      //查询条件
      currentTableLists: [], //前端分页查询数据
      search: "", //用于把字符串转化为对象内容作为请求数据参数
      listQuery: {
        searchkeys: {
          username: []
        },
        page: 1, //当前页
        limit: 10 //一页多少条记录
      },
      //表单验证
      rules: {
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
          }
          //{ validator: rules.passwordRule, trigger: "blur" }
        ],
        email: [
          {
            required: true,
            message: this.$t("user.emailMsg"),
            trigger: "blur"
          },
          { validator: rules.emailRule, trigger: "blur" }
        ],
        phone: [
          {
            required: true,
            message: this.$t("user.phoneMsg"),
            trigger: "blur"
          },
          { validator: rules.phoneRule, trigger: "blur" }
        ]
      }
    };
  },
  filters: {
    rolesFilter(status) {
      const statusMap = {
        admin: "warning",
        user: "info"
      };
      return statusMap[status];
    }
    // rolesFilter1: status => {
    //   const statusMap = {
    //     admin: "管理员",
    //     user: "普通用户"
    //   };
    //   return statusMap[status];
    // }
  },
  created() {
    //获取表格数据
    this.getList();
  },
  methods: {
    //获取数据
    getList() {
      this.loading = true;

      //转化username对象
      // if (this.search !== "") {
      //   let arr = this.search.split(" ");
      //   this.listQuery.searchkeys.username = arr;
      // }

      getUserList({ page: 1, limit: 1000 }).then(res => {
        if (res.error_code === 0) {
          this.list = res.data.results;
          this.total = res.data.total;

          this.tableListsChange();

          //this.loading = false;
        }
      });
    },
    //点击查询
    handleFilter() {
      this.listQuery.page = 1;
      this.tableListsChange();
      //this.getList();
    },
    //每页多少条记录改变
    handleSizeChange(val) {
      this.listQuery.limit = val;
      this.tableListsChange();
      //this.getList();
    },
    //当前页改变
    handleCurrentChange(val) {
      this.listQuery.page = val;
      this.tableListsChange();
      //this.getList();
    },
    //获取当前分页表格数据
    tableListsChange() {
      //转化username正则
      let username = this.search.replace(" ", "|");
      let usernameReg = new RegExp(username);

      this.currentTableLists = this.list.filter((item, index, self) => {
        return (
          index >= this.listQuery.limit * (this.listQuery.page - 1) &&
          index < this.listQuery.limit * this.listQuery.page &&
          usernameReg.test(item.username)
        );
      });
      this.loading = false;
    },
    //点击添加数据
    handleCreate() {
      this.visible = true;
      this.operate = 0;
    },
    //点击更新数据，获取用户信息
    editRow(id) {
      this.editId = id;
      getUserInfo({ id }).then(res => {
        if (res.error_code === 0) {
          this.dataForm = res.data;
          this.operate = 1;
          this.visible = true;
        }
      });
    },
    //保存数据
    saveForm() {
      this.$refs.dataForm.validate(valid => {
        if (this.dataForm.username === "admin") {
          this.$message.warning({
            message: this.$t("user.userNote"),
            center: true
          });
        } else {
          if (valid) {
            //保存按钮加载，禁止用户快速连续点击两次问题
            this.saveLoading = true;

            //把roles=['user'],转化为roles="ROLE_USER"的格式
            let roles = this.dataForm.roles;
            roles = roles.join(",");
            roles = roles.replace("admin", "ROLE_ADMIN");
            this.dataForm.roles = roles.replace("user", "ROLE_USER");

            if (this.operate === 0) {
              //添加数据
              this.dataForm.id = null; //添加不需要id
              createUser(this.dataForm).then(res => {
                if (res.error_code === 0) {
                  this.$notify({
                    title: this.$t("table.success"),
                    message: this.$t("table.createSuccess"),
                    type: "success",
                    duration: 2000
                  });
                  this.getList();
                  this.resetForm();
                }
              });
            } else if (this.operate === 1) {
              //更新数据
              this.dataForm.password = null; //更新不需要password
              updateUser(this.dataForm).then(res => {
                if (res.error_code === 0) {
                  this.$notify({
                    title: this.$t("table.success"),
                    message: this.$t("table.updateSuccess"),
                    type: "success",
                    duration: 2000
                  });
                  this.getList();
                  this.resetForm();
                }
              });
            }
          }
        }
      });
    },
    //删除数据
    deleteRow(id) {
      this.$confirm(this.$t("table.deleteNote"), this.$t("table.note"), {
        confirmButtonText: this.$t("table.delete"),
        cancelButtonText: this.$t("table.cancel"),
        type: "warning"
      })
        .then(() => {
          deleteUser({ id }).then(res => {
            if (res.error_code === 0) {
              console.log(res);
              this.$notify({
                title: this.$t("table.success"),
                message: this.$t("table.deleteSuccess"),
                type: "success",
                duration: 2000
              });
              this.getList();
            }
          });
        })
        .catch(() => {});
    },
    //点击显示用户详情
    detailClick(id) {
      this.list.forEach(item => {
        if (item.id === id) {
          this.detailVisible = true;
          this.detailTitle = item.username + "的用户详情";
          this.detail = item.introduction;
        }
      });
    },
    //重置用户密码
    resetPwd(id) {
      this.$confirm(this.$t("table.resetNote"), this.$t("table.note"), {
        confirmButtonText: this.$t("table.reset"),
        cancelButtonText: this.$t("table.cancel"),
        type: "warning"
      })
        .then(() => {
          resetUserPwd({ id, password: "000000" }).then(res => {
            if (res.error_code === 0) {
              this.$notify({
                title: this.$t("table.success"),
                message: this.$t("table.resetSuccess"),
                type: "success",
                duration: 2000
              });
            }
          });
        })
        .catch(() => {});
    },
    //关闭表单，并重置表单
    handleClose() {
      this.resetForm();
    },
    //重置并退出
    resetForm() {
      setTimeout(() => {
        //解决移除校验结果，不能解决第一次点击编辑，初始化问题
        this.$refs.dataForm.resetFields();
        //解决第一次点击编辑，初始化问题
        this.dataForm = {
          id: null,
          username: "",
          password: "",
          email: "",
          phone: "",
          roles: ["user"],
          introduction: ""
        };
      }, 500);
      this.visible = false;
      //保存按钮启动
      this.saveLoading = false;
    }
  }
};
</script>
