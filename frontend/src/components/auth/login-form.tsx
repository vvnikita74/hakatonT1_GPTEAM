import { useCallback } from 'react'
import { useNavigate } from 'react-router-dom'

import useSignIn from 'react-auth-kit/hooks/useSignIn'

import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import { useForm } from 'react-hook-form'

import TextInput from 'components/form/text-input'
import Spinner from 'components/icons/spinner'

import useLoader from 'utils/use-loader'
import { API_URL } from 'utils/config'

const schema = z.object({
	assistant: z.string().min(1, 'Обязательное поле'),
	password: z.string().min(1, 'Обязательное поле')
})

export default function LoginForm({ className = '' }) {
	const {
		register,
		handleSubmit,
		setError,
		setValue,
		formState: { errors }
	} = useForm({
		resolver: zodResolver(schema)
	})

	const { btnRef, toggleLoader } = useLoader()
	const signIn = useSignIn()
	const navigate = useNavigate()

	const onSubmit = useCallback(
		async (data: { assistant: string; password: string }) => {
			toggleLoader(true)

			try {
				const req = await fetch(`${API_URL}/signIn`, {
					method: 'POST',
					headers: {
						'Content-type': 'application/json'
					},
					body: JSON.stringify(data)
				})

				if (req.status === 401) throw new Error('credentials')

				const { token } = await req.json()

				if (
					signIn({
						auth: {
							token,
							type: 'Bearer'
						},
						userState: {
							name: data.assistant
						}
					})
				)
					navigate('/')
				else throw new Error('signIn')
			} catch (error) {
				setError('assistant', {
					type: 'manual',
					message:
						error.message === 'credentials'
							? 'Неверный логин или пароль'
							: 'Ошибка авторизации'
				})
				setValue('password', '')
			}

			toggleLoader(false)
		},
		[toggleLoader, setError, setValue, navigate, signIn]
	)

	return (
		<form
			className={className}
			onSubmit={handleSubmit(onSubmit)}>
			<TextInput
				name='assistant'
				label='Ассистент'
				placeholder='Наименование'
				type='text'
				error={(errors.assistant?.message as string) || ''}
				inputProps={register('assistant')}
			/>
			<TextInput
				name='password'
				label='Пароль'
				placeholder='******'
				type='password'
				error={(errors.password?.message as string) || ''}
				inputProps={register('password')}
			/>
			<button
				type='submit'
				ref={btnRef}
				className='base-text btn-loader base-padding relative w-full bg-indigo-500
					text-white'>
				<span className='pointer-events-none text-inherit transition-opacity'>
					Создать или редактировать
				</span>
				<Spinner
					className='absolute left-[calc(50%-0.75rem)] top-[calc(50%-0.75rem)] size-6
						fill-black text-white'
				/>
			</button>
		</form>
	)
}
