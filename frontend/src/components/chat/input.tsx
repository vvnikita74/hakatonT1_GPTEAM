import { FormEvent, useRef } from 'react'
import SendIcon from 'public/images/send.svg'
import Spinner from 'components/icons/spinner'

import {
	ChatMessage,
	useSetMessageContext
} from 'components/chat/context'

import useLoader from 'utils/use-loader'
import { API_URL } from 'utils/config'

export default function ChatInput({
	className = '',
	apiKey = ''
}) {
	const setMessages = useSetMessageContext()

	const { btnRef, toggleLoader } = useLoader()
	const inputRef = useRef(null)

	const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
		const { current } = inputRef
		let text = ''

		if (current) {
			text = current.value
			current.value = ''
		}

		event.preventDefault()
		toggleLoader(true)

		let newArr = []

		setMessages((prev: ChatMessage[]) => {
			const stateArr = [
				...prev,
				{ pending: false, text, isUser: true },
				{ pending: true, text: '', isUser: false }
			]
			newArr = [...stateArr]
			return stateArr
		})

		newArr.pop()

		fetch(`${API_URL}/chat`, {
			method: 'POST',
			headers: {
				Authorization: apiKey,
				'Content-type': 'application/json'
			},
			body: JSON.stringify({
				messages: newArr.map(item => ({
					role: item.isUser ? 'user' : 'assistant',
					content: item.text
				}))
			})
		})
			.then(res => res.json())
			.then(res => {
				setMessages((prev: ChatMessage[]) => {
					const stateArr = [...prev]
					stateArr.pop()
					return [
						...stateArr,
						{
							isUser: false,
							text: res.content,
							pending: false
						}
					]
				})

				toggleLoader(false)
			})
			.catch(() => {
				toggleLoader(false)
			})
	}

	return (
		<form
			className={`flex flex-row justify-between ${className}`}
			onSubmit={handleSubmit}>
			<input
				ref={inputRef}
				type='text'
				className='base-padding rounded-[var(--containerRadius,12px)] border
					border-[color:var(--borderColor,#000000)]
					caret-[var(--textColor,#000000)]'
			/>
			<button
				type='submit'
				className='btn-loader relative ml-2'
				ref={btnRef}>
				<span className='pointer-events-none flex text-inherit transition-opacity'>
					<SendIcon className='pointer-events-none text-[color:var(--textColor,#000000)]' />
				</span>
				<Spinner
					className='absolute left-[calc(50%-0.75rem)] top-[calc(50%-0.75rem)] size-6
						fill-black text-indigo-500'
				/>
			</button>
		</form>
	)
}
