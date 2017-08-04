<template>
    <div>
        <h2 class="hello-a" @click="incrementAsync">{{ msg }}</h2>
    </div>
</template>

<script>
    import {mapGetters, mapActions} from 'vuex'

    export default {
        props: {
            message1: {
                type: String,
                required: true
            }
        },
        computed: {},
        data () {
            return {
                msg: this.message1 + ' ' + this.$store.state.msg
            }
        },
        methods: {
            ...mapActions([
                'incrementAsync'
            ])
        }
    }
</script>

<style>
    .hello-a {
        font-size: 18px;
        color: red;
    }
</style>
