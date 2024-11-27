import { useRef } from 'react'

const useLoader = () => {
	const btnRef = useRef(null)

	const toggleLoader = (isLoading: boolean) => {
		const btn = btnRef.current
		if (btn) {
			btn.classList.toggle('loading')
			btn.disabled = isLoading
		}
	}

	return { btnRef, toggleLoader }
}

export default useLoader
