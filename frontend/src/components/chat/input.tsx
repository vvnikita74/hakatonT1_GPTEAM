import { FormEvent } from 'react'
import {
	ChatMessage,
	useSetMessageContext
} from 'components/chat/context'
import SendIcon from 'public/images/send.svg'

export default function ChatInput({ className = '' }) {
	const setMessages = useSetMessageContext()

	const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
		console.log(event)
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
			<button type='submit' className='ml-2'>
				<SendIcon className='pointer-events-none text-[color:var(--textColor,#000000)]' />
			</button>
		</form>
	)
}
