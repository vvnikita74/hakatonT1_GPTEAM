import Spinner from 'components/icons/spinner'
import { useStatusContext } from 'components/context'

export default function Loader() {
	const { pending } = useStatusContext()

	return (
		<div
			className={`fixed left-0 top-0 size-full items-center justify-center
				bg-black/50 ${pending ? 'flex' : 'hidden'}`}>
			<Spinner className='size-10 fill-indigo-500 text-white' />
		</div>
	)
}
