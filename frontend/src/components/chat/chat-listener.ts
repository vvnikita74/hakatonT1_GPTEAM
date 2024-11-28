import {
	useFilesContext,
	useStatusContext,
	useStylesContext
} from 'components/context'
import { useEffect, useRef } from 'react'

export default function StylesListener({ containerID = '' }) {
	const styles = useStylesContext()
	const files = useFilesContext()
	const { saved } = useStatusContext()

	const containerRef = useRef(null)

	useEffect(() => {
		const container = document.getElementById(containerID)
		if (container) containerRef.current = container
	}, [containerID])

	useEffect(() => {
		const { current } = containerRef

		if (current) {
			current.classList.toggle(
				'chat-disabled',
				files.length === 0 || !saved
			)
		}
	}, [files, saved])

	useEffect(() => {
		const { current } = containerRef

		if (current) {
			Object.keys(styles).map(key => {
				current.style.setProperty(
					`--${key}`,
					typeof styles[key] === 'number'
						? `${styles[key]}px`
						: styles[key]
				)
			}, {})
		}
	}, [styles])

	return null
}
