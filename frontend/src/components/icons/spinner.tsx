import SpinnerIcon from 'public/images/spinner.svg'

export default function Spinner({ className = '', ...props }) {
	return (
		<figure
			role='status'
			className='pointer-events-none transition-opacity'
			{...props}>
			<SpinnerIcon className={`${className} animate-spin`} />
			<span className='sr-only'>Загрузка...</span>
		</figure>
	)
}
