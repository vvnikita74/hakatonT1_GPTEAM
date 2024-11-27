import { ChangeEvent } from 'react'

export default function StyleInput({
	handleChange,
	objKey = '',
	label = '',
	defaultValue = 'white',
	className = ''
}: {
	handleChange: (event: ChangeEvent<HTMLInputElement>) => void
	objKey: string
	label: string
	defaultValue: string
	className: string
}) {
	return (
		<div
			className={`flex flex-row items-center bg-indigo-500 p-2 text-white
				${className}`}>
			<span className='base-text'>{label}</span>
			<div className='relative ml-3 w-fit'>
				<input
					type='color'
					data-key={objKey}
					data-type='color'
					onChange={handleChange}
					className='absolute left-0 top-0 z-10 h-full w-full cursor-pointer opacity-0'
				/>
				<span
					className='block h-5 w-5'
					style={{ backgroundColor: defaultValue }}
					id={objKey}
				/>
			</div>
		</div>
	)
}
