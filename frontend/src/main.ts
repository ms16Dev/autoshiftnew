import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router';
import ApiService from "./services/ApiService.ts";

const app = createApp(App);
app.use(router);
ApiService.init(app)

app.mount("#app");