<template>
    <div class='storage-agency'>

        <div class="title">
            <h2>{{generateTitle('logs')}}</h2>
            <div class="search">
                <el-input v-model="search" :placeholder="$t('logs.search')" :title="$t('logs.search')" prefixIcon="el-icon-search" @keyup.enter.native="handleFilter" clearable></el-input>
                &nbsp;&nbsp;
                <el-date-picker v-model="timeFilter" value-format="yyyy-MM-dd HH:mm:ss" type="datetimerange" :range-separator="$t('logs.toTime')" :start-placeholder="$t('logs.startTime')" :end-placeholder="$t('logs.endTime')"></el-date-picker>

                <el-button type="primary" @click="handleFilter" icon="el-icon-search">{{$t('table.search')}}</el-button>
                <el-button type="primary" icon="el-icon-download" :loading='$store.getters.downloadLoading' @click="handleDownload(list[0],list,$t('title')+'-'+generateTitle('logs'))">{{$t('table.export')}}</el-button>
            </div>
        </div>

        <main class="list">
            <el-table :data="list" border style="width:100%" v-loading="loading">
                <!-- <el-table-column prop="id" :label="$t('logs.id')" width='60'></el-table-column> -->
                <el-table-column prop="username" :label="$t('logs.username')"></el-table-column>
                <el-table-column prop="content" :label="$t('logs.content')"></el-table-column>
                <el-table-column prop="time" :label="$t('logs.time')">
                    <template slot-scope="scope">
                        <span>{{scope.row.time}}</span>
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
.storage-agency {
}
</style>

<script>
import { getUserList } from "@/api/user";
import { getLogsList } from "@/api/logs";
export default {
  name: "storage-agency",
  components: {},
  data() {
    return {
      //表格数据
      list: [], //表格数据
      total: null, //数据总数
      loading: true, //数据加载
      //查询条件
      timeFilter: "",
      search: "", //用于把字符串转化为对象内容作为请求数据参数
      listQuery: {
        searchkeys: {
          username: [""],
          time: ["", ""]
        },
        page: 1, //当前页
        limit: 20 //一页多少条记录
      }
    };
  },
  created() {
    //获取表格数据
    this.getList();
  },
  methods: {
    //获取数据
    getList() {
      this.loading = true;

      //=======================处理查询数据，避免后端报错================================
      if (this.search === "" && this.timeFilter === "") {
        delete this.listQuery.searchkeys;
      } else {
        //转化username对象
        if (this.search !== "") {
          let arr = this.search.split(" ");
          this.listQuery.searchkeys.username = arr;
        }

        //转化time对象
        if (this.timeFilter !== "") {
          this.listQuery.searchkeys.time = [
            this.timeFilter[0],
            this.timeFilter[1]
          ];
        }
      }

      getLogsList(this.listQuery).then(res => {
        if (res.error_code === 0) {
          this.list = res.data.results;

          this.total = res.data.total;
          this.loading = false;

          //=======================处理查询数据，避免后端报错================================
          if (this.search === "" && this.timeFilter === "") {
            this.listQuery.searchkeys = {
              username: [""],
              time: ["", ""]
            };
          } else {
            if (this.search === "") {
              this.listQuery.searchkeys = { username: [""] };
            }
            if (this.timeFilter === "") {
              this.listQuery.searchkeys = { time: ["", ""] };
            }
          }
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
    }
  }
};
</script>
