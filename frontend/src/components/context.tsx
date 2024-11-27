import {
	createContext,
	useState,
	useMemo,
	useContext
} from 'react'

import type Styles from 'types/style'

const FilesContext = createContext(null)
const StylesContext = createContext(null)
const PendingContext = createContext(null)
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

export default function ContextProvider({ children }) {
	const [files, setFiles] = useState<File[]>([])
	const [styles, setStyles] = useState<Styles>({
		backgroundColor: 'white',
		containerPadding: 12,
		inputPadding: 12,
		textColor: 'black'
	})
	const [pending, setPending] = useState<boolean>(false)

	const setters = useMemo(
		() => ({ setFiles, setStyles, setPending }),
		[setFiles, setStyles]
	)

	return (
		<SettersContext.Provider value={setters}>
			<PendingContext.Provider value={pending}>
				<FilesContext.Provider value={files}>
					<StylesContext.Provider value={styles}>
						{children}
					</StylesContext.Provider>
				</FilesContext.Provider>
			</PendingContext.Provider>
		</SettersContext.Provider>
	)
}
