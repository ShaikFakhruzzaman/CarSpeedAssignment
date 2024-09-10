package com.example.carspeedassignment.di

import android.app.Application
import android.content.Context
import com.example.carspeedassignment.MyApp
import com.example.carspeedassignment.data.notification.FirebaseNotificationService
import com.example.carspeedassignment.data.notification.NotificationService
import com.example.carspeedassignment.data.provider.GpsSpeedProvider
import com.example.carspeedassignment.data.repositories.SpeedRepositoryImpl
import com.example.carspeedassignment.domain.repositories.SpeedLimitRepository
import com.example.carspeedassignment.domain.usecases.NotificationUseCase
import com.example.carspeedassignment.domain.usecases.SpeedLimitUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.messaging.FirebaseMessaging
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
    fun provideContext(application: Application): Context = application.applicationContext


    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    fun provideFirebaseMessaging(): FirebaseMessaging {
        return FirebaseMessaging.getInstance()
    }

    @Provides
    fun provideGpsSpeedProvider(
        fusedLocationProviderClient: FusedLocationProviderClient
    ): GpsSpeedProvider {
        return GpsSpeedProvider(fusedLocationProviderClient)
    }

    @Provides
    @Singleton
    fun provideSpeedRepository(
        gpsSpeedProvider: GpsSpeedProvider
    ): SpeedLimitRepository {
        return SpeedRepositoryImpl(gpsSpeedProvider)
    }

    @Provides
    fun provideNotificationService(firebaseMessaging: FirebaseMessaging): NotificationService {
        return FirebaseNotificationService(firebaseMessaging)
    }

    @Provides
    fun provideSpeedLimitUseCase(
        repository: SpeedLimitRepository
    ): SpeedLimitUseCase {
        return SpeedLimitUseCase(repository)
    }

    @Provides
    fun provideNotificationUseCase(
        repository: SpeedLimitRepository,
        notificationService: NotificationService
    ): NotificationUseCase {
        return NotificationUseCase(repository, notificationService)
    }

}