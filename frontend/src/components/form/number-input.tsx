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
			className={`base-padding flex flex-row items-center rounded-xl bg-indigo-500
				text-white ${className}`}>
			<label className='base-text'>{label}</label>
			<input
				type='number'
				data-key={objKey}
				data-type='number'
				defaultValue={defaultValue}
				onChange={handleChange}
				className='base-text ml-5 h-7 w-7 bg-black text-center transition-colors
					focus:bg-opacity-70'
			/>
		</div>
	)
}
