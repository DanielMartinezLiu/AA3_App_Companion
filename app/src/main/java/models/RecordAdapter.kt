package models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.enti.app_companion.R

class RecordAdapter(private val _records: List<RecordModel>) : RecyclerView.Adapter<RecordAdapter.RecordsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordAdapter.RecordsViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout_record_manager, parent, false);
        return RecordsViewHolder(view);
    }

    override fun onBindViewHolder(holder: RecordAdapter.RecordsViewHolder, position: Int)
    {
        val records = _records[position];
        holder.bind(records);
    }

    override fun getItemCount(): Int
    {
        return _records.size;
    }

    class RecordsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val recordImage : ImageView = itemView.findViewById(R.id.record_image);
        private val bestHand : TextView = itemView.findViewById(R.id.higher_hand_text);
        private val bestAnte : TextView = itemView.findViewById(R.id.best_ante_text);
        private val bestRound : TextView = itemView.findViewById(R.id.best_round_text);

        fun bind(recordModel: RecordModel)
        {
            recordImage.setImageResource(recordModel.imageResId);
            bestHand.text = recordModel.bestHand.toString();
            bestAnte.text = recordModel.bestAnte.toString();
            bestRound.text = recordModel.bestRound.toString();

        }
    }
}