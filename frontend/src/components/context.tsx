import {
	createContext,
	useState,
	useMemo,
	useContext
} from 'react'
import Status, { defaultStatus } from 'types/status'

import type Styles from 'types/style'
import { defaultStyles } from 'types/style'

const FilesContext = createContext(null)
const StylesContext = createContext(null)
const StatusContext = createContext(null)
const SettersContext = createContext(null)

export function useSetterContext() {
	return useContext(SettersContext)
}

export function useFilesContext() {
	return useContext(FilesContext)
}

export function useStylesContext() {
	return useContext(StylesContext)
}

export function useStatusContext() {
	return useContext(StatusContext)
}

export default function ContextProvider({ children }) {
	const [files, setFiles] = useState<File[]>([])
	const [styles, setStyles] = useState<Styles>(defaultStyles)
	const [status, setStatus] = useState<Status>(defaultStatus)

	const setters = useMemo(
		() => ({ setFiles, setStyles, setStatus }),
		[setFiles, setStyles]
	)

	return (
		<SettersContext.Provider value={setters}>
			<StatusContext.Provider value={status}>
				<FilesContext.Provider value={files}>
					<StylesContext.Provider value={styles}>
						{children}
					</StylesContext.Provider>
				</FilesContext.Provider>
			</StatusContext.Provider>
		</SettersContext.Provider>
	)
}
