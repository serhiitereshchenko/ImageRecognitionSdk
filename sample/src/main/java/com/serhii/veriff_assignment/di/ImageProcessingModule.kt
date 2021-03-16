package com.serhii.veriff_assignment.di

import android.content.Context
import com.serhii.recognitionsdk.SdkBuilder
import com.serhii.recognitionsdk.VeriffSdk
import com.serhii.veriff_assignment.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@InstallIn(SingletonComponent::class)
@Module
class ImageProcessingModule {

    @Provides
    @AWSApiKey
    fun provideAwsApiKey(): String {
        return BuildConfig.AWS_API_KEY
    }

    @Provides
    @AWSSecretKey
    fun provideAwsSecretKey(): String {
        return BuildConfig.AWS_SECRET_KEY
    }

    @Provides
    fun provideVeriffSdk(
        @ApplicationContext context: Context,
        @AWSApiKey awsApiKey: String,
        @AWSSecretKey awsSecretKey: String
    ): VeriffSdk =
        SdkBuilder()
            .context(context)
            .awsCredentials(awsApiKey, awsSecretKey)
            .build()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AWSApiKey

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AWSSecretKey
