
import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig(({ mode }) => {
  // Load env file based on `mode` in the root directory
  const env = loadEnv(mode, process.cwd(), '')

  return {
    plugins: [vue()],
    // Optional: You can expose env variables to your client code
    define: {
      __APP_ENV__: JSON.stringify(env.VITE_API_BASE_URL)
    },
    server: {
      host: '0.0.0.0', // Ensures it listens to external requests
      port: 3000, // Match the Fly.io expected port
      proxy: {
        // If you need API proxy during development
        '/api': {
          target: env.VITE_API_BASE_URL || 'http://localhost:8080',
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, '')
        }
      }
    }
  }
})