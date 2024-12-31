/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        sans: ['Jura', 'sans-serif'], // Replace 'Roboto' with your font name
      },
    },
  },
  plugins: [
      require('@tailwindcss/forms'),
      require('tailwind-scrollbar'),

  ],
}

