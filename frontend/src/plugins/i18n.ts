// src/plugins/i18n.ts
import { createI18n } from 'vue-i18n'

const messages = {
    en: {
        welcome: 'Welcome',
    },
    ar: {
        welcome: 'مرحبا',
    },
}

const savedLang = localStorage.getItem('lang') || 'en'

const i18n = createI18n({
    legacy: false, // use Composition API
    locale: savedLang,
    fallbackLocale: 'en',
    messages,
})

export default i18n
