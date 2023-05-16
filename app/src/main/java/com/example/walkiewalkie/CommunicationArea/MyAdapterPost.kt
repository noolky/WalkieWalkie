package com.example.walkiewalkie.CommunicationArea

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.walkiewalkie.DataBase.Helper
import com.example.walkiewalkie.R

class MyAdapterPost(var postList: ArrayList<DatalistPost>,val currentUserUsername: String):RecyclerView.Adapter<MyAdapterPost.MyViewHolder>() {

    private lateinit var db : Helper
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tUserName: TextView = itemView.findViewById(R.id.userNameOnPost)
        val tPostData: TextView = itemView.findViewById(R.id.PostOnScreen)
        val EditButton: AppCompatImageButton = itemView.findViewById(R.id.EditButtonPost)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_communication_area,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        /*db = Helper(requireContext())*/
        val currentItem = postList[position]
        holder.tUserName.text = currentItem.userName
        holder.tPostData.text = currentItem.postData
        holder.EditButton.setOnClickListener { view ->
            val postID = currentItem.postID
            val postData = currentItem.postData

            val actionId = R.id.action_communicationAreaFragment_to_editFragment
            val args = Bundle().apply {
                putString("postID", postID)
                putString("postData", postData)
            }
            view.findNavController().navigate(actionId, args)
        }



        if (currentItem.userName == currentUserUsername.toString()) {
            holder.EditButton.visibility = View.VISIBLE


        } else {
            holder.EditButton.visibility = View.GONE

        }
    }

}