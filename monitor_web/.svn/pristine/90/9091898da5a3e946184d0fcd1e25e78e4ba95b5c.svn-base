<template>
      <el-tabs v-model="activeName" class="chart">
        <el-tab-pane label="metadata server状态" name="first">
          <metadata></metadata>
        </el-tab-pane>
        <el-tab-pane label="storage gateway状态" name="second">storage gateway状态</el-tab-pane>
        <el-tab-pane label="本机状态" name="third">本机状态</el-tab-pane>
      </el-tabs>
</template>

<style lang="scss">

</style>

<script>
import metadata from '@/views/charts/metadata'

export default {
  name: 'Chart',
  components: { metadata },
  data(){
    return {
      activeName: 'first'
    }
  }
}
</script>


