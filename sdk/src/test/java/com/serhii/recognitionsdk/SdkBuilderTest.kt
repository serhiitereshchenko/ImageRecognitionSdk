package com.serhii.recognitionsdk

import android.content.Context
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SdkBuilderTest {

    @Mock
    private lateinit var context: Context

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `throw exception when credentials null`() {
        SdkBuilder().context(context).build()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `throw exception when secret key empty`() {
        SdkBuilder()
            .context(context)
            .awsCredentials("apiKey", "").build()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `throw exception when api key empty`() {
        SdkBuilder()
            .context(context)
            .awsCredentials("", "secretKey").build()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `throw exception when context is null`() {
        SdkBuilder()
            .awsCredentials("apiKey", "secretKey").build()
    }
}
