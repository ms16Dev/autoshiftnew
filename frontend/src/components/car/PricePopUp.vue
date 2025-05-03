<script setup lang="ts">
import {ref} from "vue";
import {useI18n} from "vue-i18n";
import {useStaticDataStore} from "../../stores/staticDataStore.ts";
const { t } = useI18n()
import {Currency} from "../../types/staticData";

const staticData = useStaticDataStore();

const props = defineProps({
  position: {
    type: String,
    default: 'bottom',
  },
  value: {
    type: Number,
    default: 0,
  },
});

// Load currencies from store
const currencies = ref<Currency[]>(staticData.getCurrenciesByCountry(staticData.getCurrentCountry().id));



const emit = defineEmits(['close', 'save', 'currency']);

const price = ref<number | null>(props.value);


const decPrice = () => {
  if (price.value !== null) {
    price.value = Math.max(price.value - 500, 500);
  }
};

const incPrice = () => {
  if (price.value !== null) {
    price.value = Math.min(price.value + 500, 45000);
  }
};

const savePrice = () => {
  if (price.value !== null) {
    emit('save', price.value);
    emit('currency', selectedCurrencyId.value);
  }

};

const selectedCurrencyId = ref<string | null>(currencies.value[0]?.id);


const selectCurrency = (currencyId: string) => {
  selectedCurrencyId.value = currencyId;

};
</script>

<template>
  <div
      :class="[
      'absolute bg-pink-700 z-100 w-full h-full',
      position === 'bottom' ? 'top-0 end-0' : '',
      position === 'top' ? 'bottom-0 end-0' : '',
      position === 'left' ? 'mr-2 right-full top-0' : '',
      position === 'right' ? 'ml-2 left-full top-0' : '',
    ]"
  >
    <div class="flex flex-col p-4">
      <p class="text-white text-3xl my-4">{{ t('car_price_title') }}</p>


      <div class="flex flex-col p-4">

      <!-- Currency Selection as Chips -->
      <div class="currency-chips mb-6">
        <div class="flex justify-center gap-2">
          <div
              v-for="currency in currencies"
              :key="currency.id"
              @click="selectCurrency(currency.id)"
              :class="[
              'px-4 py-2 rounded-full border-2 border-white cursor-pointer transition-colors',
              selectedCurrencyId === currency.id
                ? 'bg-pink-500 text-white'
                : 'bg-pink-700 text-white hover:bg-pink-600'
            ]"
          >
            {{ currency.name_en }}
          </div>
        </div>
      </div>

      <div class="flex flex-row items-center justify-between mb-6">
        <!-- decrease button -->
        <button
            @click="decPrice"
            class="w-32 h-32 text-3xl text-white bg-pink-700 hover:bg-pink-500"
        >
          <i class="fas fa-minus"></i>
        </button>

        <input
            type="number"
            class="w-36 bg-pink-700 text-white text-3xl font-bold appearance-none border-none focus:outline-none focus:ring-0"
            v-model="price">
        <!-- increase button -->
        <button
            @click="incPrice"
            class="w-32 h-32 text-3xl text-white bg-pink-700 hover:bg-pink-500"
        >
          <i class="fas fa-plus"></i>
        </button>
      </div>

      <div class="flex justify-center">
        <!-- Save button -->
        <button
            @click="savePrice"
            class="w-32 h-32 text-3xl text-white bg-pink-700 hover:bg-pink-500"
        >
          <i class="fas fa-check"></i>
        </button>
        <!-- Close button -->
        <button
            @click="$emit('close')"
            class="w-32 h-32 text-3xl text-white bg-pink-700 hover:bg-pink-500"
        >
          <i class="fas fa-x"></i>
        </button>
      </div>
    </div>
    </div>
  </div>
</template>

<style scoped>
/* Custom checkbox style */
input[type="checkbox"] {
  appearance: none;
  -webkit-appearance: none;
  border: 2px solid white;
}

input[type="checkbox"]:checked {
  background-color: #d946ef; /* pink-500 */
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='white'%3E%3Cpath d='M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z'/%3E%3C/svg%3E");
  background-position: center;
  background-repeat: no-repeat;
  background-size: 70%;
}

.currency-selection {
  max-height: 200px;
  overflow-y: auto;
}
</style>