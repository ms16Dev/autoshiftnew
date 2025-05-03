// src/plugins/i18n.ts
import { createI18n } from 'vue-i18n'

const messages = {
    en: {
        welcome: 'Welcome',
        add_image: 'Add image',
        car_status: 'Status',
        car_price: 'Price',
        car_class: 'Class',
        car_year: 'Year',
        car_mileage: 'Mileage',
        car_gear: 'Gear',
        car_fuel: 'Fuel',
        car_engine: 'Engine',
        car_shape: 'Shape',
        car_color: 'Color',
        car_luxury: 'Luxury',
        car_safety: 'Safety',
        car_location: 'Location',
        description: 'Description',
        add_more_images: 'Add more images',
        save_car: 'Submit car',
        car_price_title: 'Set car price',
        car_year_title: 'Set car year',
        car_mileage_title: 'ٍSet car mileage',
        car_description_title: 'Add car description',
        car_status_title: 'Choose car status',
        car_gear_title: 'Chose gear type',
        car_class_title: 'Select car class / model',
        car_fuel_title: 'Chose fuel type',
        car_engine_title: 'Chose engine size',
        car_body_title: 'Chose car body shape',
        car_color_title: 'Choose car color',
        car_location_title: 'Select car location',
        car_luxury_title: 'List luxury features',
        car_safety_title: 'List safety features',
        enter_description: 'Enter a description...',

    },
    ar: {
        welcome: 'مرحبا',
        add_image: 'أضف صورة',
        car_status: 'الحالة',
        car_price: 'السعر',
        car_class: 'الفئة',
        car_year: 'السنة',
        car_mileage: 'العداد',
        car_gear: 'الجير',
        car_fuel: 'الوقود',
        car_engine: 'المحرك',
        car_shape: 'الشكل',
        car_color: 'اللون',
        car_luxury: 'الرفاهية',
        car_safety: 'الحماية',
        car_location: 'المكان',
        description: 'الوصف',
        add_more_images: 'أضف المزيد من الصور',
        save_car: 'ارسال',
        car_price_title: 'أدخل سعر السيارة',
        car_year_title: 'أدخل سنة الصنع',
        car_mileage_title: 'أدخل عداد السيارة',
        car_description_title: 'أدخل مواصفات اخرى',
        car_status_title: 'اختر حال السيارة',
        car_gear_title: 'اختر نوع التعليق',
        car_class_title: 'اختر فئة السيارة',
        car_fuel_title: 'اختر نوع الوقود',
        car_engine_title: 'اختر خجم المحرك',
        car_body_title: 'اختر شكل السيارة',
        car_color_title: 'اختر لون السيارة',
        car_location_title: 'اختر مكان السيارة',
        car_luxury_title: 'اختر مواصفات الرفاهية',
        car_safety_title: 'اختر مواصفات الحماية',
        enter_description: 'أدخل المزيد من المواصفات',


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
