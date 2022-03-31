import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
 
internal class infoListAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var infoData: infoData =
        infoData(0, "",
            infoData.info("", ""),
            infoData.Sprites("","","",""),
            infoData.Version("","")
        )
 
    fun setinfoData(data: infoData) {
        infoData = data
    }
 
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_info,
                parent,
                false)
        return infoInfoHolder(view)
    }
 
    override fun getItemCount(): Int {
        return 5
    }
 
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as infoInfoHolder).bind(position)
    }
 
    private inner class infoInfoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameArea: LinearLayout
        private val formArea: LinearLayout
        private val nameTextView: TextView
        private val formNameView: TextView
        private val imageView: ImageView
 
        init {
            nameArea = itemView.findViewById(R.id.infoNameListItemView)
            formArea = itemView.findViewById(R.id.formListItemView)
            nameTextView = itemView.findViewById(R.id.infoNameTextView)
            formNameView = itemView.findViewById(R.id.infoFormNameTextView)
            imageView = itemView.findViewById(R.id.infoImageView)
        }
 
        internal fun bind(position: Int) {
            nameArea.isVisible = position == 0
            formArea.isVisible = position != 0
            var spriteUrl = ""
            when (position) {
                0 -> {
                    nameTextView.text = infoData.info.name
                }
                1 -> {
                    formNameView.text = "test1"
                    spriteUrl = infoData.sprites.front_default
                }
                2 -> {
                    formNameView.text = "test2"
                    spriteUrl = infoData.sprites.front_shiny
                }
                3 -> {
                    formNameView.text = "test3"
                    spriteUrl = infoData.sprites.back_default
                }
                4 -> {
                    formNameView.text = "test4"
                    spriteUrl = infoData.sprites.back_shiny
                }
            }
 
            Glide.with(context)
                .asBitmap()
                .load(spriteUrl)
                .into(imageView)
        }
    }
 
}