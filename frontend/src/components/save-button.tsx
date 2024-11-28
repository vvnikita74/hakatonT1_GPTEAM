/* eslint-disable prettier/prettier */
import { useEffect, useRef } from 'react'
import useAuthHeader from 'react-auth-kit/hooks/useAuthHeader'

import {
	useFilesContext,
	useSetterContext,
	useStylesContext
} from 'components/context'

import { API_URL } from 'utils/config'

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

		files.forEach((item: File) => {
			formData.append('files', item, item.name)
		})

		formData.append('styles', JSON.stringify(styles))
		formData.append("text", "Я отправил тебе файлы, тебе необходимо отвечать на вопросы пользователей используя информацию из базы знаний. Ты отвечаешь пользователю, учти это.");

		fetch(`${API_URL}/assistant`, {
			method: 'POST',
			headers: {
				Authorization: authHeader
			},
			body: formData,
			redirect: 'follow'
		}).catch(() => {})

		setStatus({ saved: false, pending: false })		
	}

	return (
		<button
			type='button'
			onClick={handleClick}
			ref={btnRef}
			className='base-text base-padding rounded-xl fixed bottom-5 right-5 bg-indigo-500
				opacity-0 transition-opacity lg:absolute text-white'>
			Сохранить
		</button>
	)
}
