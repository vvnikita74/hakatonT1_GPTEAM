import { ChatMessage, useMessageContext } from './context'

export default function Messages({ className = '' }) {
	const messages: ChatMessage[] = useMessageContext() || []
	console.log(messages)
	return (
		<div
			className={`flex h-full flex-col overflow-scroll
				rounded-[var(--containerRadius,12px)] border
				border-[color:var(--borderColor,#000000)]
				p-[calc(var(--containerPadding,18px))] ${className}`}>
			{messages.map(({ isUser, text, pending }, index) => (
				<div
					key={text + index}
					className={`base-text mt-2 first:mt-0 ${isUser ? 'self-end' : ''}
					${pending ? 'opacity-50' : ''}`}>
					{text}
					{pending && 'Думаю...'}
				</div>
			))}
		</div>
	)
}
