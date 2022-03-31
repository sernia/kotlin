import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
 
class MainActivity : AppCompatActivity() {
    private lateinit var adapter : infoListAdapter
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
 
        initView()
    }
 
    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
 
        resultRecyclerView.layoutManager = linearLayoutManager
        adapter = infoListAdapter(this)
        resultRecyclerView.adapter = adapter
 
        apiGetButton.setOnClickListener {
            // ボタンが押されたときにeditTextから番号を取得する。
            if (infoIdEditText.text.isNotEmpty()) {
                // infoの検索
                getinfo(infoIdEditText.text.toString().toInt())
            }
        }
    }
 
    @SuppressLint("CheckResult")
    private fun getinfo(id: Int) {
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create() //
 
        val retrofit = Retrofit.Builder()
            .baseUrl("https://infoapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
 
 
        val client = retrofit.create(infoInfo::class.java)
        client.infoInfo(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter.setinfoData(it)
                adapter.notifyDataSetChanged()
            }, {
                it.printStackTrace()
            })
    }
}