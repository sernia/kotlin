import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.eclipsesource.json.Json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val URL = "https://www.hoge.com/test.php"
        val sendDataJson = "{\"id\":\"foo@hoge.com\",\"pw\":\"pass\"}"
        val getButton = findViewById(R.id.buttonGet) as Button
        getButton.setOnClickListener {
            getButton.setEnabled(false)
            onParallelGetButtonClick(URL,sendDataJson)
        }
    }

    //非同期処理でHTTP GET or POSTを実行します。
    fun onParallelGetButtonClick(URL: String, sendDataJson: String) = GlobalScope.launch(Dispatchers.Main) {
        val getButton = findViewById(R.id.buttonGet) as Button
//        val textView = findViewById(R.id.text) as TextView
//        ここのコメントを外すと落ちる。

        val http = HttpUtil()
        //Mainスレッドでネットワーク関連処理を実行するとエラーになるためBackgroundで実行
        val deferred = GlobalScope.async(Dispatchers.Default) {
            http.httpPost(URL,sendDataJson)
        }
        deferred.await().let {
            //minimal-jsonを使って　jsonをパース
            val result = Json.parse(it).asObject()
            val ret1 = result.getString("id", "no id data")
            Log.d("TAG",ret1)

//            textView.setText(ret1)
//            ここでテキストビューに表示したい。

            getButton.setEnabled(true)
        }
    }
}