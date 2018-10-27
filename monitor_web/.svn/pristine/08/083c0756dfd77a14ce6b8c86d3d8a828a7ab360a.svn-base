<template>
    <div class='storage-agency'>

        <div class="title">
            <h2>{{generateTitle('storageagency')}}</h2>
            <div class="search">
                <!-- <el-input v-model="search" :placeholder="$t('region.search')" :title="$t('region.search')" prefixIcon="el-icon-search" @keyup.enter.native="handleFilter" clearable></el-input> -->
                <!-- <el-select v-model="listQuery.searchkeys.regionids" clearable multiple collapse-tags :placeholder="$t('client.chooseRegion')">
                  <el-option v-for="item in regionNameLists" :label="item.name" :key="item.id" :value="item.id"></el-option>
                </el-select>
                <el-button type="primary" @click="handleFilter" icon="el-icon-search">{{$t('table.search')}}</el-button> -->

                <el-select v-model="region_id" clearable @change="getList(region_id)">
                    <el-option v-for="item in regionNameLists" :label="item.name" :key="item.id" :value="item.id"></el-option>
                </el-select>

                <el-button type="primary" icon="el-icon-download" :loading='$store.getters.downloadLoading' @click="handleDownload(list[0],list,$t('title')+'-'+generateTitle('storageagency'))">{{$t('table.export')}}</el-button>
            </div>
        </div>

        <main class="list">
            <el-table :data="list" border style="width:100%" v-loading="loading">
                <el-table-column prop="sgw_id" label="ID" width='60'></el-table-column>
                <el-table-column prop="region_id" :label="$t('agency.region_id')"></el-table-column>
                <el-table-column prop="sgw_ip" :label="$t('agency.sgw_ip')"></el-table-column>
                <el-table-column prop="cpu_percent" :label="$t('agency.cpu_percent')">
                  <template slot-scope="scope">
                      <el-tooltip effect="dark" :content="String(scope.row.cpu_percent)" placement="right">
                          <el-progress :width="60" type="circle" :percentage="scope.row.cpu_percent"></el-progress>
                      </el-tooltip>
                  </template>
                </el-table-column>
                <el-table-column prop="mem_total" :label="$t('agency.mem_total')">
                  <template slot-scope="scope">
                    <p>{{scope.row.mem_total | transferKB(scope.row.cpu_percent)}}</p>
                  </template>
                </el-table-column>
                <el-table-column prop="mem_free" :label="$t('agency.mem_free')">
                  <template slot-scope="scope">
                    <p>{{scope.row.mem_free | transferKB(scope.row.mem_free)}}</p>
                  </template>
                </el-table-column>
                <el-table-column prop="disk_used" :label="$t('agency.disk_used')">
                  <template slot-scope="scope">
                    <p>{{scope.row.disk_used | transferKB(scope.row.disk_used)}}</p>
                  </template>
                </el-table-column>
                <el-table-column prop="disk_free" :label="$t('agency.disk_free')">
                  <template slot-scope="scope">
                    <p>{{scope.row.disk_free | transferKB(scope.row.disk_free)}}</p>
                  </template>
                </el-table-column>
                <el-table-column prop="netio_input" :label="$t('agency.netio_input')">
                  <template slot-scope="scope">
                    <p>{{scope.row.netio_input | transferKB(scope.row.netio_input)}}</p>
                  </template>
                </el-table-column>
                <el-table-column prop="netio_output" :label="$t('agency.netio_output')">
                  <template slot-scope="scope">
                    <p>{{scope.row.netio_output | transferKB(scope.row.netio_output)}}</p>
                  </template>
                </el-table-column>
                
                <el-table-column prop="conn_state" :label="$t('agency.conn_state')"></el-table-column>
                <el-table-column prop="conn_dealed" :label="$t('agency.conn_dealed')"></el-table-column>

                <!-- <el-table-column prop="capacityUsed" :label="$t('agency.capacityUsed')">
                    <template slot-scope="scope">
                        <el-tooltip effect="dark" :content="String(scope.row.capacityUsed)" placement="right">
                            <el-progress :width="60" type="circle" :percentage="Number((scope.row.capacityUsed/scope.row.capacityTotal*100).toFixed(2))"></el-progress>
                        </el-tooltip>
                    </template>
                </el-table-column>
                <el-table-column prop="capacityUsable" :label="$t('agency.capacityUsable')">
                    <template slot-scope="scope">
                        <el-tooltip effect="light" :content="String(scope.row.capacityUsable)" placement="right">
                            <el-progress :width="60" type="circle" :percentage="Number((scope.row.capacityUsable/scope.row.capacityTotal*100).toFixed(2))"></el-progress>
                        </el-tooltip>
                    </template>
                </el-table-column>
                <el-table-column prop="capacityTotal" :label="$t('agency.capacityTotal')"></el-table-column> -->

                <el-table-column prop="status" :label="$t('agency.status')">
                    <template slot-scope="scope">
                        <el-tag :type="scope.row.status | statusFilter">{{scope.row.status | statusFilter1}}</el-tag>
                    </template>
                </el-table-column>
            </el-table>
        </main>
    </div>
</template>

<style lang='scss'>
.storage-agency {
}
</style>

<script>
import { getRegionList } from "@/api/region";
import { getStorageAgencyList } from "@/api/storageAgency";
export default {
  name: "storage-agency",
  components: {},
  data() {
    return {
      //表格数据
      list: [], //表格数据
      //total: null, //数据总数
      loading: false, //数据加载
      regionNameLists: [], //所有区域名称
      region_id: "" //查询区域
    };
  },
  filters: {
    statusFilter(status) {
      const statusMap = {
        0: "warning",
        1: "success"
      };
      return statusMap[status];
    },
    statusFilter1(status) {
      const statusMap = {
        0: "离线",
        1: "在线"
      };
      return statusMap[status];
    },
    transferKB(value) {
      //console.log(value);
      switch (true) {
        case value < 1024:
          return value + "B";
          break;
        case value >= 1024 && value < 1024 * 1024:
          return Math.round(value / 1024) + "KB";
          break;
        case value >= 1024 * 1024 && value < 1024 * 1024 * 1024:
          return Math.round(value / (1024 * 1024)) + "MB";
          break;
        case value >= 1024 * 1024 * 1024 && value < 1024 * 1024 * 1024 * 1024:
          return Math.round(value / (1024 * 1024 * 1024)) + "GB";
          break;
        default:
          break;
      }
    }
  },
  created() {
    //获取所有区域名称-并且获取全部数据
    this.getRegionNameLists();
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

          //获取所有数据
          this.getAllList();
        }
      });
    },
    //获取所有数据
    getAllList() {
      //获取所有数据
      this.list = []; //数据初始化
      this.loading = true;
      this.regionNameLists.forEach(item => {
        getStorageAgencyList({ region_center_id: item.id }).then(res => {
          if (res.error_code === 0) {
            res.data.forEach(item => {
              this.list.push(item);
            });

            //this.total = res.data.total;
            this.loading = false;
          }
        });
      });
    },
    //获取单个数据
    getList(region_center_id) {
      //console.log(region_center_id);
      //region_center_id为空时获取全部数据，否则获取单个数据
      if (region_center_id === "") {
        this.getAllList();
      } else {
        this.loading = true;
        getStorageAgencyList({ region_center_id }).then(res => {
          if (res.error_code === 0) {
            this.list = res.data;

            this.loading = false;
          }
        });
      }
    }
  }
};
</script>
