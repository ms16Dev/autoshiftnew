// stores/staticData.ts
import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { DataType, DataItem, Make, City, Currency, Country } from '../types/staticData.ts'
import apiService from "../core/services/ApiService.ts";

export const useStaticDataStore = defineStore('staticData', () => {
    // Store all data types
    const data = ref<Record<DataType, DataItem[]>>({
        roles: [],
        status: [],
        makes: [],
        fuel: [],
        engines: [],
        gears: [],
        colors: [],
        shapes: [],
        luxury: [],
        safety: [],
        countries: [],
        cities: [],
        currencies: []
    })

    // Current language
    const currentLanguage = ref(localStorage.getItem("lang") || "en");

    // Current country
    const currentCountry = ref(localStorage.getItem("country") || "country_yemen");


    // Computed properties for easier access to specific types
    const makes = computed(() => data.value.makes as Make[])
    const cities = computed(() => data.value.cities as City[])
    const currencies = computed(() => data.value.currencies as Currency[])
    const countries = computed(() => data.value.countries as Country[])

    // Fetch data for a specific type
    const fetchData = async (type: DataType) => {
        try {
            const response = await apiService.get1(`/ref-data/${type}`)
            const jsonData = await response.data
            data.value[type] = jsonData
        } catch (error) {
            console.error(`Error fetching ${type}:`, error)
        }
    }

    // Get localized name for an item
    const getLocalizedName = (item: DataItem) => {
        return currentLanguage.value === 'en' ? item.name_en : item.name_ar
    }

    // Find item by ID in a specific data type
    const findItemById = <T extends DataItem>(type: DataType, id: string): T | undefined => {
        return data.value[type].find(item => item.id === id) as T | undefined
    }

    // Toggle language
    const toggleLanguage = (lang: string) => {
        currentLanguage.value = lang
    }

    // Get all cities for a specific country
    const getCitiesByCountry = (): City[] => {
        const country = findItemById<Country>('countries', currentCountry.value)
        if (!country) return []

        return country.cities
            .map(cityId => findItemById<City>('cities', cityId))
            .filter(Boolean) as City[]
    }

    // Get all currencies for a specific country
    const getCurrenciesByCountry = (countryId: string): Currency[] => {
        const country = findItemById<Country>('countries', countryId)
        if (!country) return []

        return country.currencies
            .map(currencyId => findItemById<Currency>('currencies', currencyId))
            .filter(Boolean) as Currency[]
    }

    // Get all classes for a specific make
    const getMakeClasses = (makeId: string): string[] => {
        const make = findItemById<Make>('makes', makeId)
        return make?.classes || []
    }

    // Get country for a specific city
    const getCountryForCity = (cityId: string): Country | undefined => {
        const city = findItemById<City>('cities', cityId)
        if (!city) return undefined
        return findItemById<Country>('countries', city.countryId)
    }

    // Get country for a specific currency
    const getCountryForCurrency = (currencyId: string): Country | undefined => {
        const currency = findItemById<Currency>('currencies', currencyId)
        if (!currency) return undefined
        return findItemById<Country>('countries', currency.countryId)
    }

    // Initialize store by fetching all data types
    const initialize = async () => {
        const types: DataType[] = [
            'roles', 'status', 'makes', 'fuel', 'engines', 'gears',
            'colors', 'shapes', 'luxury', 'safety', 'countries',
            'cities', 'currencies'
        ]
        await Promise.all(types.map(type => fetchData(type)))
    }

    return {
        data,
        makes,
        cities,
        currencies,
        countries,
        currentLanguage,
        fetchData,
        getLocalizedName,
        findItemById,
        toggleLanguage,
        getCitiesByCountry,
        getCurrenciesByCountry,
        getMakeClasses,
        getCountryForCity,
        getCountryForCurrency,
        initialize
    }
})