ackage com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    val URL = "http://zipcloud.ibsnet.co.jp/api/search?zipcode=1350091"

    data class ZipResponse(
        var message : String ?= null,
        var status : String ?= null,
        var results : ArrayList = ArrayList()
    )

    data class Address(
        var address1 : String ?= null,
        var address2 : String ?= null,
        var address3 : String ?= null,
        var kana1 : String ?= null,
        var kana2 : String ?= null,
        var kana3 : String ?= null,
        var prefcode : String ?= null,
        var zipcode : String ?= null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        callApi()
    }

    private fun callApi() = GlobalScope.launch(Dispatchers.Main) {
        async(Dispatchers.Default) {
            getResponse(URL)
        }.await().let {
            var result = Gson().fromJson(it, ZipResponse::class.java)
            text_view.text =
                result.results[0].address1 +
                        " " + result.results[0].address2 +
                        " " + result.results[0].address3
        }
    }

    private fun getResponse(url: String): String? {
        return OkHttpClient()
            .newCall(Request.Builder().url(url).build())
            .execute()
            .body?.string()
    }
}