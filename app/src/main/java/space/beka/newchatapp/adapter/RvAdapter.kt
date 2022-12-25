package space.beka.newchatapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import space.beka.newchatapp.databinding.ItemRvBinding
import space.beka.newchatapp.models.User

class RvAdapter(var list: ArrayList<User> = ArrayList(), val rvClick: RvClick) :
    RecyclerView.Adapter<RvAdapter.Vh>() {
    inner class Vh(var itemRvBinding: ItemRvBinding) : RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(user: User, position: Int) {
            itemRvBinding.tvName.text = user.displayName
            Picasso.get().load(user.imageLink).into(itemRvBinding.imageItem)
            itemRvBinding.root.setOnClickListener {
                rvClick.itemClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size


}
interface RvClick{
    fun itemClick(user: User)

}