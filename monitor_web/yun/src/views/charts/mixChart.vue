<template>
    <div class='chart-container'>
        <chart width="100%" height="100%"></chart>
    </div>
</template>

<style lang='scss' scoped>
    .chart-container{ position: relative; padding: 20px; width: 100%; height:85vh;}
</style>

<script>
    import Chart from '@/components/Charts/mixChart';
    export default {
        name: 'mixChart',
        components: {Chart},
        data() {
            return {

            }
        }
    }
</script>
