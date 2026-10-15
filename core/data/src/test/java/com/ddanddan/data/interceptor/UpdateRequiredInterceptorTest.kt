package com.ddanddan.data.interceptor

import com.ddanddan.data.update.UpdateRequiredNotifier
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UpdateRequiredInterceptorTest {

    private lateinit var notifier: UpdateRequiredNotifier

    @Before
    fun setUp() {
        notifier = UpdateRequiredNotifier()
    }

    private fun execute(statusCode: Int): Response {
        val fakeServer = Interceptor { chain ->
            Response.Builder()
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .code(statusCode)
                .message("fake")
                .body("".toResponseBody())
                .build()
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(UpdateRequiredInterceptor(notifier))
            .addInterceptor(fakeServer)
            .build()
        return client.newCall(Request.Builder().url("http://localhost/").build()).execute()
    }

    @Test
    fun `426 응답이면 업데이트 필요 상태가 켜진다`() {
        execute(426).close()

        assertTrue(notifier.updateRequired.value)
    }

    @Test
    fun `426 응답도 호출부에는 그대로 전달된다`() {
        val response = execute(426)

        assertEquals(426, response.code)
        response.close()
    }

    @Test
    fun `정상 응답이면 상태가 켜지지 않는다`() {
        execute(200).close()

        assertFalse(notifier.updateRequired.value)
    }

    @Test
    fun `426이 아닌 에러 응답도 상태를 켜지 않는다`() {
        listOf(400, 401, 403, 404, 500).forEach { execute(it).close() }

        assertFalse(notifier.updateRequired.value)
    }

    @Test
    fun `한 번 켜지면 이후 정상 응답이 와도 유지된다`() {
        execute(426).close()
        execute(200).close()

        assertTrue(notifier.updateRequired.value)
    }
}
