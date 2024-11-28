export default function DBAddForm({ className = '' }) {
	return (
		<form className={`${className} flex flex-col`}>
			<span className='title-text text-center'>
				Добавление базы данных
			</span>
		</form>
	)
}
