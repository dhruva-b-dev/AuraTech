package com.dhruva.auratech.data.stripe

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object StripeApiHelper {
    private const val SECRET_KEY = "sk_test_51UPjszEKg1PiPhcMymSpilhFBLugiOvqCezqlhjRoNQ6cmOZNnAH1yTVYUIczf4PxnPOtWxYiNyf0Tu6fjCfle4H00gZKfG37f"

    suspend fun createPaymentIntent(amountCents: Long, currency: String = "usd"): String? = withContext(Dispatchers.IO) {
        try {
            val url = URL("https://api.stripe.com/v1/payment_intents")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Authorization", "Bearer $SECRET_KEY")
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            conn.doOutput = true

            val postData = "amount=$amountCents&currency=$currency&automatic_payment_methods[enabled]=true"
            OutputStreamWriter(conn.outputStream).use { writer ->
                writer.write(postData)
                writer.flush()
            }

            val responseCode = conn.responseCode
            val stream = if (responseCode in 200..299) conn.inputStream else conn.errorStream
            val responseStr = stream?.bufferedReader()?.use { it.readText() } ?: ""

            if (responseCode in 200..299) {
                val json = JSONObject(responseStr)
                return@withContext json.optString("client_secret")
            } else {
                Log.e("StripeApiHelper", "Failed to create PaymentIntent: $responseStr")
                null
            }
        } catch (e: Exception) {
            Log.e("StripeApiHelper", "Exception creating PaymentIntent", e)
            null
        }
    }
}
