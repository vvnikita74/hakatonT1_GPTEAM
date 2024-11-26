/* eslint-disable @typescript-eslint/no-require-imports */
import path from 'path'
import webpack, { Configuration } from 'webpack'
import HtmlWebpackPlugin from 'html-webpack-plugin'
import ForkTsCheckerWebpackPlugin from 'fork-ts-checker-webpack-plugin'
import ESLintPlugin from 'eslint-webpack-plugin'
import { TsconfigPathsPlugin } from 'tsconfig-paths-webpack-plugin'

import 'webpack-dev-server'

const webpackConfig = (env): Configuration => ({
	entry: './src/index.tsx',
	...(env.production || !env.development
		? {}
		: { devtool: 'eval-source-map' }),
	resolve: {
		extensions: ['.ts', '.tsx', '.js', '.jsx'],
		plugins: [new TsconfigPathsPlugin()]
	},
	output: {
		path: path.join(__dirname, '/dist'),
		filename: 'build.js'
	},
	devServer: {
		historyApiFallback: true
	},
	module: {
		rules: [
			{
				test: /\.(ts|tsx)$/,
				loader: 'ts-loader',
				options: {
					transpileOnly: true
				},
				exclude: /dist/
			},
			{
				test: /\.(js|jsx)$/,
				exclude: /node_modules/,
				use: {
					loader: 'babel-loader',
					options: {
						presets: ['@babel/preset-env', '@babel/preset-react']
					}
				}
			},
			{
				test: /\.css$/,
				use: ['style-loader', 'css-loader', 'postcss-loader']
			}
		]
	},
	plugins: [
		new HtmlWebpackPlugin({
			template: './public/index.html'
		}),
		new webpack.DefinePlugin({
			'process.env.PRODUCTION':
				env.production || !env.development,
			'process.env.NAME': JSON.stringify(
				require('./package.json').name
			),
			'process.env.VERSION': JSON.stringify(
				require('./package.json').version
			)
		}),
		new ForkTsCheckerWebpackPlugin(),
		new ESLintPlugin({ files: './src/**/*.{ts,tsx,js,jsx}' })
	]
})

export default webpackConfig
