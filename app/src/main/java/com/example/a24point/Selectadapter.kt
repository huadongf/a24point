package com.example.a24point

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Selectadapter(private val context: Context, private val kapianList: List<Kapian>) : RecyclerView.Adapter<Selectadapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val kapianImage: ImageView = view.findViewById(R.id.kapianImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.kapian_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val my= ViewModelProvider(context as MainActivity).get(Myvi::class.java)
            val kapian = kapianList[position]
                my.xuanzeList.remove(kapian)
                my.ad.notifyDataSetChanged()
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kapian = kapianList[position]
        Glide.with(context).load(kapian.imageId).into(holder.kapianImage)
    }
    override fun getItemCount() = kapianList.size

}