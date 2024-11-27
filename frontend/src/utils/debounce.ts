/**
 * Creates a debounced function that delays invoking the provided function until after the specified wait time has elapsed since the last time the debounced function was invoked.
 *
 * @param {Function} func - The function to debounce.
 * @param {number} wait - The number of milliseconds to delay.
 * @returns {Function} - A new debounced function.
 */
export default function debounce<
	T extends (...args: unknown[]) => unknown
>(func: T, wait: number): (...args: Parameters<T>) => void {
	let timeout: ReturnType<typeof setTimeout>

	return (...args: Parameters<T>) => {
		clearTimeout(timeout)
		timeout = setTimeout(() => {
			func(...args)
		}, wait)
	}
}
