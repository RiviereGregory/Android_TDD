package gri.riverjach.codingcompanionfinder

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object CommonTestDataUtil {
    fun dispatch(request: RecordedRequest): MockResponse? {
        val headers = request.headers
        if (request.method.equals("POST")) {
            if (request.path.equals("/oauth2/token")) {
                return MockResponse().setResponseCode(200).setBody(
                    "{\"access_token\":\"valid_token\"}"
                )
            }
        }
        val authorization = headers.values("Authorization")
        if (!authorization.isEmpty() &&
            authorization.get(0).equals("Bearer valid_token")
        ) {
            return when (request.path) {
                "/animals?limit=20&location=30318" -> {
                    MockResponse()
                        .setResponseCode(200)
                        .setBody(
                            readFile("search_30318.json")
                        )
                }
                "/animals?limit=20&location=90210" -> {
                    MockResponse()
                        .setResponseCode(200)
                        .setBody("{\"animals\": []}")
                }
                else -> {
                    MockResponse().setResponseCode(404).setBody("{}")
                }
            }
        } else {
            return MockResponse().setResponseCode(401).setBody("{}")
        }
    }

    fun nonInterceptedDispatch(
        request: RecordedRequest
    ): MockResponse? {
        val headers = request.headers
        return when (request.path) {
            "/animals?limit=20&location=30318" -> {
                MockResponse().setResponseCode(200).setBody(
                    readFile("search_30318.json")
                )
            }
            "/animals?limit=20&location=90210" -> {
                MockResponse().setResponseCode(200)
                    .setBody("{\"animals\": []}")
            }
            else -> {
                MockResponse().setResponseCode(404).setBody("{}")
            }
        }
    }


    @Throws(IOException::class)
    private fun readFile(jsonFileName: String): String {
        val inputStream = this::class.java
            .getResourceAsStream("/assets/$jsonFileName") ?: this::class.java
            .getResourceAsStream("/$jsonFileName")
        ?: throw NullPointerException(
            "Have you added the local resource correctly?, "
                    + "Hint: name it as: " + jsonFileName
        )

        val stringBuilder = StringBuilder()
        var inputStreamReader: InputStreamReader? = null
        try {
            inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var character: Int = bufferedReader.read()
            while (character != -1) {
                stringBuilder.append(character.toChar())
                character = bufferedReader.read()
            }
        } catch (exception: IOException) {
            exception.printStackTrace()
        } finally {
            inputStream.close()
            inputStreamReader?.close()
        }
        return stringBuilder.toString()
    }

}