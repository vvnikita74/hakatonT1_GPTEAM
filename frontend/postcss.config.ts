export const plugins = {
	tailwindcss: {},
	autoprefixer: {},
	'postcss-flexbugs-fixes': {},
	cssnano: {},
	'postcss-preset-env': {
		stage: 2,
		features: {
			'nesting-rules': true,
			'gap-properties': true,
			'custom-properties': true,
			'custom-media-queries': true
		}
	}
}
