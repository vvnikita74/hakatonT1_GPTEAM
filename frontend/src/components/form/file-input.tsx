import { useSetterContext } from 'components/context'
import { ChangeEvent, MouseEvent } from 'react'

export default function FileInput() {
	const { setFiles } = useSetterContext()

	const handleClick = (event: MouseEvent<HTMLButtonElement>) => {
		const { currentTarget } = event

		if (currentTarget) {
			currentTarget.querySelector('input').click()
		}
	}

	const handleFileInput = (
		event: ChangeEvent<HTMLInputElement>
	) => {
		if (event.target.files) {
			setFiles((prev: File[]) => [
				...prev,
				...Array.from(event.target.files)
			])
		}
	}

	return (
		<button
			type='button'
			onClick={handleClick}
			className='base-text w-fit bg-indigo-500 p-2 text-white'>
			<input
				type='file'
				multiple
				hidden
				accept='.txt, .pdf, .doc, .docx, .csv, .xlsx'
				onChange={handleFileInput}
				className='pointer-events-none'
			/>
			Выберите файлы
		</button>
	)
}
