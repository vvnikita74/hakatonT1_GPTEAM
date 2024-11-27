import {
	useFilesContext,
	useIsSaveContext,
	useStylesContext
} from 'components/context'
import { useEffect, useRef } from 'react'

export default function StylesListener({ containerID = '' }) {
	const styles = useStylesContext()
	const files = useFilesContext()
	const isSaved = useIsSaveContext()

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
				files.length === 0 || !isSaved
			)
		}
	}, [files, isSaved])

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
