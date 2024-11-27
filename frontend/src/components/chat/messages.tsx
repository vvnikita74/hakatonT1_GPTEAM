import { ChatMessage, useMessageContext } from './context'

export default function Messages({ className = '' }) {
	const messages: ChatMessage[] = useMessageContext() || []

	return (
		<div
			className={`flex h-full flex-col overflow-scroll
				rounded-[var(--containerRadius,12px)] border
				border-[color:var(--textColor,#000000)]
				p-[calc(var(--containerPadding,18px))] ${className}`}>
			{messages.map(({ isUser, text, pending }, index) => (
				<div
					key={text + index}
					className={`base-text ${isUser ? 'self-end' : ''}
					${pending ? 'opacity-50' : ''}`}>
					{text}
					{pending && 'Думаю...'}
				</div>
			))}
		</div>
	)
}
