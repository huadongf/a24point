package com.example.a24point

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Equationadapter(private val context: Context, private val kapianList: List<String>) : RecyclerView.Adapter<Equationadapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val equationName: TextView = view.findViewById(R.id.equaa)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.equation_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.equationName.text = kapianList[position]
    }
    override fun getItemCount() = kapianList.size

}