import { useSetterContext } from 'components/context'
import ColorInput from 'components/form/color-input'
import { ChangeEvent } from 'react'

import type Styles from 'types/style'
import NumberInput from './form/number-input'
import debounce from 'utils/debounce'

export default function StyleManager({ className = '' }) {
	const { setStyles } = useSetterContext()

	const handleColorChange = debounce(
		(event: ChangeEvent<HTMLInputElement>) => {
			const { target } = event

			if (target) {
				const { value, dataset } = target
				const { key, type } = dataset

				switch (type) {
					case 'color': {
						const colorSpan = document.getElementById(key)
						if (colorSpan)
							colorSpan.style.backgroundColor = value

						setStyles((prev: Styles) => ({
							...prev,
							[key]: value
						}))

						break
					}
					case 'number': {
						setStyles((prev: Styles) => ({
							...prev,
							[key]: Number(value)
						}))

						break
					}
				}
			}
		},
		375
	)

	return (
		<div className={`flex w-fit flex-col ${className}`}>
			<ColorInput
				defaultValue='black'
				objKey='textColor'
				label='Цвет текста'
				handleChange={handleColorChange}
				className='mt-3 justify-between first:mt-0'
			/>
			<ColorInput
				defaultValue='white'
				objKey='backgroundColor'
				label='Цвет фона'
				handleChange={handleColorChange}
				className='mt-3 justify-between first:mt-0'
			/>
			<ColorInput
				defaultValue={'gray'}
				label='Цвет фона поля ввода'
				objKey='inputBackgroundColor'
				handleChange={handleColorChange}
				className='mt-3 justify-between first:mt-0'
			/>
			<NumberInput
				defaultValue={12}
				label='Оступы, px'
				objKey='containerPadding'
				handleChange={handleColorChange}
				className='mt-3 justify-between first:mt-0'
			/>
		</div>
	)
}
