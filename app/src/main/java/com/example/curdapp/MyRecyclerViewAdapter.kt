package com.example.curdapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.curdapp.databinding.ListItemBinding
import com.example.curdapp.db.Subscriber
import com.example.curdapp.generated.callback.OnClickListener

class MyRecyclerViewAdapter(
    private val subscribersList: List<Subscriber>,
    private val clickListener: (Subscriber) -> Unit
) :
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribersList[position],clickListener)
    }

    override fun getItemCount(): Int {
        return subscribersList.size
    }
}

class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit) {
        binding.textViewName.text = subscriber.name
        binding.textViewEmail.text = subscriber.email
        binding.listItemLayout.setOnClickListener {
            clickListener(subscriber)
        }
    }
}