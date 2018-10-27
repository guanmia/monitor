<template>
    <div class='tags-view-container'>
        <scroll-pane class="tags-view-wrapper" ref="scrollPane">
            <router-link ref="tag" class="tags-view-item" :class="isActive(tag) ? 'active' : ''"
                         v-for="tag in Array.from(visitedViews)"
                         :to="tag.path"
                         :key="tag.path"
                         @contextmenu.prevent.native="openMenu(tag,$event)">
                {{generateTitle(tag.title)}}
                <span class="el-icon-close" @click.prevent.stop="closeSelectedTag(tag)"></span>
            </router-link>
        </scroll-pane>
        <ul class="contextmenu" v-show="visible" :style="{left: left+'px',top: top+'px'}">
            <li @click="closeSelectedTag(selectedTag)">关闭</li>
            <li @click="closeOthersTags">关闭其它</li>
            <li @click="closeAllTags">关闭全部</li>
        </ul>
    </div>
</template>

<style lang='scss' scoped>
    .tags-view-container{ min-height:50px !important; border-radius: 5px;box-shadow: 0 1px 1px rgba(0, 0, 0, 0.2); border-top: 4px solid #f44336; position:relative; z-index:2;
        .tags-view-wrapper { background: #fff; height: 50px; border-bottom: 1px solid #d8dce5; box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .12), 0 0 3px 0 rgba(0, 0, 0, .04);
            display:flex; align-items:center;
            .tags-view-item { display: inline-block; position: relative; height: 30px; line-height: 30px; border: 1px solid #d8dce5; text-decoration:none;
                color: #495060; background: #fff; padding: 0 8px; font-size: 14px; margin-left: 5px; margin-top: 4px;
                &:first-of-type { margin-left: 15px;}
                &.active { background-color: #42b983; color: #fff; border-color: #42b983;
                    &::before { content: ''; background: #fff; display: inline-block; width: 8px; height: 8px; border-radius: 50%; position: relative; margin-right: 2px;}
                }
                .el-icon-close { width: 16px; height: 16px; vertical-align: 2px; border-radius: 50%; text-align: center; transition: all .3s cubic-bezier(.645, .045, .355, 1);
                    transform-origin: 100% 50%;
                    &:before { transform: scale(.6); display: inline-block; vertical-align: -3px;}
                    &:hover { background-color: #b4bccc; color: #fff;}
                }
            }
        }
        .contextmenu { margin: 0; background: #fff; z-index: 2; position: fixed; list-style-type: none; padding: 5px 0; border-radius: 4px;
            font-size: 12px; font-weight: 400; color: #333; box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, .3);
            li { margin: 0; padding: 7px 16px; cursor: pointer;
                &:hover { background: #eee;}
            }
        }
    }

</style>

<script>
    import ScrollPane from '@/components/ScrollPane';
    import { generateTitle } from '@/utils/i18n';
    export default {
        name: 'demo',
        components: {ScrollPane},
        data() {
            return {
                visible: false,
                top: 0,
                left: 0,
                selectedTag: {}
            }
        },
        computed: {
            visitedViews(){
                //visitedViews：路由数组
                return this.$store.state.tagsView.visitedViews;
            }
        },
        watch: {
            $route(){
                this.addViewTags();
                this.moveToCurrentTag();
            },
            visible(value){
                if(value){
                    window.addEventListener('click',this.closeMenu);
                }else{
                    window.removeEventListener('click',this.closeMenu);
                }
            }
        },
        mounted(){
            this.addViewTags();
        },
        methods: {
            generateTitle,
            generateRoute(){
                if(this.$route.meta.title){
                    return this.$route
                }
                return false;
            },
            isActive(route){
                return route.path === this.$route.path || route.name === this.$route.meta.title;
            },
            addViewTags(){
                const route = this.generateRoute();
                if(!route){
                    return false
                }
                this.$store.dispatch('addVisitedViews',route);
            },
            moveToCurrentTag(){
                const tags = this.$refs.tag;
                this.$nextTick(() => {
                    for(const tag of tags){
                        if(tag.to === this.$route.path){
                            this.$refs.scrollPane.moveToTarget(tag.$el);
                            break;
                        }
                    }
                });
            },
            closeSelectedTag(view) {
                this.$store.dispatch('delVisitedViews', view).then((views) => {
                    if (this.isActive(view)) {
                        const latestView = views.slice(-1)[0]
                        if (latestView) {
                            this.$router.push(latestView.path);
                        } else {
                            this.$router.push('/home')
                        }
                    }
                    this.visible = false;
                })
            },
            closeOthersTags() {
                this.$router.push(this.selectedTag.path)
                this.$store.dispatch('delOthersViews', this.selectedTag).then(() => {
                    this.moveToCurrentTag();
                    this.visible = false;
                })
            },
            closeAllTags() {
                this.$store.dispatch('delAllViews')
                this.$router.push('/')
                this.visible = false;
            },
            openMenu(tag, e){
                console.log(e);
                this.visible = true;
                this.selectedTag = tag;
                this.left = e.clientX;
                this.top = e.clientY;
            },
            closeMenu() {
                this.visible = false
            }
        }
    }
</script>
