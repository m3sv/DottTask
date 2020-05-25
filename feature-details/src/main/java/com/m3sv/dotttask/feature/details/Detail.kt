package com.m3sv.dotttask.feature.details

import androidx.recyclerview.widget.DiffUtil

data class Detail(
    val text: String
)

class DetailDiffCallback : DiffUtil.ItemCallback<Detail>() {
    override fun areItemsTheSame(oldItem: Detail, newItem: Detail): Boolean = oldItem === newItem

    override fun areContentsTheSame(oldItem: Detail, newItem: Detail): Boolean {
        return oldItem.text == newItem.text
    }
}
