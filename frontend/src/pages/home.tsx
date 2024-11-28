import { useLoaderData } from 'react-router'

import Chat from 'components/chat-widget'
import ContextProvider from 'components/context'
import DataLoader from 'components/data-loader'
import FileManager from 'components/file-manager'
// import DBManager from 'components/db-manager'
import Loader from 'components/loader'
import SaveButton from 'components/save-button'
import StyleManager from 'components/style-manager'
import Assistant from 'types/assistant'

import Styles, { defaultStyles } from 'types/style'

export default function Home() {
	const data = useLoaderData() as Assistant

	const isDataValid = Boolean(data?.apiKey)

	const dataStyle =
		(JSON.parse(data?.styles || '') as Styles) || defaultStyles
	const dataFile = data?.files || []

	return (
		<ContextProvider>
			{isDataValid && (
				<DataLoader style={dataStyle} files={dataFile} />
			)}
			<main
				className='relative flex size-full flex-col p-7 lg:grid
					lg:grid-cols-[60%_40%]'>
				<div className='flex flex-col justify-between'>
					<div className='flex flex-col'>
						<h2 className='title-text mb-4 block'>
							Знания ассистента
						</h2>
						<div className='flex flex-row'>
							<FileManager />
							{/* <DBManager className='ml-4 h-fit' /> */}
						</div>
					</div>
					<div className='mt-6 flex flex-col lg:mt-0'>
						<h2 className='title-text mb-4 block'>
							Внешний вид ассистента
						</h2>
						<StyleManager
							className=''
							initialStyles={dataStyle}
						/>
					</div>
				</div>
				<div className='mt-6 flex flex-col items-center justify-center lg:mt-0'>
					<Chat
						className='h-96 w-full lg:h-2/3'
						apiKey={data.apiKey}
					/>
				</div>
				<SaveButton />
			</main>
			<Loader />
		</ContextProvider>
	)
}
