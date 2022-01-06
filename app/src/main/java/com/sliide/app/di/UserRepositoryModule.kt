package com.sliide.app.di

import com.sliide.app.data.repository.UsersRepository
import com.sliide.app.data.repository.UsersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserRepositoryModule {

    @Binds
    abstract fun bindsUsersRepository(userRepositoryImpl: UsersRepositoryImpl): UsersRepository

}