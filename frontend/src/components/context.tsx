import {
	createContext,
	useState,
	useMemo,
	useContext
} from 'react'

import type Styles from 'types/style'
import { defaultStyles } from 'types/style'

const FilesContext = createContext(null)
const StylesContext = createContext(null)
const IsSavedContext = createContext(null)
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

export function useIsSaveContext() {
	return useContext(IsSavedContext)
}

export default function ContextProvider({ children }) {
	const [files, setFiles] = useState<File[]>([])
	const [styles, setStyles] = useState<Styles>(defaultStyles)
	const [isSaved, setIsSaved] = useState<boolean>(false)

	const setters = useMemo(
		() => ({ setFiles, setStyles, setIsSaved }),
		[setFiles, setStyles]
	)

	return (
		<SettersContext.Provider value={setters}>
			<IsSavedContext.Provider value={isSaved}>
				<FilesContext.Provider value={files}>
					<StylesContext.Provider value={styles}>
						{children}
					</StylesContext.Provider>
				</FilesContext.Provider>
			</IsSavedContext.Provider>
		</SettersContext.Provider>
	)
}
