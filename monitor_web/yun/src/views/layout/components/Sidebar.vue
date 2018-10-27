<template>
    <div class='sidebar'>
        <div class="user">
            <div class="photo">
                <img src="../../../assets/images/tx.jpg">
            </div>
        </div>

        <el-menu :defaultActive="routerIndex" :router="true" backgroundColor="#1f2837" textColor="#fff" activeTextColor="#ffd04b">
            <template v-for="item in menuItems" >
                
                    <el-menu-item :index="item.path + '/chart'" v-if="!item.haschild">
                        <i :class="item.icon"></i>
                        <i class="el-icon-arrow-right"></i>
                        <span slot="title">{{generateTitle(item.title)}}</span>
                    </el-menu-item>               
                
                    <el-submenu :index="item.path" v-if="item.haschild">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <i class="el-icon-arrow-right"></i>
                            <span slot="title">{{generateTitle(item.title)}}</span>
                        </template>
                        <template v-for="child in item.children">
                            <el-menu-item :index="item.path+'/'+child.path">
                                <i :class="child.icon"></i>
                                <i class="el-icon-arrow-right"></i>
                                <span slot="title">{{generateTitle(child.title)}}</span>
                            </el-menu-item>
                        </template>
                    </el-submenu>                
               
            </template>
            
           </el-menu> 
           
    </div>
</template>

<style lang='scss'>
.sidebar {
  .user {
    padding: 20px 0;
    border-bottom: 1px solid #333e53;
    .photo {
      width: 80px;
      height: 80px;
      margin: 0 auto;
      overflow: hidden;
      border-radius: 50%;
      border: 4px solid rgba(255, 255, 255, 0.3);
      img {
        width: 100%;
        height: auto;
      }
    }
  }
  .el-menu {
    border-right: none;
    background-color: #1f2837;
    li {
      width: 100%;
      border-bottom: 1px solid #333e53;
      i {
        color: #fff;
        &:nth-child(2) {
          float: right;
          margin-top: 18px;
        }
      }
    }
    .el-submenu {
      .el-submenu__title {
        color: #fff;
        i:last-child {
          display: none;
        }
      }
      > div {
        border-left: 8px solid #1f2837;
      }
      &:hover > div {
        border-color: #19202c;
      }
      &.is-opened > div i:nth-child(2) {
        transform: rotate(90deg); transition: all .3s;
      }
      .el-menu-item {
        padding-left: 30px !important;
      }
    }
    .el-menu-item {
      color: #fff;
      border-left: 8px solid #1f2837;
      padding-right: 20px;
      &:hover {
        background-color: #233342;
        border-left-color: #f44336;
      }
      &.is-active {
        background-color: #233342;
        border-left-color: #f44336;
        color: #ffd04b;
      }
    }
    .el-menu-item .fa,
    .el-submenu .fa {
      margin-right: 5px;
      width: 24px;
      text-align: center;
      font-size: 18px;
      vertical-align: middle;
    }
    .el-submenu__icon-arrow {
      display: none;
    }
  }
}
</style>

<script>
export default {
  name: "sidebar",
  components: {},
  data() {
    return {
      menuItems: [] //菜单项
      //routerIndex: this.$route.path, //记录首页默认导航，el-menu必备
    };
  },
  computed: {
    routerIndex(){
        return this.$route.path; //根据路由实时更新
    }
  },
  created() {
    //获取菜单数据
    this.menuItems = this.getmenuItems(this.$store.getters.permission_routers);
  },
  methods: {
    //获取菜单数据
    getmenuItems(routers) {
      let menus = [];

      for (let i in routers) {
        if (routers[i].meta) {
          if (routers[i].meta.ismenu) {
            let menuobj = {
              path: "",
              name: "",
              icon: "",
              title: "",
              haschild: false,
              children: []
            };
            menuobj.path = routers[i].path;
            menuobj.title = routers[i].meta.title;
            menuobj.name = routers[i].meta.menu_name;
            menuobj.icon = routers[i].meta.icon;
            if (routers[i].children && routers[i].children.length) {
              menuobj.children = this.getmenuItems(routers[i].children);
              if (menuobj.children.length > 0) menuobj.haschild = true;
            }
            menus.push(menuobj);
          }
        }
      }

      return menus;
    }
  }
};
</script>
