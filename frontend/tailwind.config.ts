/** @type {import('tailwindcss').Config} */

import defaultTheme from 'tailwindcss/defaultTheme'

export const content = ['./src/**/*.{js,ts,jsx,tsx,mdx}']

export const theme = {
	extend: {
		colors: {
			indigo: {
				100: '#cceff7',
				200: '#99dff0',
				300: '#66cee8',
				400: '#33bee1',
				500: '#00aed9',
				600: '#008bae',
				700: '#006882',
				800: '#004657',
				900: '#00232b'
			}
		},
		screens: {
			'2xs': '360px',
			xs: '480px',
			...defaultTheme.screens
		},
		transitionDuration: {
			DEFAULT: '300ms'
		}
	}
}
