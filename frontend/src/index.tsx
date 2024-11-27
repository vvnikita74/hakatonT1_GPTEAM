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
// import AuthOutlet from '@auth-kit/react-router/AuthOutlet'

import LoginPage from 'pages/login'
import Home from 'pages/home'

const store = createStore({
	authName: '_auth',
	authType: 'cookie',
	cookieDomain: window.location.hostname,
	cookieSecure: window.location.protocol === 'https:'
})

const router = createBrowserRouter(
	createRoutesFromElements(
		<>
			<Route path='/login' element={<LoginPage />} />
			{/* <Route
				element={<AuthOutlet fallbackPath='/login' />}
				errorElement={null}> */}
			<Route index element={<Home />} />
			{/* </Route> */}
		</>
	)
)

ReactDOM.createRoot(document.getElementById('root')!).render(
	<AuthProvider store={store}>
		<RouterProvider router={router} />
	</AuthProvider>
)
