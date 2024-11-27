import ContextProvider from 'components/context'
import FileManager from 'components/file-manager'
import SaveButton from 'components/save-button'
import StyleManager from 'components/style-manager'

export default function Home() {
	return (
		<ContextProvider>
			<main className='relative grid size-full grid-cols-[40%_60%] p-5'>
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
				<SaveButton />
			</main>
		</ContextProvider>
	)
}
