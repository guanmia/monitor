<template>
    <div id="app">
        <router-view/>
    </div>
</template>

<script>
    export default {
        components: {},
        name: 'app'
    }
</script>

<style lang="scss">
    @import './assets/css/base.scss';
    #app {
        font-family: 'Microsoft YaHei','Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        //text-align: center;
        color: #2c3e50;
        font-size:14px;
        height:100%;
    }
    //全局控制下拉菜单样式
    .el-dropdown-menu{ background-color:#f44336 !important; border:none !important;
        .el-dropdown-menu__item{ padding:0 !important;
            a{ color:#fff; text-decoration:none; display:flex; padding:0 20px;
                &:hover{ color:#ffd04b; background-color:#f65f54;}
            }
        }
    }
    .el-popper[x-placement^=bottom] .popper__arrow{border-bottom-color: #f44336;}
    //用户详情提示样式
    .introduction{ max-width:200px;}
</style>
