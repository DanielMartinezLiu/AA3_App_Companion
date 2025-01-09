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
import kotlin.concurrent.thread
import kotlin.math.log

class MarvelAdapter(private val _marvel: List<MarvelCharacter>) : RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelAdapter.MarvelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout_api, parent, false);

        return MarvelViewHolder(view);
    }

    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        val marvel = _marvel[position]
        holder.bind(marvel)
    }

    override fun getItemCount(): Int {
        return _marvel.size;
    }

    class MarvelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val characterName : TextView = itemView.findViewById(R.id.api_character_name)
        private val characterImage : ImageView = itemView.findViewById(R.id.api_character_image)

        fun bind(marvelCharacter: MarvelCharacter)
        {
            characterName.text = marvelCharacter.name

            val urlSum = marvelCharacter.thumbnail.path.replace("http://", "https://") + '.' + marvelCharacter.thumbnail.extension + "?4a47441d0ad2b1d769d917af032b6810"
            Log.e("CACA", urlSum)
            loadImage(urlSum, characterImage)
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