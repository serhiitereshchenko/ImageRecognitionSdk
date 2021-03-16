package com.serhii.veriff_assignment.di

import com.serhii.recognitionsdk.VeriffSdk
import com.serhii.veriff_assignment.data.repository.RecognitionRepository
import com.serhii.veriff_assignment.data.repository.RecognitionRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun provideRecognitionRepository(veriffSdk: VeriffSdk): RecognitionRepository {
        return RecognitionRepositoryImpl(veriffSdk)
    }
}
