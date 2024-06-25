package com.bouchara.projet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyMessageAdapter(var userList: ArrayList<DataMessageList>) :
    RecyclerView.Adapter<MyMessageAdapter.MyViewHolder>() {

    private lateinit var mListener: MyMessageAdapter.onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun onItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        //val tname: TextView = itemView.findViewById(R.id.tv_message_phone)
        val tmessage: TextView = itemView.findViewById(R.id.tv_message_text)
        val tdate: TextView = itemView.findViewById(R.id.tv_message_date_time)
        val tnamephone: TextView = itemView.findViewById(R.id.tv_message_name_phone)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMessageAdapter.MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_message_item, parent, false)
        return MyMessageAdapter.MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyMessageAdapter.MyViewHolder, position: Int) {
        val currentItem = userList[position]
        //holder.tname.text = currentItem.messageNumber
        holder.tmessage.text = currentItem.message
        holder.tdate.text = currentItem.dateMessage.toString()
        holder.tnamephone.text = currentItem.nomphone
    }

    override fun getItemCount(): Int {
        return userList.size
    }


}