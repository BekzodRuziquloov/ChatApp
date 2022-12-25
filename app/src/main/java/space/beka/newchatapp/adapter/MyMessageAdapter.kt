package space.beka.newchatapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import space.beka.newchatapp.databinding.ItemFromBinding
import space.beka.newchatapp.databinding.ItemToBinding
import space.beka.newchatapp.models.MyMessage


class MyMessageAdapter(val uid: String, val list: List<MyMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class FromVh(val itemRv: ItemFromBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(myMessage: MyMessage) {
            itemRv.tvDate.text = myMessage.date
            itemRv.tvText.text = myMessage.text
        }
    }

    inner class ToVh(val itemRv: ItemToBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(myMessage: MyMessage) {
            itemRv.tvDate.text = myMessage.date
            itemRv.tvText.text = myMessage.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            return FromVh(
                ItemFromBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return ToVh(ItemToBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 0) {
            val mh = holder as FromVh
            mh.onBind(list[position])
        } else {
            val mh = holder as ToVh
            mh.onBind(list[position])

        }
    }

    override fun getItemCount(): Int = list.size
    override fun getItemViewType(position: Int): Int {
        return if (list[position].fromUid == uid)
            0
        else 1

    }
}

