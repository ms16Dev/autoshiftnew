// types/staticData.ts
export interface BaseItem {
    id: string
    name_en: string
    name_ar: string
}

export interface Make extends BaseItem {
    url: string
    classes: string[]
}

export interface City extends BaseItem {
    countryId: string
}

export interface Currency extends BaseItem {
    countryId: string
}

export interface Country extends BaseItem {
    cities: string[]
    currencies: string[]
}

export type DataType =
    | 'roles'
    | 'status'
    | 'makes'
    | 'fuel'
    | 'engines'
    | 'gears'
    | 'colors'
    | 'shapes'
    | 'luxury'
    | 'safety'
    | 'countries'
    | 'cities'
    | 'currencies'

export type DataItem = BaseItem | Make | City | Currency | Country

