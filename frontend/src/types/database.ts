export default interface Database {
	host: string
	port: number
	username: string
	password: string
	database: string
	query: string
}

export const defaultDbs = []
