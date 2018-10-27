<template>
    <div class='home'>
        <el-container>
            <el-header>
                <navbar></navbar>
            </el-header>
            <el-container>
                <el-aside width="240px">
                    <sidebar></sidebar>
                </el-aside>
                <el-main>
                    <tags-view></tags-view>
                    <router-view/>
                </el-main>
            </el-container>
            <!--<el-footer height="40px">footer</el-footer>-->
        </el-container>
    </div>
</template>

<style lang='scss'>
@import "../assets/css/base";
.home {
  height: 100%;
  background-color: #e0e8f5;
  .el-container {
    height: 100%;
    //头部
    .el-header {
      box-shadow: 0 2px 3px 0 rgba(0, 0, 0, 0.2);
      padding: 0 20px 0 0;
      background: #fff;
    }
    //导航
    .el-aside {
      background-color: #1f2837;
    }
    .el-main {
      //全局定义所有最小高度为100%
      > div:nth-child(2) {
        background-color: #fff;
        overflow: hidden;
        padding: 0 20px;
        border-radius: 0 0 5px 5px;
        .el-table__body-wrapper {
          overflow: hidden;
        }
      }
      //表单数据
      .dataForm {
        .el-dialog {
          width: 800px;
          margin-bottom: 100px;
        }
        .el-form {
          .el-form-item {
            .el-slider__button {
              margin-top: -5px;
            }
          }
        }
      }
      //标题
      .title {
        height: 60px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        h2 {
          font-weight: normal;
          font-size: 16px;
          display: none;
          .el-tag {
            font-weight: bold;
          }
        }
        .search {
          flex: 1;
          margin-right: auto;
          display: flex; //margin-left:40px;
          .el-input {
            width: 250px;
          }
          .el-select {
            .el-input {
              width: 150px;
            }
          }
        }
        .el-button,
        .el-select {
          margin-left: 10px;
        }
      }
      //表格数据
      .tableLists {
        .el-table {
          font-size: 12px;
          .cell {
            text-align: center;
          }
          .el-switch__label {
            color: #999;
            &.is-active {
              color: #409eff;
            }
          }
        }
      }
      //分页样式
      .page {
        display: flex;
        margin: 20px 0;
        .el-pagination {
          margin-left: auto;
        }
      }
    }
    //尾部
    .el-footer {
      position: fixed;
      background-color: #409eff;
      width: 100%;
      bottom: 0;
    }
  }
}
</style>

<script>
import Navbar from "@/views/layout/components/Navbar";
import Sidebar from "@/views/layout/components/Sidebar";
import TagsView from "@/views/layout/components/TagsView";
export default {
  name: "home",
  components: { Navbar, Sidebar, TagsView },
  data() {
    return {};
  },
  created() {},
  methods: {
    toggleSideBar() {
      this.$store.dispatch("toggleSideBar");
    }
  }
};
</script>
