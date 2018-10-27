<template>
    <!-- <el-breadcrumb class="app-breadcrumb" separator="/">
        <transition-group name="breadcrumb">
            <el-breadcrumb-item v-for="(item,index) in levelList" :key="item.path" v-if="item.meta.title">
                <span v-if="index === levelList.length-1" class="no-redirect">{{generateTitle(item.meta.title)}}</span>
                <router-link v-else :to="item.redirect || item.path">{{generateTitle(item.meta.title)}}</router-link>
            </el-breadcrumb-item>
        </transition-group>
    </el-breadcrumb> -->
    <div class='bread'>
        <h3>{{generateTitle(title)}}</h3>
    </div>
</template>

<style lang='scss' scoped>
    .app-breadcrumb.el-breadcrumb { display: inline-block; font-size: 14px; line-height: 50px; margin-left: 20px;
        .no-redirect { color: #97a8be; cursor: text;}
    }
    .bread{ margin-left: 20px;
        h3{ font-weight:normal;}
    }
</style>

<script>
    export default {
        name: 'demo',
        components: {},
        data() {
            return {
                levelList: [],
                title: ""
            }
        },
        created(){
            this.getBreadcrumb();
        },
        watch: {
            $route(){
                this.getBreadcrumb();
            }
        },
        methods: {
            getBreadcrumb(){
                //console.log(this.$route);
                /*let matched = this.$route.matched.filter(item => item.meta.title);

                const first = matched[0];
                if(first.meta.title !== 'chart'){
                    matched = [{path: '/chart',meta: {title: 'chart'}}].concat(matched);
                }
                console.log(matched);
                this.levelList = matched;*/

                this.levelList = [];
                let route = this.$route;
                // if(route.meta.title === 'chart'){
                //     this.levelList.push({path: '/home',meta: {title: 'home'}});
                // }else{
                //     this.levelList = [{path: '/home',meta: {title: 'home'}}].concat({path: route.path,meta: {title: route.meta.title}});
                // }
                
                this.title = route.meta.title;
                //console.log(this.title);
            }
        }
    }
</script>
