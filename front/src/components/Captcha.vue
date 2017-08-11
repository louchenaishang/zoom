<template>
  <div :captchaId="captchaId" :scene="scene">
    <img :src="src" class="captcha">
  </div>
</template>

<script>
  export default {
    name: 'captcha',
    props: {
      captchaId: {
        type: String,
        required: true
      },
      scene: {
        type: String,
        required: true
      }
    },
    data() {
      return {
        prefix: '/api/captcha/image?captchaId='
      }
    },
    computed: {
      src() {
        return this.prefix + this.captchaId;
      }
    },
    methods: {
      //
    },
    mounted() {
      //
    }
  }
</script>

<style scoped>
  .captcha{
    border-radius: 4px;
  }
</style>
