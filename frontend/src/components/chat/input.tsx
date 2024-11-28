import { FormEvent } from 'react'
import SendIcon from 'public/images/send.svg'
import Spinner from 'components/icons/spinner'

import {
	ChatMessage,
	useSetMessageContext
} from 'components/chat/context'

import useLoader from 'utils/use-loader'

export default function ChatInput({ className = '' }) {
	const setMessages = useSetMessageContext()

	const { btnRef, toggleLoader } = useLoader()

	const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
		console.log(event)
		toggleLoader(true)

		setMessages((prev: ChatMessage[]) => [
			...prev,
			{ pending: true, text: '', isUser: false }
		])
	}

	return (
		<form
			className={`flex flex-row justify-between ${className}`}
			onSubmit={handleSubmit}>
			<input
				type='text'
				className='rounded-[var(--containerRadius,12px)] border
					border-[color:var(--textColor,#000000)] p-2
					caret-[var(--textColor,#000000)]'
			/>
			<button
				type='submit'
				className='btn-loader ml-2'
				ref={btnRef}>
				<span className='pointer-events-none text-inherit transition-opacity'>
					<SendIcon className='pointer-events-none text-[color:var(--textColor,#000000)]' />
				</span>
				<Spinner
					className='absolute left-[calc(50%-0.75rem)] top-[calc(50%-0.75rem)] size-6
						fill-black text-white'
				/>
			</button>
		</form>
	)
}
