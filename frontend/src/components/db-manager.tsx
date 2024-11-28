import DBAddForm from 'components/db-add-form'
import toggleModal from 'utils/toggle-modal'

export default function DBManager({ className = '' }) {
	const toggleDBModal = () => {
		toggleModal('db-add-modal')
	}

	return (
		<div className={`flex flex-row ${className}`}>
			<button
				type='button'
				onClick={toggleDBModal}
				className='base-padding base-text w-fit rounded-xl bg-indigo-500 text-white'>
				Подключение БД
			</button>
			<div
				id='db-add-modal'
				className='hidden-opacity fixed left-0 top-0 z-10 flex size-full
					items-center justify-center transition-opacity'>
				<button
					type='button'
					onClick={toggleDBModal}
					className='absolute left-0 top-0 size-full bg-black/50'
				/>
				<DBAddForm className='relative z-10 rounded-xl bg-white px-6 py-3 text-black' />
			</div>
		</div>
	)
}
