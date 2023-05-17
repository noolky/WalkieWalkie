package com.example.walkiewalkie.CommunicationArea

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.walkiewalkie.DataBase.Helper
import com.example.walkiewalkie.R

class MyAdapterPost(var postList: ArrayList<DatalistPost>,val currentUserUsername: String):RecyclerView.Adapter<MyAdapterPost.MyViewHolder>() {


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tUserName: TextView = itemView.findViewById(R.id.userNameOnPost)
        val tPostData: TextView = itemView.findViewById(R.id.PostOnScreen)
        val EditButton: AppCompatImageButton = itemView.findViewById(R.id.EditButtonPost)
        val DeleteButton: AppCompatImageButton = itemView.findViewById(R.id.DeleteButtonPost)
        val dbh: Helper = Helper(itemView.context)
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
        holder.DeleteButton.setOnClickListener {
            val dialog = Dialog(holder.itemView.context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.layout_delete_dialog)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val btnYes : Button = dialog.findViewById(R.id.DeleteComfirmButton)
            val btnNo : Button = dialog.findViewById(R.id.DeleteNotComfirm)

            btnYes.setOnClickListener {
                val postID = currentItem.postID
                val deleted = holder.dbh.deletePostData(postID)
                if (deleted) {
                    postList.removeAt(holder.adapterPosition)
                    notifyDataSetChanged() // Notify RecyclerView of data change
                }
                dialog.dismiss()
            }
            btnNo.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()

        }
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

        val drawableDisable = ContextCompat.getDrawable(holder.itemView.context, R.drawable.disable_button)
        val drawableEnableGreen = ContextCompat.getDrawable(holder.itemView.context, R.drawable.custom_button_post_green)
        val drawableEnableRed = ContextCompat.getDrawable(holder.itemView.context, R.drawable.custom_button_post_red)
        if (currentItem.userName == currentUserUsername) {
            /*holder.EditButton.visibility = View.VISIBLE
            holder.DeleteButton.visibility = View.VISIBLE*/

            holder.EditButton.background = drawableEnableGreen
            holder.DeleteButton.background = drawableEnableRed
            holder.EditButton.isEnabled = true
            holder.DeleteButton.isEnabled = true

        } else {
            /*holder.EditButton.visibility = View.GONE
            holder.DeleteButton.visibility = View.GONE*/

            holder.EditButton.background = drawableDisable
            holder.DeleteButton.background = drawableDisable
            holder.EditButton.isEnabled = false
            holder.DeleteButton.isEnabled = false


        }
    }


}