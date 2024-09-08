package com.example.carspeedassignment.di

import com.example.carspeedassignment.data.datasource.remote.AWSDataSource
import com.example.carspeedassignment.data.datasource.remote.FirebaseDataSource
import com.example.carspeedassignment.data.repositories.SpeedRepositoryImpl
import com.example.carspeedassignment.domain.repositories.SpeedRepository
import com.example.carspeedassignment.domain.usecases.CheckSpeedUseCase
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CarModule {

    @Provides
    @Singleton
    fun provideCheckSpeedUseCase(
        speedRepository: SpeedRepository
    ):CheckSpeedUseCase{
        return CheckSpeedUseCase(speedRepository)
    }

    @Provides
    fun provideSpeedRepository(
        firebaseDataSource: FirebaseDataSource,
        awsDataSource: AWSDataSource
    ): SpeedRepository {
        return SpeedRepositoryImpl(firebaseDataSource, awsDataSource)
    }

    @Provides
    fun provideFirebaseDataSource(firebaseDatabase: FirebaseDatabase): FirebaseDataSource{
        return FirebaseDataSource(firebaseDatabase)
    }

    @Provides
    fun provideAWSDataSource(): AWSDataSource{
        return AWSDataSource()
    }

    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase{
        return FirebaseDatabase.getInstance()
    }
}