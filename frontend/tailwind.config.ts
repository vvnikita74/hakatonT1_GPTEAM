/** @type {import('tailwindcss').Config} */

import defaultTheme from 'tailwindcss/defaultTheme'

export const content = ['./src/**/*.{js,ts,jsx,tsx,mdx}']

export const theme = {
	extend: {
		colors: {
			indigo: {
				100: '#cceefa',
				200: '#99ddf5',
				300: '#66ccf0',
				400: '#33bbeb',
				500: '#00aae6',
				600: '#0088b8',
				700: '#00668a',
				800: '#00445c',
				900: '#00222e'
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
