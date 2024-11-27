import { useSetterContext } from 'components/context'
import ColorInput from 'components/form/color-input'
import { ChangeEvent } from 'react'

import type Styles from 'types/style'

export default function StyleManager({ className = '' }) {
	const { setStyles } = useSetterContext()

	const handleChange = (
		event: ChangeEvent<HTMLInputElement>
	) => {
		const { currentTarget } = event

		if (currentTarget) {
			const { value, dataset } = currentTarget
			const { key } = dataset

			const colorSpan = document.getElementById(key)
			console.log(colorSpan)
			if (colorSpan) colorSpan.style.backgroundColor = value

			setStyles((prev: Styles) => ({
				...prev,
				[key]: currentTarget.value
			}))
		}
	}

	return (
		<div className={`flex flex-col ${className}`}>
			<ColorInput
				defaultValue='black'
				objKey='textColor'
				label='Цвет текста'
				handleChange={handleChange}
				className='mt-3 w-fit first:mt-0'
			/>
			<ColorInput
				defaultValue='white'
				objKey='textColor'
				label='Цвет фона'
				handleChange={handleChange}
				className='mt-3 w-fit first:mt-0'
			/>
		</div>
	)
}
