import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router';
import ApiService from "./services/ApiService.ts";

const app = createApp(App);

// 🏁 Apply Language on First Load
const setupLanguage = () => {
    const savedLang = localStorage.getItem("lang") || "en";
    document.documentElement.lang = savedLang;
    document.documentElement.dir = savedLang === "ar" ? "rtl" : "ltr";
};
setupLanguage(); // Call it before mounting the app

app.use(router);
ApiService.init(app)

app.mount("#app");