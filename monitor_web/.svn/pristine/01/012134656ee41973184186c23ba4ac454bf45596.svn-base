<template>
    <div class='client'>

        <div class="dataForm">
            <el-dialog :title="operateTitle" :visible.sync="visible" :closeOnClickModal="false" :beforeClose="handleClose">

                <!--添加/修改客户端-->
                <el-form :model="dataForm" ref="dataForm" :rules="rules" labelWidth="200px" v-if="operate === 0 || operate === 1">
                    <!-- <el-form-item :label="$t('client.id')" v-show="dataForm.id">
                        <p>{{dataForm.id}}</p>
                    </el-form-item> -->
                    <el-form-item :label="$t('client.site_id')" prop="site_id">
                      <el-input v-model="dataForm.site_id" v-if="operate === 0" clearable></el-input>
                      <p v-else>{{dataForm.site_id}}</p>
                    </el-form-item>
                    <el-form-item :label="$t('client.regionName')" prop="region_center_id">
                        <el-select v-model="dataForm.region_center_id" clearable>
                            <el-option v-for="item in regionNameLists" :label="item.name" :key="item.id" :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item :label="$t('client.clientName')" prop="clientName">
                        <el-input v-model="dataForm.clientName" clearable></el-input>
                    </el-form-item>
                    <el-form-item :label="$t('client.location')" prop="location">
                        <el-cascader :options="regionOptions" v-model="dataForm.location" @change="addressChange" clearable></el-cascader>
                    </el-form-item>
                    <el-form-item :label="$t('client.street')" prop="street">
                        <el-input v-model="dataForm.street" clearable></el-input>
                    </el-form-item>

                    <el-form-item :label="$t('client.transfer_time')" prop="transfer_time">
                        <el-input-number v-model="dataForm.transfer_time" :min="0" :max="60*60*24*365" clearable></el-input-number>
                    </el-form-item>
                    <el-form-item :label="$t('client.clear_time')" prop="clear_time">
                        <el-input-number v-model="dataForm.clear_time" :min="0" :max="60*60*24*365" clearable></el-input-number>
                    </el-form-item>
                    <el-form-item :label="$t('client.heartbeat_interval')" prop="heartbeat_interval">
                        <el-input-number v-model="dataForm.heartbeat_interval" :min="0" :max="60*60*24*365" clearable></el-input-number>
                    </el-form-item>
                    <el-form-item :label="$t('client.upload_scan_interval')" prop="upload_scan_interval">
                        <el-input-number v-model="dataForm.upload_scan_interval" :min="0" :max="60*60*24*365" clearable></el-input-number>
                    </el-form-item>
                    <el-form-item :label="$t('client.clear_scan_interval')" prop="clear_scan_interval">
                        <el-input-number v-model="dataForm.clear_scan_interval" :min="0" :max="60*60*24*365" clearable></el-input-number>
                    </el-form-item>
                    <el-form-item :label="$t('client.console_log_level')" prop="console_log_level">
                        <el-select v-model="dataForm.console_log_level" clearable>
                            <el-option v-for="item in log_levels" :label="item" :key="item" :value="item"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item :label="$t('client.file_log_level')" prop="file_log_level">
                        <el-select v-model="dataForm.file_log_level" clearable>
                            <el-option v-for="item in log_levels" :label="item" :key="item" :value="item"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item :label="$t('client.is_log_to_server')" prop="is_log_to_server">
                        <el-radio-group v-model="dataForm.is_log_to_server">
                          <el-radio :label="'true'">是</el-radio>
                          <el-radio :label="'false'">否</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item :label="$t('client.log_server_ip')" prop="log_server_ip" v-if="dataForm.is_log_to_server === 'true'">
                        <el-input v-model="dataForm.log_server_ip" clearable></el-input>
                    </el-form-item>
                    <el-form-item :label="$t('client.log_server_port')" prop="log_server_port" v-if="dataForm.is_log_to_server === 'true'">
                        <el-input v-model="dataForm.log_server_port" clearable></el-input>
                    </el-form-item>
                    <el-form-item :label="$t('client.upload_point_in_time')" prop="upload_point_in_time">
                        <el-time-picker v-model="dataForm.upload_point_in_time" value-format="HH:mm:ss"></el-time-picker>
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" @click="saveForm" :loading='saveLoading'>{{$t('table.save')}}</el-button>
                        <el-button type="info" @click="resetForm">{{$t('table.cancel')}}</el-button>
                    </el-form-item>
                </el-form>

                <!--批量添加客户端-->
                <el-form :model="dataForm" ref="dataForm" :rules="rules" labelWidth="150px" v-else-if="operate === 2" class="upload-file">
                    <el-form-item :placeholder="$t('client.regionName')" prop="region_center_id">
                        <el-select v-model="dataForm.region_center_id" :placeholder="$t('client.regionName')" clearable>
                            <el-option v-for="item in regionNameLists" :label="item.name" :key="item.id" :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item prop="file">
                        <el-upload
                            class="upload-demo"
                            drag
                            action="https://jsonplaceholder.typicode.com/posts/"
                            :auto-upload="true"
                            :limit="1"
                            :on-exceed="handleExceed"
                            :before-upload="beforeUpload"
                            :on-change="handleChange"
                            :on-success="hangleSuccess"
                            multiple>
                            <i class="el-icon-upload"></i>
                            <div class="el-upload__text" v-html="$t('client.uploadText')"></div>
                            <div class="el-upload__tip" slot="tip" v-html="$t('client.uploadTip')"></div>
                        </el-upload>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="saveForm" :loading='saveLoading'>{{$t('table.save')}}</el-button>
                        <el-button type="info" @click="resetForm">{{$t('table.cancel')}}</el-button>
                    </el-form-item>
                </el-form>

                <!--批量修改客户端配置-->
                <el-form :model="dataForm" ref="dataForm" :rules="rules" labelWidth="200px" v-else-if="operate === 3">
                    <el-form-item :label="$t('client.regionName')" prop="region_center_id">
                        <el-select v-model="dataForm.region_center_id" clearable>
                            <el-option v-for="item in regionNameLists" :label="item.name" :key="item.id" :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                    
                    <el-form-item :label="$t('client.transfer_time')" prop="transfer_time">
                        <el-input-number v-model="dataForm.transfer_time" :min="0" :max="60*60*24*365" clearable></el-input-number>
                    </el-form-item>
                    <el-form-item :label="$t('client.clear_time')" prop="clear_time">
                        <el-input-number v-model="dataForm.clear_time" :min="0" :max="60*60*24*365" clearable></el-input-number>
                    </el-form-item>
                    <el-form-item :label="$t('client.heartbeat_interval')" prop="heartbeat_interval">
                        <el-input-number v-model="dataForm.heartbeat_interval" :min="0" :max="60*60*24*365" clearable></el-input-number>
                    </el-form-item>
                    <el-form-item :label="$t('client.upload_scan_interval')" prop="upload_scan_interval">
                        <el-input-number v-model="dataForm.upload_scan_interval" :min="0" :max="60*60*24*365" clearable></el-input-number>
                    </el-form-item>
                    <el-form-item :label="$t('client.clear_scan_interval')" prop="clear_scan_interval">
                        <el-input-number v-model="dataForm.clear_scan_interval" :min="0" :max="60*60*24*365" clearable></el-input-number>
                    </el-form-item>
                    <el-form-item :label="$t('client.console_log_level')" prop="console_log_level">
                        <el-input v-model="dataForm.console_log_level" clearable></el-input>
                    </el-form-item>
                    <el-form-item :label="$t('client.file_log_level')" prop="file_log_level">
                        <el-input v-model="dataForm.file_log_level" clearable></el-input>
                    </el-form-item>
                    <el-form-item :label="$t('client.is_log_to_server')" prop="is_log_to_server">
                        <el-radio-group v-model="dataForm.is_log_to_server">
                          <el-radio :label="'true'">是</el-radio>
                          <el-radio :label="'false'">否</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item :label="$t('client.log_server_ip')" prop="log_server_ip">
                        <el-input v-model="dataForm.log_server_ip" clearable></el-input>
                    </el-form-item>
                    <el-form-item :label="$t('client.log_server_port')" prop="log_server_port">
                        <el-input v-model="dataForm.log_server_port" clearable></el-input>
                    </el-form-item>
                    <el-form-item :label="$t('client.upload_point_in_time')" prop="upload_point_in_time">
                        <el-time-picker v-model="dataForm.upload_point_in_time"></el-time-picker>
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" @click="saveForm" :loading='saveLoading'>{{$t('table.save')}}</el-button>
                        <el-button type="info" @click="resetForm">{{$t('table.cancel')}}</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
        </div>
        
        <el-dialog class="config" title='配置记录' :visible.sync='configVisible'>
          <table border="1">
            <tr>
              <!-- <th>{{$t('client.site_id')}}</th> -->
              <th>{{$t('client.regionName')}}</th>
              <th>{{$t('client.clientName')}}</th>
              <th>{{$t('client.location')}}</th>
              <th>{{$t('client.addTime')}}</th>
              <th>{{$t('client.newConfigTime')}}</th>
              <th>{{$t('client.status')}}</th>
            </tr>
            <tr v-for="config in configList">
              <!-- <td>{{config.site_id}}</td> -->
              <td>{{config.regionName}}</td>
              <td>{{config.clientName}}</td>
              <td>
                {{config.location[0] ? config.location[0].label : ""}} 
                {{config.location[1] ? config.location[1].label : ""}} 
                {{config.location[2] ? config.location[2].label : ""}} 
              </td>
              <td>{{config.addTime}}</td>
              <td>{{config.newConfigTime}}</td>
              <td>{{config.status}}</td>
            </tr>
          </table>
        </el-dialog>

        <el-dialog class="moreDetail" :title="moreDetailData.regionName + '的配置详情'" :visible.sync='moreDetail'>
          <el-form :model="moreDetailData" labelWidth="250px">
              <el-form-item :label="$t('client.transfer_time')+'：'">
                <p>{{moreDetailData.transfer_time}}</p>
              </el-form-item>
              <el-form-item :label="$t('client.clear_time')+'：'">
                <p>{{moreDetailData.clear_time}}</p>
              </el-form-item>
              <el-form-item :label="$t('client.heartbeat_interval')+'：'">
                <p>{{moreDetailData.heartbeat_interval}}</p>
              </el-form-item>
              <el-form-item :label="$t('client.upload_scan_interval')+'：'">
                <p>{{moreDetailData.upload_scan_interval}}</p>
              </el-form-item>
              <el-form-item :label="$t('client.clear_scan_interval')+'：'">
                <p>{{moreDetailData.clear_scan_interval}}</p>
              </el-form-item>
              <el-form-item :label="$t('client.console_log_level')+'：'">
                <p>{{moreDetailData.console_log_level}}</p>
              </el-form-item>
              <el-form-item :label="$t('client.file_log_level')+'：'">
                <p>{{moreDetailData.file_log_level}}</p>
              </el-form-item>
              <el-form-item :label="$t('client.is_log_to_server')+'：'">
                <p>{{moreDetailData.is_log_to_server}}</p>
              </el-form-item>
              <el-form-item :label="$t('client.log_server_ip')+'：'" v-if="moreDetailData.is_log_to_server === 'true'">
                <p>{{moreDetailData.log_server_ip}}</p>
              </el-form-item>
              <el-form-item :label="$t('client.log_server_port')+'：'" v-if="moreDetailData.is_log_to_server === 'true'">
                <p>{{moreDetailData.log_server_port}}</p>
              </el-form-item>
              <el-form-item :label="$t('client.upload_point_in_time')+'：'">
                <p>{{moreDetailData.upload_point_in_time}}</p>
              </el-form-item>
              <el-form-item :label="$t('client.client_ver')+'：'">
                <p>{{moreDetailData.client_ver}}</p>
              </el-form-item>
              <el-form-item :label="$t('client.config_ver')+'：'">
                <p>{{moreDetailData.config_ver}}</p>
              </el-form-item>
          </el-form>
        </el-dialog>

        <div class="title">
            <h2>{{generateTitle('client')}}</h2>
            <div class="search">
                <el-input v-model="search" :placeholder="$t('client.search')" :title="$t('client.search')" prefixIcon="el-icon-search" @keyup.enter.native="handleFilter" clearable></el-input>
                <el-select v-model="listQuery.searchkeys.regionids" clearable multiple collapse-tags :placeholder="$t('client.chooseRegion')">
                    <el-option v-for="item in regionNameLists" :label="item.name" :key="item.id" :value="item.id"></el-option>
                </el-select>
                <el-button type="primary" @click="handleFilter" icon="el-icon-search">{{$t('table.search')}}</el-button>
                <el-button type="primary" icon="el-icon-download" :loading='$store.getters.downloadLoading' @click="handleDownload(list[0],list,$t('title')+'-'+generateTitle('client'))">{{$t('table.export')}}</el-button>
            </div>
            <el-button type="primary" @click="handleCreate" icon="el-icon-plus" v-if='checkRole()'>{{$t('table.add')}}</el-button>
            <el-button type="primary" @click="addBatchRow" icon="el-icon-plus" v-if='checkRole()'>{{$t('client.createBatch')}}</el-button>
            <el-button type="primary" @click="editBatchRow" icon="el-icon-edit-outline" v-if='checkRole()'>{{$t('client.updateBatch')}}</el-button>
        </div>

        <main class="list">
            <el-table :data="list" border style="width:100%" @selection-change="handleSelectionChange" v-loading="loading">

                <el-table-column type="selection"></el-table-column>
                <el-table-column prop="site_id" :label="$t('client.site_id')" width='80'></el-table-column>

                <el-table-column prop="regionName" :label="$t('client.regionName')"></el-table-column>
                <el-table-column prop="clientName" :label="$t('client.clientName')"></el-table-column>
                <!-- <el-table-column prop="clientAddress" label="客户端位置" width="200">
                    <template slot-scope="scope">
                        <span>{{scope.row.clientAddress[0]}} {{scope.row.clientAddress[1]}} {{scope.row.clientAddress[2]}}</span>
                        <span>{{scope.row.clientStreet}}</span>
                    </template>
                </el-table-column> -->
                <el-table-column prop="location" :label="$t('client.location')">
                  <template slot-scope='scope'>
                    {{scope.row.location[0] ? scope.row.location[0].label : ""}} 
                    {{scope.row.location[1] ? scope.row.location[1].label : ""}} 
                    {{scope.row.location[2] ? scope.row.location[2].label : ""}} 
                  </template>
                </el-table-column>
                <el-table-column prop="addTime" :label="$t('client.addTime')">
                    <template slot-scope="scope">
                        <!-- <span>{{new Date(scope.row.addTime).toLocaleString()}}</span> -->
                        <span>{{scope.row.addTime}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="newConfigTime" :label="$t('client.newConfigTime')"></el-table-column>
                <el-table-column prop="status" :label="$t('client.status')">
                    <template slot-scope="scope">
                        <p>文件数量： <span class='blue'>{{scope.row.status.file_num}}</span></p>
                    </template>
                </el-table-column>

                <!-- <el-table-column prop="transfer_time" :label="$t('client.transfer_time')"></el-table-column>
                <el-table-column prop="clear_time" :label="$t('client.clear_time')"></el-table-column>
                <el-table-column prop="heartbeat_interval" :label="$t('client.heartbeat_interval')"></el-table-column>
                <el-table-column prop="upload_scan_interval" :label="$t('client.upload_scan_interval')"></el-table-column>
                <el-table-column prop="clear_scan_interval" :label="$t('client.clear_scan_interval')"></el-table-column>
                <el-table-column prop="console_log_level" :label="$t('client.console_log_level')"></el-table-column>
                <el-table-column prop="file_log_level" :label="$t('client.file_log_level')"></el-table-column>
                <el-table-column prop="is_log_to_server" :label="$t('client.is_log_to_server')"></el-table-column>
                <el-table-column prop="log_server_ip" :label="$t('client.log_server_ip')"></el-table-column>
                <el-table-column prop="log_server_port" :label="$t('client.log_server_port')"></el-table-column>
                <el-table-column prop="upload_point_in_time" :label="$t('client.upload_point_in_time')"></el-table-column>
                <el-table-column prop="client_ver" :label="$t('client.client_ver')"></el-table-column>
                <el-table-column prop="config_ver" :label="$t('client.config_ver')"></el-table-column> -->
                
                <el-table-column :label="$t('client.actions')" width="240">
                  <template slot-scope="scope">
                      <div class="action">
                        <el-button type="success" size="mini" @click="moreDetailFn(scope.row.id)" icon="el-icon-view">详细配置</el-button> 
                        <el-button type="danger" size="mini" @click.native.prevent="deleteRow(scope.row.id)" icon="el-icon-delete" v-if='checkRole()'>{{$t('table.delete')}}</el-button>
                      </div>
                      <div class="action">
                        <el-button type="success" size="mini" @click="viewConfig(scope.row.site_id)" icon="el-icon-view">{{$t('client.viewConfig')}}</el-button>
                        <el-button type="primary" size="mini" @click="editRow(scope.row.id)" icon="el-icon-edit" v-if='checkRole()'>{{$t('table.edit')}}</el-button>
                      </div>
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
.client {
  .config {
    .el-dialog {
      width: 80%;
    }
    table {
      width: 100%;
      text-align: center;
      border: 1px solid #eee;
      tr {
        th {
          height: 40px;
          text-align: center;
        }
        td {
          padding: 10px 0;
        }
      }
    }
  }
  //更多详细配置
  .moreDetail {
    .el-dialog {
      width: 600px;
      .el-form-item {
        margin-bottom: 5px;
      }
    }
  }

  .action {
    margin-bottom: 8px;
    display: flex;
    justify-content: center;
  }
  .upload-file {
    .el-button {
    }
  }
}
</style>

<script>
import { getRegionList, getLocation } from "@/api/region";
import rules from "@/base/rules";

import {
  getClientList,
  createClient,
  createBatchClient,
  getClientInfo,
  updateClient,
  updateBatchClient,
  deleteClient,
  getSite,
  getStatus
} from "@/api/client";
export default {
  name: "client",
  components: {},
  data() {
    return {
      //表格数据
      list: [], //表格数据
      total: null, //数据总数
      //表单数据
      dataForm: {
        id: null,
        site_id: null,
        //regionName: "",
        region_center_id: null,
        clientName: "",
        location: [],
        location_id: null,
        street: "",
        transfer_time: null,
        clear_time: null,
        heartbeat_interval: null,
        upload_scan_interval: null,
        clear_scan_interval: null,
        console_log_level: "",
        file_log_level: "",
        is_log_to_server: "true",
        log_server_ip: "",
        log_server_port: null,
        upload_point_in_time: "",
        file: null
      },
      visible: false, //表单显示与隐藏
      regionOptions: [], //中国省市区三级联级联选择器
      regionNameLists: [], //所有区域名称
      multipleSelection: [], //批量选择表单数据
      clientids: [], //获取需要修改数据的id
      selectclientName: [], //选中区域名称的数组
      operate: 0, //判断用户是添加（0）,修改数据（1）,批量添加数据（2），批量修改数据（3）
      loading: true, //数据加载
      saveLoading: false, //保存按钮的加载
      configList: [], //配置记录列表
      configVisible: false, //配置记录弹框
      moreDetail: false, //是否显示更多详情，true为不显示
      moreDetailData: {}, //详情配置
      log_levels: ["DEBUG","INFO","WARNING","ERROR","CRITICAL"],
      //查询条件
      search: "", //用于把字符串转化为对象内容作为请求数据参数
      listQuery: {
        searchkeys: {
          clientnames: [],
          regionids: []
        },
        page: 1, //当前页
        limit: 20 //一页多少条记录
      },
      rules: {
        site_id: [
          {
            required: true,
            message: this.$t("client.site_idMsg"),
            trigger: "blur"
          }
        ],
        region_center_id: [
          {
            required: true,
            message: this.$t("region.regionNameMsg"),
            trigger: "change"
          }
        ],
        clientName: [
          {
            required: true,
            message: this.$t("client.clientNameMsg"),
            trigger: "blur"
          }
        ],
        location: [
          {
            required: true,
            message: this.$t("client.locationMsg"),
            trigger: "change"
          }
        ],
        log_server_ip: [
          {
            required: true,
            message: this.$t("client.log_server_ipMsg"),
            trigger: "blur"
          },
          {
            validator: rules.ipRule,
            message: this.$t("region.ipRule"),
            trigger: "blur"
          }
        ],
        log_server_port: [
          {
            required: true,
            message: this.$t("client.log_server_portMsg"),
            trigger: "blur"
          },
          {
            validator: rules.portRule,
            message: this.$t("region.portRule"),
            trigger: "blur"
          }
        ],
        file: [
          {
            required: true,
            message: this.$t("client.fileMsg"),
            trigger: "change"
          }
        ]
      }
    };
  },
  computed: {
    //用户操作
    operateTitle() {
      switch (this.operate) {
        case 0:
          return this.$t("client.createClient");
          break;
        case 1:
          return this.$t("client.updateClient");
          break;
        case 2:
          return this.$t("client.createBatch");
          break;
        case 3:
          return this.$t("client.updateBatch");
          break;
        default:
          break;
      }
    }
  },
  created() {
    //获取所有区域名称
    this.getRegionNameLists();
    //获取表格数据
    this.getList();
  },
  methods: {
    //获取所有区域名称
    getRegionNameLists() {
      getRegionList({ page: 1, limit: 100 }).then(res => {
        if (res.error_code === 0) {
          let list = res.data.results;

          list.forEach(item => {
            this.regionNameLists.push({
              id: item.id,
              name: item.regionName
            });
          });
        }
      });
    },
    //上传文件
    //文件超过个数限制时的钩子
    handleExceed(files, fileList) {
      //files:当前选中的文件集合，fileList：当前选中超出限制个数的文件
      this.$message.warning({
        message: `只能选择 ${files.length} 个文件`,
        center: true
      });
    },
    //上传文件之前的钩子
    beforeUpload(file) {
      let excelType =
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      if (file.type.indexOf(excelType) === -1) {
        this.$message.warning({
          message: this.$t("client.uploadTip1"),
          center: true
        });
        return false;
      }
      if (file.size / 1024 > 500) {
        this.$message.warning({
          message: this.$t("client.uploadTip2"),
          center: true
        });
        return false;
      }
      return true;
    },
    //文件状态改变时的钩子，添加文件，上传成功和上传失败时都会被调用
    handleChange(file, fileList) {},
    //文件上传成功时的钩子
    hangleSuccess(res, file) {
      //res:{id: 101}, file:上传的文件
      //let imageUrl = URL.createObjectURL(file.raw);
      this.dataForm.file = file.raw;
    },
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
            label: y.location
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
      this.dataForm.location_id = value;
    },
    //获取全部省市区域--并处理数据列表中的location和location_id
    getLocation(list) {
      return new Promise((resolve, reject) => {
        getLocation()
          .then(res => {
            if (res.error_code == 0) {
              this.locationList = res.data;

              //获取中国省市区三级联级联选择器格式化
              this.getLocations(this.locationList);

              for (let item of list) {
                item.location = [];
                //省份
                for (let item1 of this.locationList) {
                  if (item.location_id == item1.location_id) {
                    //item.location.push(item1.location);
                    item.location.push({
                      value: item1.location_id,
                      label: item1.location
                    });
                  } else {
                    //城市
                    for (let item2 of item1.children) {
                      if (item.location_id == item2.location_id) {
                        //item.location.push(item1.location, item2.location);
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
                          if (item.location_id == item3.location_id) {
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

              resolve(list);
            }
          })
          .catch(error => {
            console.log(error);
          });
      });
    },
    //获取数据
    getList() {
      this.loading = true;

      //let searchkeys = this.listQuery.searchkeys;
      //转化clientnames对象
      if (this.search !== "") {
        let arr = this.search.split(" ");
        this.listQuery.searchkeys.clientnames = arr;
      }

      getClientList(this.listQuery).then(res => {
        if (res.error_code === 0) {
          //判断res.data是否为空对象，不为空时执行，为空时数据初始化
          if (JSON.stringify(res.data) !== "{}") {
            let list = res.data.results;

            //获取全部省市区域--并处理数据列表中的location和location_id
            this.getLocation(list).then(res => {
              //let list = res;
              this.list = res;

              //获取客户端状态--并处理数据列表中的status
              this.getStatus(this.list).then(res => {
                this.list = res;

                //把clear_time和clear_scan_interval的秒转化为天
                this.list.forEach(item => {
                  item.clear_time = Math.floor(
                    item.clear_time / (60 * 60 * 24)
                  );
                  item.clear_scan_interval = Math.floor(
                    item.clear_scan_interval / (60 * 60 * 24)
                  );
                });
              });
            });
            this.total = res.data.total;
          } else {
            this.list = [];
          }

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
    //获取客户端状态--并处理数据列表中的status
    getStatus(list) {
      return new Promise((resolve, reject) => {
        getStatus()
          .then(res => {
            if (res.error_code === 0) {
              let status = res.data;

              list.forEach(item => {
                item.status = { file_num: 0 };
                status.forEach(item1 => {
                  if (item1.site_id == item.site_id) {
                    item.status.file_num = item1.file_num;
                  }
                });
              });

              resolve(list);
            }
          })
          .catch(err => {
            reject(err);
          });
      });
    },
    //更多详细配置
    moreDetailFn(id) {
      this.list.forEach(item => {
        if (item.id === id) {
          this.moreDetailData = item;
        }
      });
      this.moreDetail = !this.moreDetail;
    },
    //点击添加数据
    handleCreate() {
      this.visible = true;
      this.operate = 0;
    },
    //点击编辑，获取客户端信息
    editRow(id) {
      getClientInfo({ id }).then(res => {
        if (res.error_code === 0) {
          this.dataForm = res.data;

          //设置location
          this.dataForm.location = [];

          //把clear_time和clear_scan_interval的秒转化为天
          this.dataForm.clear_time = Math.floor(
            this.dataForm.clear_time / (60 * 60 * 24)
          );
          this.dataForm.clear_scan_interval = Math.floor(
            this.dataForm.clear_scan_interval / (60 * 60 * 24)
          );

          this.list.forEach(item => {
            if (this.dataForm.location_id == item.location_id) {
              item.location.forEach(item => {
                this.dataForm.location.push(item.value);
              });
            }
          });
          //console.log(this.dataForm);

          this.operate = 1;
          this.visible = true;
        }
      });
    },
    //点击查看配置记录
    viewConfig(site_id) {
      getSite({ site_id }).then(res => {
        if (res.error_code === 0) {
          //console.log(Object.keys(res.data).length);

          if (Object.keys(res.data).length !== 0) {
            let configList = res.data.results;

            this.getLocation(configList).then(res => {
              this.configList = res;

              this.configVisible = true;
            });
          } else {
            this.$message.warning({
              message: "暂时没有配置记录！",
              center: true
            });
          }
        }
      });
    },
    //点击批量添加数据
    addBatchRow() {
      this.visible = true;
      this.operate = 2;
    },
    //批量选择表单数据
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //点击批量修改数据
    editBatchRow() {
      //获取需要修改数据的id
      this.clientids = []; //选中清空
      this.selectclientName = []; //选中区域名称的数组清空
      this.multipleSelection.forEach((item, index, self) => {
        this.clientids.push(item.id);
        this.selectclientName.push(item.clientName);
      });

      //判断数据有没有选中，有就弹出表单
      if (this.clientids.length > 0) {
        this.operate = 3;
        this.visible = true;
      } else {
        this.$message.warning({
          message: "您还没有选中客户端数据！",
          center: true,
          duration: 1200
        });
      }
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

          //把clear_time和clear_scan_interval的天转化为秒
          this.dataForm.clear_time = this.dataForm.clear_time * 60 * 60 * 24;
          this.dataForm.clear_scan_interval =
            this.dataForm.clear_scan_interval * 60 * 60 * 24;

          if (this.operate === 0) {
            //添加数据
            delete this.dataForm.file;

            //把clear_time和clear_scan_interval的天转化为秒
            this.dataForm.clear_time = this.dataForm.clear_time * 60 * 60 * 24;
            this.dataForm.clear_scan_interval =
              this.dataForm.clear_scan_interval * 60 * 60 * 24;

            createClient(this.dataForm).then(res => {
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
            delete this.dataForm.file;
            updateClient(this.dataForm).then(res => {
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
          } else if (this.operate === 2) {
            //批量添加数据
            console.log(this.dataForm.file);

            this.dataForm = {
              region_center_id: this.dataForm.region_center_id,
              file: this.dataForm.file
            };
            //console.log(formData.get("file"));

            createBatchClient(this.dataForm).then(res => {
              if (res.error_code === 0) {
                this.$notify({
                  title: this.$t("table.success"),
                  message: this.$t("client.createBatchSuccess"),
                  type: "success",
                  duration: 2000
                });
                this.getList();
                this.resetForm();
              }
            });
          } else if (this.operate === 3) {
            //批量修改数据
            this.dataForm = {
              clientids: this.clientids,
              region_center_id: this.dataForm.region_center_id,
              transfer_time: this.dataForm.transfer_time,
              clear_time: this.dataForm.clear_time,
              heartbeat_interval: this.dataForm.heartbeat_interval,
              upload_scan_interval: this.dataForm.upload_scan_interval,
              clear_scan_interval: this.dataForm.clear_scan_interval,
              console_log_level: this.dataForm.console_log_level,
              file_log_level: this.dataForm.file_log_level,
              is_log_to_server: this.dataForm.is_log_to_server,
              log_server_ip: this.dataForm.log_server_ip,
              log_server_port: this.dataForm.log_server_port,
              xupload_point_in_time: this.dataForm.upload_point_in_time
            };

            console.log(this.dataForm);

            updateBatchClient(this.dataForm).then(res => {
              if (res.error_code === 0) {
                delete this.dataForm.clientids;
                this.$notify({
                  title: this.$t("table.success"),
                  message: this.$t("client.updateBatchSuccess"),
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
          deleteClient({ id }).then(res => {
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
          site_id: null,
          //regionName: "",
          region_center_id: null,
          clientName: null,
          location: [],
          location_id: null,
          street: "",
          transfer_time: null,
          clear_time: null,
          heartbeat_interval: null,
          upload_scan_interval: null,
          clear_scan_interval: null,
          console_log_level: "",
          file_log_level: "",
          is_log_to_server: "true",
          log_server_ip: "",
          log_server_port: null,
          upload_point_in_time: "",
          file: null
        };
      }, 100);
      this.visible = false;
      //保存按钮启动
      this.saveLoading = false;
    }
  }
};
</script>
