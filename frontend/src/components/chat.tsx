import { useStylesContext } from './context'

export default function Chat({ className = '' }) {
	const styles = useStylesContext()

	console.log(styles)

	return (
		<div
			id='assistant-container'
			className={`bg-[var(--backgroundColor,"#ffffff")] p-[--containerPadding]
				text-[color:var(--textColor,"#000000")] ${className}`}
			style={Object.keys(styles).reduce((acc, key) => {
				acc[`--${key}`] =
					typeof styles[key] === 'number'
						? `${styles[key]}px`
						: styles[key]
				return acc
			}, {})}>
			<span className='title-text text-center'>
				Чат-ассистент
			</span>
		</div>
	)
}
