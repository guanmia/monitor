<template>
    <div class='region'>

        <div class="dataForm">
            <el-dialog  :title="operate === 0 ? $t('region.createRegion') : $t('region.updateRegion')" :visible.sync="visible" :closeOnClickModal="false" :beforeClose="handleClose">
                <el-form :model="dataForm" ref="dataForm" :rules="rules" labelWidth="150px">
                    <el-form-item :label="$t('region.id')" prop="id">
                        <el-input v-model="dataForm.id" v-if="operate === 0"></el-input>
                        <p v-else>{{dataForm.id}}</p>
                    </el-form-item>
                    <el-form-item :label="$t('region.regionName')" prop="regionName">
                        <el-input v-model="dataForm.regionName"></el-input>
                    </el-form-item>
                    <el-form-item :label="$t('region.location')" prop="location">
                        <el-cascader :options="regionOptions" v-model="dataForm.location" @change="addressChange" clearable></el-cascader>
                    </el-form-item>
                    <el-form-item :label="$t('region.dbIp')" prop="dbIp">
                        <el-input v-model="dataForm.dbIp"></el-input>
                    </el-form-item>
                    <el-form-item :label="$t('region.dbPort')" prop="dbPort">
                        <el-input v-model="dataForm.dbPort"></el-input>
                    </el-form-item>
                    <el-form-item :label="$t('region.msIp')" prop="msIp">
                        <el-input v-model="dataForm.msIp"></el-input>
                    </el-form-item>
                    <el-form-item :label="$t('region.msPort')" prop="msPort">
                        <el-input v-model="dataForm.msPort"></el-input>
                    </el-form-item>
                    <el-form-item :label="$t('region.status')" prop="status" v-if='operate === 1'>
                        <el-radio-group v-model="dataForm.status">
                            <el-radio :label="1">{{$t('region.use')}}</el-radio>
                            <el-radio :label="2">{{$t('region.maintain')}}</el-radio>
                            <el-radio :label="3">{{$t('region.nonuse')}}</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="saveForm" :loading='saveLoading'>{{$t('table.save')}}</el-button>
                        <el-button type="info" @click="resetForm">{{$t('table.cancel')}}</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
        </div>

        <div class="title">
            <h2>{{generateTitle('region')}}</h2>
            <div class="search">
                <!-- <el-input v-model="search" :placeholder="$t('region.search')" :title="$t('region.search')" prefixIcon="el-icon-search" @keyup.enter.native="handleFilter" clearable></el-input>
                <el-button v-waves type="primary" @click="handleFilter" icon="el-icon-search">{{$t('table.search')}}</el-button> -->
                <el-button v-waves type="primary" icon="el-icon-download" :loading='$store.getters.downloadLoading' @click="handleDownload(list[0],list,$t('title')+'-'+generateTitle('region'))">{{$t('table.export')}}</el-button>
            </div>
            <el-button v-waves type="primary" @click="handleCreate" icon="el-icon-plus" v-if='this.checkRole()'>{{$t('table.add')}}</el-button>
        </div>

        <main class="lists">
            <el-table :data="list" border style="width:100%" v-loading="loading">
                <el-table-column prop="id" :label="$t('region.id')" width='80'></el-table-column>
                <el-table-column prop="regionName" :label="$t('region.regionName')"></el-table-column>
                <!-- <el-table-column :label="$t('region.location')" width="200">
                    <template slot-scope="scope">
                        <span>{{scope.row.location[0]}} {{scope.row.location[1]}} {{scope.row.location[2]}}</span>
                        <span>{{scope.row.regionStreet}}</span>
                    </template>
                </el-table-column> -->
                <el-table-column prop="location" :label="$t('region.location')">
                  <template slot-scope='scope'>
                    {{scope.row.location[0] ? scope.row.location[0].label : ""}} 
                    {{scope.row.location[1] ? scope.row.location[1].label : ""}} 
                    {{scope.row.location[2] ? scope.row.location[2].label : ""}} 
                  </template>
                </el-table-column>
                <el-table-column prop="dbIp" :label="$t('region.dbIp')"></el-table-column>
                <el-table-column prop="dbPort" :label="$t('region.dbPort')"></el-table-column>
                <el-table-column prop="msIp" :label="$t('region.msIp')"></el-table-column>
                <el-table-column prop="msPort" :label="$t('region.msPort')"></el-table-column>
                <el-table-column prop="clientNum" :label="$t('region.clientNum')">
                    <template slot-scope="scope">
                        <router-link to="/center/client">
                            <el-button type="text">{{scope.row.clientNum}}</el-button>
                        </router-link>
                    </template>
                </el-table-column>
                <!-- <el-table-column prop="storageAgencyNum" :label="$t('region.storageAgencyNum')">
                    <template slot-scope="scope">
                        <router-link :to="{name: 'storage-agency',params: {region_id: scope.row.id}}">
                            <el-button type="text">{{scope.row.storageAgencyNum}}</el-button>
                        </router-link>
                    </template>
                </el-table-column> -->
                <!--<el-table-column prop="storageServerNum" label="存储服务器数量"></el-table-column>-->
                <el-table-column prop="status" :label="$t('region.status')">
                    <template slot-scope="scope">
                      <el-tag :type="scope.row.status | statusFilter">{{scope.row.status === 1 ? $t('region.use') : (scope.row.status === 2 ? $t('region.maintain') : $t('region.nonuse'))}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column :label="$t('region.actions')" width="200" v-if='checkRole()'>
                    <template slot-scope="scope">
                        <el-button type="primary" size="mini" @click="editRow(scope.row.id)" icon="el-icon-edit">{{$t('table.edit')}}</el-button>
                        <el-button type="danger" size="mini" @click.native.prevent="deleteRow(scope.row.id)" icon="el-icon-delete">{{$t('table.delete')}}</el-button>
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
.region {
}
</style>

<script>
import rules from "@/base/rules";
import {
  getRegionList,
  createRegion,
  getRegionInfo,
  updateRegion,
  deleteRegion,
  getLocation
} from "@/api/region";
export default {
  name: "region",
  components: {},
  data() {
    return {
      //表格数据
      list: [], //表格数据
      total: null, //数据总数
      locationList: [], //区域地址数据
      //表单数据
      dataForm: {
        id: null,
        regionName: "",
        location: [],
        location_id: null,
        dbIp: "",
        dbPort: "",
        msIp: "",
        msPort: "",
        status: 1
      },
      visible: false, //表单显示与隐藏
      regionOptions: [], //中国省市区三级联级联选择器
      editIndex: 0, //点击修改的是哪个索引
      operate: 0, //判断用户是添加（0）还是修改数据（1）
      loading: true, //数据加载
      saveLoading: false, //保存按钮的加载
      //查询条件
      search: "", //用于把字符串转化为对象内容作为请求数据参数
      listQuery: {
        // searchkeys: {
        //   regionName: []
        // },
        page: 1, //当前页
        limit: 20 //一页多少条记录
      },
      //表单验证
      rules: {
        id: [
          {
            required: true,
            message: this.$t("region.idMsg"),
            trigger: "blur"
          },
          { validator: rules.idRule, trigger: "blur" }
        ],
        regionName: [
          {
            required: true,
            message: this.$t("region.regionNameMsg"),
            trigger: "blur"
          }
        ],
        location: [
          {
            required: true,
            message: this.$t("region.locationMsg"),
            trigger: "blur"
          }
        ],
        //regionStreet: [{ required: true, message: "详细地址不能为空！", trigger: "blur" }],
        dbIp: [
          {
            required: true,
            message: this.$t("region.dbIpMsg"),
            trigger: "blur"
          },
          { validator: rules.ipRule, trigger: "blur" }
        ],
        dbPort: [
          {
            required: true,
            message: this.$t("region.dbPortMsg"),
            trigger: "blur"
          },
          { validator: rules.portRule, trigger: "blur" }
        ],
        msIp: [
          {
            required: true,
            message: this.$t("region.msIpMsg"),
            trigger: "blur"
          },
          { validator: rules.ipRule, trigger: "blur" }
        ],
        msPort: [
          {
            required: true,
            message: this.$t("region.msPortMsg"),
            trigger: "blur"
          },
          { validator: rules.portRule, trigger: "blur" }
        ]
      }
    };
  },
  directives: {
    //输入数字管道
    numberOnly: {
      bind(el) {
        this.handler = () => {
          el.value = el.value.replace(/\D+/, "");
        };
        el.addEventListener("input", el.handler);
      },
      unbind(el) {
        el.removeEventListener("input", el.handler);
      }
    }
  },
  filters: {
    statusFilter(status) {
      const statusMap = {
        1: "success",
        2: "warning",
        3: "danger"
      };
      return statusMap[status];
    },
    statusFilter1(status) {
      //console.log(this.loading);
      const statusMap = {
        1: "使用",
        2: "维护",
        3: "停用"
      };
      return statusMap[status];
    }
  },
  created() {
    //获取表格数据
    this.getList();
  },
  methods: {
    //获取中国省市区三级联级联选择器格式化
    getLocations(locations) {
      for (let x of locations) {
        //获取省份
        let city = [];
        for (let y of x.children) {
          //获取城市
          let area = [];
          for (let z of y.children) {
            //获取地区
            area.push({ value: z.location_id, label: z.location });
          }
          city.push({
            value: y.location_id,
            label: y.location,
            //children: area
          });
        }
        this.regionOptions.push({
          value: x.location_id,
          label: x.location,
          children: city
        });
      }
    },
    //三级联地址改变
    addressChange(value) {
      this.dataForm.location = value;
    },
    //获取全部省市区域
    getLocation(list) {
      getLocation().then(res => {
        if (res.error_code === 0) {
          this.locationList = res.data;

          this.getLocations(this.locationList);

          for (let item of list) {
            item.location = [];
            //省份
            for (let item1 of this.locationList) {
              if (item.location_id === item1.location_id) {
                item.location.push({
                  value: item1.location_id,
                  label: item1.location
                });
              } else {
                //城市
                for (let item2 of item1.children) {
                  if (item.location_id === item2.location_id) {
                    item.location.push(
                      {
                        value: item1.location_id,
                        label: item1.location
                      },
                      {
                        value: item2.location_id,
                        label: item2.location
                      }
                    );
                  } else {
                    //区域
                    for (let item3 of item2.children) {
                      if (item.location_id === item3.location_id) {
                        //item.location.push(item1.location, item2.location, item3.location);
                        item.location.push(
                          {
                            value: item1.location_id,
                            label: item1.location
                          },
                          {
                            value: item2.location_id,
                            label: item2.location
                          },
                          {
                            value: item3.location_id,
                            label: item3.location
                          }
                        );
                      }
                    }
                  }
                }
              }
            }
          }
          //console.log(list[0].location);

          this.list = list;
        }
      });
    },
    //获取数据
    getList() {
      this.loading = true;

      //转化regionName对象
      if (this.search !== "") {
        let arr = this.search.split(" ");
        this.listQuery.searchkeys.regionName = arr;
      }

      getRegionList(this.listQuery).then(res => {
        if (res.error_code === 0) {
          let list = res.data.results;
          //this.list = list;

          this.getLocation(list);

          this.total = res.data.total;
          this.loading = false;
        }
      });
    },
    //点击查询
    handleFilter() {
      this.listQuery.page = 1;
      this.getList();
    },
    //每页多少条记录改变
    handleSizeChange(val) {
      this.listQuery.limit = val;
      this.getList();
    },
    //当前页改变
    handleCurrentChange(val) {
      this.listQuery.page = val;
      this.getList();
    },
    //点击添加数据
    handleCreate() {
      this.visible = true;
      this.operate = 0;
    },
    //点击更新数据，获取区域信息
    editRow(id) {
      this.editIndex = id;
      getRegionInfo({ id }).then(res => {
        if (res.error_code === 0) {
          this.dataForm = res.data;

          //设置location
          this.dataForm.location = [];
          this.list.forEach(item => {
            if (this.dataForm.location_id === item.location_id) {
              item.location.forEach(item => {
                this.dataForm.location.push(item.value);
              });
            }
          });
          console.log(this.dataForm);

          this.operate = 1;
          this.visible = true;
        }
      });
    },
    //保存数据
    saveForm() {
      this.$refs.dataForm.validate(valid => {
        if (valid) {
          //保存按钮的加载，禁止用户快速连续点击两次问题
          this.saveLoading = true;

          //把location转换成location_id
          this.dataForm.location_id = this.dataForm.location[
            this.dataForm.location.length - 1
          ];
          //console.log(this.dataForm);

          if (this.operate === 0) {
            //添加数据
            createRegion(this.dataForm).then(res => {
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
            updateRegion(this.dataForm).then(res => {
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
          deleteRegion({ id }).then(res => {
            if (res.error_code === 0) {
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
          regionName: "",
          location: [],
          location_id: null,
          dbIp: "",
          dbPort: "",
          msIp: "",
          msPort: "",
          status: 1
        };
      }, 500);
      this.visible = false;
      //保存按钮启动
      this.saveLoading = false;
    }
  }
};
</script>
