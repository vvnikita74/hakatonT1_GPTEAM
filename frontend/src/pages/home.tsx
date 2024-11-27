import Chat from 'components/chat'
import ContextProvider from 'components/context'
import FileManager from 'components/file-manager'
import SaveButton from 'components/save-button'
import StyleManager from 'components/style-manager'

export default function Home() {
	return (
		<ContextProvider>
			<main className='relative grid size-full grid-cols-[60%_40%] p-5'>
				<div className='flex flex-col justify-between'>
					<div className='flex flex-col'>
						<h2 className='title-text mb-4 block'>
							Знания ассистента
						</h2>
						<FileManager />
					</div>
					<div className='flex flex-col'>
						<h2 className='title-text mb-4 block'>
							Внешний вид ассистента
						</h2>
						<StyleManager />
					</div>
				</div>
				<div className='flex flex-col items-center justify-center'>
					<Chat className='h-1/2 w-full' />
				</div>
				<SaveButton />
			</main>
		</ContextProvider>
	)
}
