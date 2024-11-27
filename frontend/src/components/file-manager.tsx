import FileInput from 'components/form/file-input'
import FilesView from 'components/views/files-view'

export default function FileManager({ className = '' }) {
	return (
		<div className={`flex w-fit flex-col ${className}`}>
			<FileInput />
			<FilesView className='mt-4' />
		</div>
	)
}
