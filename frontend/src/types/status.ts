export default interface Status {
	saved: boolean
	pending: boolean
}

export const defaultStatus: Status = {
	saved: false,
	pending: false
}
