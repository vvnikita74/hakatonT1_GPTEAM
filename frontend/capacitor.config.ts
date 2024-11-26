import type { CapacitorConfig } from '@capacitor/cli'

const config: CapacitorConfig = {
	appId: 'com.app.id',
	appName: 'appName',
	webDir: 'dist',
	// server: {
	// 	url: 'http://192.168.0.176:3000',
	// 	cleartext: true,
	// },
	plugins: {
		SafeArea: {
			enabled: true,
			customColorsForSystemBars: true,
			statusBarColor: '#000000',
			statusBarContent: 'light',
			navigationBarColor: '#000000',
			navigationBarContent: 'light',
			offset: 0
		}
	}
}

export default config
