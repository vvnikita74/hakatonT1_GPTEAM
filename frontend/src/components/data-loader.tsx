import { useSetterContext } from 'components/context'
import { useEffect } from 'react'

export default function DataLoader({ files, style }) {
	const { setFiles, setStyles, setDbs } = useSetterContext()

	useEffect(() => {
		setFiles(files)
		setDbs([])
		setStyles(style)
	}, [setDbs, setFiles, setStyles, files, style])

	return null
}
