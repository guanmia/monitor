<template>
    <el-dropdown trigger="click" class="international" @command="handleSetLanguage">
        <div>
            <svg-icon class-name="international-icon" icon-class="language"></svg-icon>
        </div>
        <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="zh" :disabled="language === 'zh'"><a href="javascript:;">中文</a></el-dropdown-item>
            <el-dropdown-item command="en" :disabled="language === 'en'"><a href="javascript:;">English</a></el-dropdown-item>
        </el-dropdown-menu>
    </el-dropdown>
</template>

<style lang='scss' scoped>
    .international-icon { width:25px; height:25px; cursor: pointer;}
</style>

<script>
    export default {
        name: 'langselect',
        components: {},
        data() {
            return {
                msg: ''
            }
        },
        computed: {
            language(){
                return this.$store.getters.language
            }
        },
        methods: {
            handleSetLanguage(lang){
                this.$i18n.locale = lang;
                this.$store.dispatch('setLanguage',lang);
                this.$message({
                    message: lang === 'zh' ? '切换中文成功！！' : 'switch English succss！！',
                    center: true,
                    type: 'success'
                });
            }
        }
    }
</script>
