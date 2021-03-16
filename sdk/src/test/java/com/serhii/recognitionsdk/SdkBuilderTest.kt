package com.serhii.recognitionsdk

import android.content.Context
import org.junit.Test
import org.mockito.Mockito

class SdkBuilderTest {

    @Test(expected = IllegalArgumentException::class)
    fun `throw exception when credentials null`() {
        val context = Mockito.mock(Context::class.java)
        SdkBuilder().context(context).build()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `throw exception when aws credentials empty`() {
        val context = Mockito.mock(Context::class.java)
        SdkBuilder()
            .context(context)
            .awsCredentials("", "").build()
    }
}
