/** @type {import('tailwindcss').Config} */

import defaultTheme from 'tailwindcss/defaultTheme'

export const content = ['./src/**/*.{js,ts,jsx,tsx,mdx}']

export const theme = {
	extend: {
		colors: {
			indigo: {
				100: '#d7e6f2',
				200: '#b0cde5',
				300: '#88b3d8',
				400: '#619acb',
				500: '#3981be',
				600: '#2e6798',
				700: '#224d72',
				800: '#17344c',
				900: '#0b1a26'
			}
		},
		screens: {
			'2xs': '360px',
			xs: '480px',
			...defaultTheme.screens
		}
	}
}
