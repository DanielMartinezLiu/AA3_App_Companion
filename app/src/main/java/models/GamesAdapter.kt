package models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
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

class GamesAdapter(private val _games: List<DarkSoulsGame>) : RecyclerView.Adapter<GamesAdapter.GameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesAdapter.GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout_api, parent, false);

        return GameViewHolder(view);
    }

    override fun onBindViewHolder(holder: GamesAdapter.GameViewHolder, position: Int) {
        val games = _games[position]
        holder.bind(games)
    }

    override fun getItemCount(): Int {
        return _games.size;
    }

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val characterName : TextView = itemView.findViewById(R.id.api_character_name)
        private val characterImage : ImageView = itemView.findViewById(R.id.api_character_image)

        fun bind(darkSoulsGame: DarkSoulsGame)
        {
            characterName.text = darkSoulsGame.name
            darkSoulsGame.background_image?.let { loadImage(it, characterImage) }
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