<template>
    <!--引入svg-sprite-loader依赖，使用svg sprite合并为一张svg，有多个symbol应用，通过symbol的id来显示图标-->
    <svg :class="svgClass" aria-hidden="true">
        <use :xlink:href="iconName"></use>
    </svg>
</template>

<style lang='scss' scoped>
    .svg-icon { fill: currentColor; overflow: hidden; //currentColor：当前标签所继承的文字颜色，使用hover等改变文字颜色，图标也跟着一起变化
    }
</style>

<script>
    export default {
        name: 'svg-icon',
        components: {},
        props: {
            iconClass: {
                type: String,
                required: true
            },
            className: {
                type: String
            }
        },
        computed: {
            iconName(){
                return `#icon-${this.iconClass}`;
            },
            svgClass(){
                if(this.className){
                    return 'svg-icon ' + this.className;
                }else{
                    return 'svg-icon';
                }
            }
        },
        mounted(){

        },
        data() {
            return {
                msg: ''
            }
        }
    }
</script>
