import LoginForm from 'components/auth/login-form'

export default function LoginPage() {
	return (
		<section
			className='relative flex size-full flex-col items-center justify-center
				overflow-hidden p-4'>
			<div
				className='absolute left-4 top-4 flex w-[calc(100%-2rem)] flex-col
					items-center'>
				<h2 className='text-xl font-medium sm:text-2xl'>
					Авторизация
				</h2>
			</div>
			<LoginForm className='w-full max-w-96' />
		</section>
	)
}
