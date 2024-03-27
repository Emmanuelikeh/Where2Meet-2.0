package com.example.where2meet_20

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.where2meet_20.databinding.IncomingMessageBinding
import com.example.where2meet_20.databinding.OutgoingMessageBinding
import com.parse.ParseUser
import java.util.Objects


class MessageAdapter(private val messagesList: ArrayList<Messages>, private val context: Context) : RecyclerView.Adapter<MessageAdapter.ViewHolder>(){
    private final val MESSAGE_OUTGOING = 0
    private final val MESSAGE_INCOMING = 1


    override fun getItemViewType(position: Int): Int {
        return if(isCurrentUser(position)) MESSAGE_OUTGOING else MESSAGE_INCOMING
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if(viewType == MESSAGE_OUTGOING){
            val inflate = OutgoingMessageBinding.inflate(inflater, parent, false)
            return MessageOutgoingHolder(inflate)
        }
        else{
            val inflate = IncomingMessageBinding.inflate(inflater, parent, false)
            return MessageIncomingHolder(inflate)
        }
    }

    override fun onBindViewHolder(holder: MessageAdapter.ViewHolder, position: Int) {
        val message = messagesList.get(position)
        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    private fun isCurrentUser(position: Int): Boolean {
        val message: Messages = messagesList.get(position)
        val currentUser = ParseUser.getCurrentUser()
        return Objects.equals(message.messageSender!!.objectId, currentUser.objectId)
    }

    abstract inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        abstract fun bind(message: Messages)
    }

    inner class MessageIncomingHolder(incomingMessageBinding: IncomingMessageBinding ): ViewHolder(incomingMessageBinding.root){

        private val incomingMessageBinding: IncomingMessageBinding

        init {
            this.incomingMessageBinding = incomingMessageBinding
        }

        override fun bind(message: Messages) {
            incomingMessageBinding.messageText.text = message.messageBody
        }
    }

    inner class MessageOutgoingHolder(outgoingMessageBinding: OutgoingMessageBinding): ViewHolder(outgoingMessageBinding.root){

        private val outgoingMessageBinding: OutgoingMessageBinding

        init {
            this.outgoingMessageBinding = outgoingMessageBinding
        }

        override fun bind(message: Messages) {
            outgoingMessageBinding.messageText.text = message.messageBody
        }
    }
}