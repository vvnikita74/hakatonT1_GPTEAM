/* eslint-disable prettier/prettier */
import { useEffect, useRef } from 'react'
import {
	useFilesContext,
	useSetterContext,
	useStylesContext
} from 'components/context'

import { API_URL } from 'utils/config'
import useAuthHeader from 'react-auth-kit/hooks/useAuthHeader'

export default function SaveButton() {
	const authHeader = useAuthHeader()
	const btnRef = useRef(null)

	const files = useFilesContext()
	const styles = useStylesContext()
	const { setStatus } = useSetterContext()

	useEffect(() => {
		return () => {
			const { current } = btnRef
			if (current) {
				current.classList.remove('opacity-0')
			}
		}
	}, [files, styles])

	const handleClick = async () => {
		setStatus({ saved: false, pending: true })
		const formData = new FormData()

		formData.append('files', files)
		formData.append('styles', JSON.stringify(styles))

		try {
			const req = await fetch(`${API_URL}/assistant`, {
				method: 'POST',
				headers: {
					'authorization': authHeader,
					'content-Type': 'multipart/form-data'
					// 'Access-Control-Allow-Origin':
					// 	'http://172.20.10.2:8080',
					// Origin: 'http://172.20.10.2:8080',
					// 'Access-Control-Allow-Methods':
					// 	'POST, GET, OPTIONS, DELETE, PUT',
					// 'Access-Control-Allow-Headers':
					// 	'x-requested-with, Content-Type, Origin, Authorization, Accept',
					// 'Content-Security-Policy':
					// 	'connect-src http://172.20.10.3:8080'
				},
				body: formData
			})

			const res = await req.json()
			console.log(res)
		} catch (error) {
			console.error(error)
			setStatus({ saved: false, pending: false })
		}
	}

	return (
		<button
			type='button'
			onClick={handleClick}
			ref={btnRef}
			className='base-text base-padding fixed bottom-5 right-5 bg-indigo-500
				opacity-0 transition-opacity lg:absolute'>
			Сохранить
		</button>
	)
}
