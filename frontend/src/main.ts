import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router';
import ApiService from "./core/services/ApiService.ts";
import {createPinia} from "pinia";
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import i18n from './plugins/i18n.ts'

const app = createApp(App);

// 🏁 Apply Language on First Load
const setupLanguage = () => {
    const savedLang = localStorage.getItem("lang") || "en";
    document.documentElement.lang = savedLang;
    document.documentElement.dir = savedLang === "ar" ? "rtl" : "ltr";
};
setupLanguage(); // Call it before mounting the app

// Create the Pinia store and add the persisted state plugin
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

app.use(i18n)

app.use(router);
ApiService.init(app)

app.use(pinia)

app.mount("#app");