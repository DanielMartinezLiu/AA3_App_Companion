package models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.enti.app_companion.R
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class CharacterAdapter(private val _characters: List<LolChampion>) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterAdapter.CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout_api, parent, false);

        return CharacterViewHolder(view);
    }

    override fun onBindViewHolder(holder: CharacterAdapter.CharacterViewHolder, position: Int) {
        val marvel = _characters[position]
        holder.bind(marvel)
    }

    override fun getItemCount(): Int {
        return _characters.size;
    }

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val characterName : TextView = itemView.findViewById(R.id.api_character_name)
        //private val characterImage : ImageView = itemView.findViewById(R.id.api_character_image)

        fun bind(lolChampion: LolChampion)
        {
            characterName.text = lolChampion.name

            val urlSum = "https://ddragon.leagueoflegends.com/cdn/15.1.1/img/champion/" + lolChampion.image.full
            Log.e("DEBUG", urlSum)
            //loadImage(urlSum, characterImage)
        }

        private fun loadImage(url: String, imageView: ImageView){
            Thread {
                try {
                    val imageUrl = URL(url)
                    val connection = imageUrl.openConnection() as HttpURLConnection
                    connection.connect()

                    val inputStream: InputStream = connection.inputStream
                    val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)

                    Handler(Looper.getMainLooper()).post {
                        imageView.setImageBitmap(bitmap)
                    }

                    connection.disconnect()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        }
    }
}