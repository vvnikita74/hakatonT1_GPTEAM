import {
	createContext,
	useState,
	useMemo,
	useContext
} from 'react'

import Database, { defaultDbs } from 'types/database'
import Status, { defaultStatus } from 'types/status'
import Styles, { defaultStyles } from 'types/style'

const FilesContext = createContext(null)
const DBsContext = createContext(null)
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

export function useDbsContext() {
	return useContext(StatusContext)
}

export default function ContextProvider({ children }) {
	const [files, setFiles] = useState<File[]>([])
	const [styles, setStyles] = useState<Styles>(defaultStyles)
	const [status, setStatus] = useState<Status>(defaultStatus)
	const [dbs, setDbs] = useState<Database[]>(defaultDbs)

	const setters = useMemo(
		() => ({ setFiles, setStyles, setStatus, setDbs }),
		[setFiles, setStyles, setStatus, setDbs]
	)

	return (
		<SettersContext.Provider value={setters}>
			<StatusContext.Provider value={status}>
				<DBsContext.Provider value={dbs}>
					<FilesContext.Provider value={files}>
						<StylesContext.Provider value={styles}>
							{children}
						</StylesContext.Provider>
					</FilesContext.Provider>
				</DBsContext.Provider>
			</StatusContext.Provider>
		</SettersContext.Provider>
	)
}
