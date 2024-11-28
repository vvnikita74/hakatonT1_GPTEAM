import useAuthUser from 'react-auth-kit/hooks/useAuthUser'

import StylesListener from 'components/chat/chat-listener'
import ChatContextProvider from 'components/chat/context'
import ContentView from 'components/chat/content'

export default function Chat({ className = '' }) {
	const user = useAuthUser() as { name: string }

	return (
		<div
			id='assistant-container'
			className={`relative flex flex-col rounded-[var(--containerRadius,12px)]
				bg-[var(--backgroundColor,#ffffff)]
				p-[var(--containerPadding,18px)]
				text-[color:var(--textColor,#000000)] transition-all ${className}`}>
			<StylesListener containerID='assistant-container' />
			<div className='base-text text-left'>
				{user?.name || 'Чат-ассистент'} (предварительный
				просмотр)
			</div>
			{/* <div
				className='disabled-text title-text absolute left-0 top-0 size-full
					items-center justify-center bg-black/50 text-center text-red-500'>
				<span>
					Для доступа необходимо загрузить знания
					и&nbsp;сохранить изменения
				</span>
			</div> */}
			<ChatContextProvider>
				<ContentView className='mt-4' />
			</ChatContextProvider>
		</div>
	)
}
