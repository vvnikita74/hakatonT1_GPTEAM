import { memo, InputHTMLAttributes } from 'react'

const TextInput = memo(
	({
		name = '',
		type = 'text',
		label = '',
		placeholder = '',
		error = '',
		inputProps = {}
	}: {
		name?: string
		type?: string
		label?: string
		placeholder?: string
		error?: string
		inputProps?: InputHTMLAttributes<HTMLInputElement>
	}) => {
		return (
			<label
				className='relative block caret-black transition-colors'
				htmlFor={name}>
				{label ? (
					<span className='base-text mb-2 block px-2.5'>
						{label}
					</span>
				) : (
					<span className='sr-only'>{placeholder || name}</span>
				)}
				<input
					name={name}
					type={type}
					placeholder={placeholder}
					className={`base-text base-padding block rounded-xl border transition-colors
						${error ? 'border-red-500 placeholder:text-red-500 focus:border-red-500' : 'border-indigo-500 focus:border-indigo-200'}`}
					{...inputProps}
				/>
				<span
					className={`block text-right text-sm text-red-500 transition-[opacity,height]
						${error ? 'h-5 opacity-100' : 'h-0 opacity-0'}`}
					id='error-text'>
					{error || ''}
				</span>
			</label>
		)
	}
)

export default TextInput
