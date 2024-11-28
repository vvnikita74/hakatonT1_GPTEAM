import { createContext, useState, useContext } from 'react'

const MessagesContext = createContext(null)
const SetMessagesContext = createContext(null)

export const useMessageContext = () => {
	return useContext(MessagesContext)
}

export const useSetMessageContext = () => {
	return useContext(SetMessagesContext)
}

export interface ChatMessage {
	isUser: boolean
	text: string
	pending: boolean
}

export default function ChatContextProvider({
	children = null
}) {
	const [messages, setMessages] = useState<ChatMessage[]>([
		{
			isUser: true,
			text: 'Привет, сколько мне лет?',
			pending: false
		},
		{
			isUser: false,
			text: 'Привет, 22',
			pending: false
		}
	])

	return (
		<SetMessagesContext.Provider value={setMessages}>
			<MessagesContext.Provider value={messages}>
				{children}
			</MessagesContext.Provider>
		</SetMessagesContext.Provider>
	)
}
