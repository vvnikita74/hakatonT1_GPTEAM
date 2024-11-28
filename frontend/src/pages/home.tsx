import Chat from 'components/chat-widget'
import ContextProvider from 'components/context'
import FileManager from 'components/file-manager'
import Loader from 'components/loader'
import SaveButton from 'components/save-button'
import StyleManager from 'components/style-manager'

export default function Home() {
	return (
		<ContextProvider>
			<main
				className='relative flex size-full flex-col p-5 lg:grid
					lg:grid-cols-[60%_40%]'>
				<div className='flex flex-col justify-between'>
					<div className='flex flex-col'>
						<h2 className='title-text mb-4 block'>
							Знания ассистента
						</h2>
						<FileManager />
					</div>
					<div className='mt-6 flex flex-col lg:mt-0'>
						<h2 className='title-text mb-4 block'>
							Внешний вид ассистента
						</h2>
						<StyleManager />
					</div>
				</div>
				<div className='mt-6 flex flex-col items-center justify-center lg:mt-0'>
					<Chat className='h-96 w-full lg:h-2/3' />
				</div>
				<SaveButton />
			</main>
			<Loader />
		</ContextProvider>
	)
}
