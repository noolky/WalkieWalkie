package com.example.walkiewalkie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapterPost(var postList: ArrayList<DatalistPost>):RecyclerView.Adapter<MyAdapterPost.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tUserName: TextView = itemView.findViewById(R.id.userNameOnPost)
        val tPostData: TextView = itemView.findViewById(R.id.PostOnScreen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_communication_area,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = postList[position]
        holder.tUserName.text = currentItem.userName
        holder.tPostData.text = currentItem.postData
    }

}