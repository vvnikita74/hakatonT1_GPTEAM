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
	const { setPending } = useSetterContext()

	useEffect(() => {
		return () => {
			const { current } = btnRef
			if (current) {
				current.classList.remove('opacity-0')
			}
		}
	}, [files, styles])

	const handleClick = async () => {
		setPending(true)

		const formData = new FormData()

		formData.append('files[]', files)
		formData.append('styles', JSON.stringify(styles))

		const req = await fetch(`${API_URL}/assistant`, {
			method: 'POST',
			headers: {
				Authorization: authHeader,
				'Content-Type': 'multipart/form-data'
			},
			body: formData
		})

		const res = await req.json()
		console.log(res)
	}

	return (
		<button
			type='button'
			onClick={handleClick}
			ref={btnRef}
			className='base-text base-padding absolute bottom-5 right-5 bg-indigo-500
				opacity-0 transition-opacity'>
			Сохранить
		</button>
	)
}
