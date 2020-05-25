package com.m3sv.dotttask.feature.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class DetailsAdapter : ListAdapter<Detail, DetailViewHolder>(DetailDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder =
        LayoutInflater
            .from(parent.context)
            .run { DetailViewHolder(inflate(R.layout.detail_item, parent, false)) }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bindView(getItem(position).text)
    }
}


class DetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val textView = view as TextView

    fun bindView(text: String) {
        textView.text = text
    }

}
