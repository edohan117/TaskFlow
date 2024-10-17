const {defineConfig} = require('@vue/cli-service')

module.exports = defineConfig({
  devServer: {
    port: 3000, // 포트 3000에서 실행되도록 설정
    proxy: {
      '/api': {
        target: 'http://localhost:3001',
        changeOrigin: true
      }
    }
  }
})
