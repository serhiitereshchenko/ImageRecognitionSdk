package com.serhii.veriff_assignment.di

import android.content.Context
import android.text.TextUtils
import com.serhii.recognitionsdk.SdkBuilder
import com.serhii.recognitionsdk.VeriffSdk
import com.serhii.veriff_assignment.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

private const val SAFE_KEY = "safeKey"

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
    ): VeriffSdk {
        val safeApiKey = if (TextUtils.isEmpty(awsApiKey)) SAFE_KEY else awsApiKey
        val safeSecretKey = if (TextUtils.isEmpty(awsSecretKey)) SAFE_KEY else awsSecretKey
        return SdkBuilder()
            .context(context)
            .awsCredentials(safeApiKey, safeSecretKey)
            .build()
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AWSApiKey

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AWSSecretKey
