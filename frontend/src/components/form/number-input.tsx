import { ChangeEvent } from 'react'

export default function NumberInput({
	label,
	defaultValue,
	handleChange,
	objKey,
	className
}: {
	handleChange: (event: ChangeEvent<HTMLInputElement>) => void
	label: string
	defaultValue: number
	objKey: string
	className: string
}) {
	return (
		<div
			className={`flex flex-row items-center bg-indigo-500 p-2 ${className}`}>
			<label className='base-text'>{label}</label>
			<input
				type='number'
				data-key={objKey}
				data-type='number'
				defaultValue={defaultValue}
				onChange={handleChange}
				className='base-text ml-5 h-6 w-6 bg-black text-center text-white
					transition-colors focus:bg-opacity-70'
			/>
		</div>
	)
}
