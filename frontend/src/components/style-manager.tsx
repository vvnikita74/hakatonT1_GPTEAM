import { useSetterContext } from 'components/context'
import ColorInput from 'components/form/color-input'
import { ChangeEvent } from 'react'

import type Styles from 'types/style'
import NumberInput from './form/number-input'
import debounce from 'utils/debounce'

export default function StyleManager({
	className = '',
	initialStyles
}: {
	className: string
	initialStyles: Styles
}) {
	const { setStyles } = useSetterContext()

	const handleChange = debounce(
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
		250
	)

	return (
		<div className={`flex w-fit flex-col ${className}`}>
			<ColorInput
				defaultValue={initialStyles.textColor}
				objKey='textColor'
				label='Цвет текста'
				handleChange={handleChange}
				className='mt-3 justify-between first:mt-0'
			/>
			<ColorInput
				defaultValue={initialStyles.backgroundColor}
				objKey='backgroundColor'
				label='Цвет фона'
				handleChange={handleChange}
				className='mt-3 justify-between first:mt-0'
			/>
			<ColorInput
				defaultValue={initialStyles.borderColor}
				objKey='borderColor'
				label='Цвет обводки'
				handleChange={handleChange}
				className='mt-3 justify-between first:mt-0'
			/>
			<NumberInput
				defaultValue={initialStyles.containerPadding}
				label='Оступы, px'
				objKey='containerPadding'
				handleChange={handleChange}
				className='mt-3 justify-between first:mt-0'
			/>
			<NumberInput
				defaultValue={initialStyles.containerRadius}
				label='Закругление, px'
				objKey='containerRadius'
				handleChange={handleChange}
				className='mt-3 justify-between first:mt-0'
			/>
		</div>
	)
}
