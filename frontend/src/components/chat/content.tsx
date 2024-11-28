import ChatInput from 'components/chat/input'
import Messages from 'components/chat/messages'

export default function ContentView({ className = '' }) {
	return (
		<div
			className={`flex size-full flex-col justify-between ${className}`}>
			<Messages />
			<ChatInput className='mt-4' />
		</div>
	)
}
