import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ownapi.ItemsViewModel
import com.example.ownapi.R
import com.squareup.picasso.Picasso

class Adapter(private val dataList: List<ItemsViewModel>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        val imageView = holder.pokemonImage
        // Set the Pokémon image using Picasso (or any other image loading library you prefer)
        Picasso.get()
            .load(item.image)
            .fit()
            .into(imageView as ImageView)

        // Set Pokémon name and weight
        holder.pokemonName.text = item.name
        holder.pokemonWeight.text = "Weight: ${item.weight} kg"
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokemonImage = itemView.findViewById<ImageView>(R.id.pokemonImage)
        val pokemonName = itemView.findViewById<TextView>(R.id.pokemonName)
        val pokemonWeight = itemView.findViewById<TextView>(R.id.pokemonWeight)
    }
}
