import './index.css'

import ReactDOM from 'react-dom/client'

import {
	createBrowserRouter,
	createRoutesFromElements,
	Route,
	RouterProvider
} from 'react-router-dom'

import createStore from 'react-auth-kit/createStore'
import AuthProvider from 'react-auth-kit'
import AuthOutlet from '@auth-kit/react-router/AuthOutlet'

import LoginPage from 'pages/login'
import Home from 'pages/home'
import { API_URL } from 'utils/config'

const store = createStore({
	authName: '_auth',
	authType: 'cookie',
	cookieDomain: window.location.hostname,
	cookieSecure: window.location.protocol === 'https:'
})

const getAuthStore = () => {
	let userState = null
	let authHeader = null

	try {
		const { userState: userStateStore, auth } = store.tokenObject
			.value || {
			userState: null,
			auth: null
		}

		if (!auth?.type || !auth?.token) return null

		userState = userStateStore
		authHeader = `${auth.type} ${auth.token}`
	} catch {
		/* empty */
	}

	return { userState, authHeader }
}

const router = createBrowserRouter(
	createRoutesFromElements(
		<>
			<Route path='/login' element={<LoginPage />} />
			<Route
				element={<AuthOutlet fallbackPath='/login' />}
				errorElement={null}>
				<Route
					index
					element={<Home />}
					loader={async () => {
						const { authHeader } = getAuthStore() || {
							authHeader: null
						}
						if (!authHeader) return null

						return fetch(`${API_URL}/assistant`, {
							headers: {
								Authorization: authHeader,
								'Access-Control-Allow-Origin':
									'http://localhost:8081',
								Origin: 'http://localhost:8081',
								'Access-Control-Allow-Methods':
									'POST, GET, OPTIONS, DELETE, PUT',
								'Access-Control-Allow-Headers':
									'x-requested-with, Content-Type, Origin, Authorization, Accept',
								'Content-Security-Policy':
									'connect-src http://172.20.10.3:8080'
							}
						}).catch(() => null)
					}}
				/>
			</Route>
		</>
	)
)

ReactDOM.createRoot(document.getElementById('root')!).render(
	<AuthProvider store={store}>
		<RouterProvider router={router} />
	</AuthProvider>
)
