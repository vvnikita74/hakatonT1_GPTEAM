import { useFilesContext } from 'components/context'
import FileIcon from 'public/images/file.svg'

export default function FilesView({ className = '' }) {
	const files = useFilesContext()

	if (files.length === 0) return null

	return (
		<div className={className}>
			<ul>
				{files.map((file: File) => (
					<li
						key={file.name}
						className='base-text mt-2 flex flex-row items-center first:mt-0'>
						<FileIcon className='mr-2' />
						{file.name}
					</li>
				))}
			</ul>
		</div>
	)
}
