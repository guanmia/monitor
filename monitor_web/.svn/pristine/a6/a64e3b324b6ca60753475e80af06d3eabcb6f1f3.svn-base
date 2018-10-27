<template>
    <div class="pagination">
            <el-pagination
                background
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-sizes="[10,20,50,100]"
                :page-size="pageSize"
                layout="total,sizes,prev,pager,next,jumper"
                :total="tableLists.length">
            </el-pagination>
        </div>
</template>

<style lang='scss'>
    //@import '../assets/css/base.scss';

</style>

<script>
    export default {
        name: 'pagination',
        components: {},
        data() {
            return {
                //表格当前分页数据
                currentTableLists: [],
                pageSize: 10, //一页有多少条数据
                currentPage: 1, //当前页
            }
        },
        methods: {
            /*----------------------分页--------------------*/
            //每页多少条数据
            handleSizeChange(val) {
                this.pageSize = val;
                this.tableListsChange();
            },
            //当前页
            handleCurrentChange(val){
                this.currentPage = val;
                this.tableListsChange();
            },
            //获取当前分页表格数据
            tableListsChange(){
                this.currentTableLists = this.tableLists.filter((item,index,self) => {
                    return index >= this.pageSize * (this.currentPage-1) && index < this.pageSize * this.currentPage;
                });
                this.loading = false;
            },
        }
    }
</script>
