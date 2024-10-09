import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    proxy: {
      '/confirm': {
        target: 'https://j11a308.p.ssafy.io',
        changeOrigin: true,
      }
    }
  },
})
