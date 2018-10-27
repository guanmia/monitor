<template>
    <div class='backend-storage'>

        <!--<div class="dataForm">
            <el-dialog title="修改客户端所在区域" :visible.sync="visible" :closeOnClickModal="false" :beforeClose="handleClose">
                <el-form :model="dataForm" ref="dataForm" labelWidth="150px">
                    <el-form-item label="区域名称：">
                        <el-input v-model="dataForm.areaName"></el-input>
                    </el-form-item>
                    <el-form-item label="存储ip：">
                        <el-input v-model="dataForm.storageIp"></el-input>
                    </el-form-item>
                    <el-form-item label="服务器类型：">
                        <el-input v-model="dataForm.serverType"></el-input>
                    </el-form-item>
                    <el-form-item label="已用容量：">
                        <el-slider v-model="dataForm.capacityUserd" :min="0" :max="2048" showInput></el-slider>
                    </el-form-item>
                    <el-form-item label="可用容量：">
                        <el-slider v-model="dataForm.capacityUsable" :min="0" :max="2048" showInput></el-slider>
                    </el-form-item>
                    <el-form-item label="总容量：">
                        <el-slider v-model="dataForm.capacityTotal" :min="0" :max="2048" showInput></el-slider>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="saveForm">保 存</el-button>
                        <el-button type="info" @click="resetForm">取 消</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
        </div>-->

        <div class="title">
            <h2>后端存储列表</h2>
            <!--<el-button type="primary" @click="addRow">添 加</el-button>-->
        </div>

        <main class="tableLists">

            <el-table :data="tableLists" border style="width:100%">
                <el-table-column prop="areaName" label="区域名称" :filters="areaNameFilter" :filter-method="areaNamefilterTag" filter-placement="bottom"></el-table-column>
                <el-table-column prop="storageIp" label="存储ip"></el-table-column>
                <el-table-column prop="serverType" label="服务器类型"></el-table-column>
                <el-table-column prop="capacityUserd" label="已用容量">
                    <template slot-scope="scope">
                        <el-tooltip effect="light" :content="String(scope.row.capacityUserd)" placement="right">
                            <el-progress :width="60" type="circle" :percentage="Number((scope.row.capacityUserd/scope.row.capacityTotal*100).toFixed(2))"></el-progress>
                        </el-tooltip>
                    </template>
                </el-table-column>
                <el-table-column prop="capacityUsable" label="可用容量">
                    <template slot-scope="scope">
                        <el-tooltip effect="light" :content="String(scope.row.capacityUsable)" placement="right">
                            <el-progress :width="60" type="circle" :percentage="Number((scope.row.capacityUsable/scope.row.capacityTotal*100).toFixed(2))"></el-progress>
                        </el-tooltip>
                    </template>
                </el-table-column>
                <el-table-column prop="capacityTotal" label="总容量">
                    <!--<template slot-scope="scope">
                        <el-tooltip effect="light" :content="String(scope.row.capacityTotal)" placement="right">
                            <el-progress :width="60" type="circle" :percentage="100"></el-progress>
                        </el-tooltip>
                    </template>-->
                </el-table-column>
                <el-table-column label="操作" width="200">
                    <template slot-scope="scope">
                        <!--<el-button type="primary" @click="editRow(scope.$index)">配 置</el-button>-->
                        <el-button @click.native.prevent="deleteRow(scope.$index)">删 除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </main>

    </div>
</template>

<style lang='scss'>
    .backend-storage{}
</style>

<script>
    export default {
        name: 'backend-storage',
        components: {},
        data() {
            return {
                //表格数据
                tableLists: [
                    {id: 1,areaName: '深圳1',storageIp: '0.0.0.0',serverType: '服务器类型',capacityUserd: 1000,capacityUsable: 24,capacityTotal: 1024},
                    {id: 2,areaName: '深圳2',storageIp: '0.0.0.0',serverType: '服务器类型',capacityUserd: 900,capacityUsable: 124,capacityTotal: 1024},
                    {id: 3,areaName: '深圳1',storageIp: '0.0.0.0',serverType: '服务器类型',capacityUserd: 800,capacityUsable: 224,capacityTotal: 1024},
                    {id: 4,areaName: '深圳2',storageIp: '0.0.0.0',serverType: '服务器类型',capacityUserd: 700,capacityUsable: 324,capacityTotal: 1024},
                ],
                //表单数据
                dataForm: {id: null,areaName: '深圳1',storageIp: '0.0.0.0',serverType: '服务器类型',capacityUserd: 1000,capacityUsable: 24,capacityTotal: 1024},
                //dataForm: {id: null,areaName: '',storageIp: '',serverType: '',capacityUserd: 0,capacityUsable: 0,capacityTotal: 0},
                visible: false, //表单显示与隐藏
                areaNameFilter: [], //区域名称过滤
                editIndex: 0, //点击修改的是哪个索引
                operate: 0, //判断用户是添加（0）,修改数据（1）

            }
        },
        mounted(){
            //获取过滤数组
            this.FilterFn();
        },
        methods: {
            //获取过滤数组--通用
            FilterFn(){
                let list = [],lists = [],x,y;
                for(x of this.tableLists){ //变成数据
                    list.push(x.areaName);
                }
                lists = list.filter((item,index,self) => { //过滤数组相同的值
                    return self.indexOf(item) === index;
                });

                for(y of lists){
                    this.areaNameFilter.push({text: y,value: y});
                }
            },
            //筛选必须--区域
            areaNamefilterTag(value,row){
                return row.areaName === value;
            },
            //点击修改数据
            editRow(index){
                this.editIndex = index;
                this.operate = 1;
                this.visible = true;
                for(let x in this.tableLists[index]){
                    this.dataForm[x] = this.tableLists[index][x];
                }
            },
            //批量选择表单数据
            handleSelectionChange(val){
                //console.log(val);
                this.multipleSelection = val;
            },
            //保存数据
            saveForm(){
                if(this.operate === 0){ //添加数据
                    //this.dataForm.id = this.tableLists.length + 1;
                    //this.tableLists.push(JSON.parse(JSON.stringify(this.dataForm))); //深度复制，数据解耦
                }else if(this.operate === 1){ //修改数据
                    for(let x in this.dataForm){
                        this.tableLists[this.editIndex][x] = this.dataForm[x];
                    }
                    setTimeout(() => { //解决快速点击2次时，表单已重置修改信息为空的问题
                        this.dataForm = {id: null,areaName: '',storageIp: '',serverType: '',capacityUserd: 0,capacityUsable: 0,capacityTotal: 0}; //解决重置无效问题
                    },500);
                }
                this.resetForm();
            },
            //删除数据
            deleteRow(index){
                this.$confirm('是否删除该数据？','提示',{
                    confirmButtonText: '删除',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$message.success({message: '删除数据成功！',center: true,duration: 1200, onClose: () => {
                        this.tableLists.splice(index,1);
                    }});
                }).catch(() => {});
            },
            //关闭表单，并重置表单
            handleClose(){
                this.resetForm();
            },
            //重置并退出
            resetForm(){
                this.$refs.dataForm.resetFields();
                this.visible = false;
            }
        }
    }
</script>
