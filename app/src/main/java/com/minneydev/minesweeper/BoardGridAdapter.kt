package com.minneydev.minesweeper

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class BoardGridAdapter (private val context: Context, private val cells: List<Cell>) : BaseAdapter() {
    class ViewHolder {
        var imageView: ImageView? = null
    }
    //Based off prof code
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        var holder: ViewHolder
        if (view == null) {
            val inflater = (context as Activity).layoutInflater
            view = inflater.inflate(R.layout.grid_cell,parent,false)
            holder = ViewHolder()
            holder.imageView = view!!.findViewById(R.id.cell)
            view.tag = holder
        }else {
            holder = view.tag as ViewHolder
        }
        holder.imageView!!.setImageResource(cells[position].getCellType())
        return view
    }
    override fun getItem(position: Int): Any {
        return cells[position]
    }
    override fun getItemId(position: Int): Long {
        return 0
    }
    override fun getCount(): Int {
        return cells.size
    }
}