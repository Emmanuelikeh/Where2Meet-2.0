package com.example.where2meet_20

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.where2meet_20.databinding.ActivityInvitationsBinding
import com.example.where2meet_20.databinding.InvitationItemBinding
import com.example.where2meet_20.databinding.ItemSentInvitationsBinding

class InviteAdapter(private val inviteList: ArrayList<Invite>, private val context: Context, private val screen: ScreenTypes) : RecyclerView.Adapter<InviteAdapter.ViewHolder>(){
    enum class ScreenTypes{
        PENDING,SENT
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InviteAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (screen == ScreenTypes.PENDING) {
            val inflate = InvitationItemBinding.inflate(inflater, parent, false)
            PendingInviteViewHolder(inflate)
        } else{
            val inflate = ItemSentInvitationsBinding.inflate(inflater, parent, false)
            SentInviteViewHolder(inflate)
        }

    }

    override fun onBindViewHolder(holder: InviteAdapter.ViewHolder, position: Int) {
        val invite = inviteList.get(position)
        holder.bind(invite)
    }

    override fun getItemCount(): Int {
        return inviteList.size
    }

    abstract inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        abstract fun bind(invite: Invite)
    }

    inner class PendingInviteViewHolder(invitationItemBinding: InvitationItemBinding): ViewHolder(invitationItemBinding.root){

        private val invitationItemBinding: InvitationItemBinding

        init {
            this.invitationItemBinding = invitationItemBinding
//            itemView.setOnClickListener(this)
        }

        override fun bind(invite: Invite) {
            invitationItemBinding.tvPendingInviteTitle.text = invite.title
            invitationItemBinding.tvPendingInviteSendersName.text = invite.sender?.fetchIfNeeded()?.username.toString()
            invitationItemBinding.tvPendingUsersAddress.text = invite.address
            DateUtil().setDate("MM/dd/yyyy",invitationItemBinding.tvPendingInviteInvitationDate,"Date: ",invite.invitationDate)
            invitationItemBinding.ivPendingInviteSendersProfileImage.load(invite.sender?.getParseFile("profileImage")?.url){
                placeholder(R.drawable.image_app)
            }
            invitationItemBinding.btnInviteAccept.setOnClickListener{
                accpetInvite(invite)
            }
            
            invitationItemBinding.btnInviteReject.setOnClickListener{
                regectInvite(invite)
            }
        }

        private fun regectInvite(invite: Invite) {
            invite.flag = 1
            invite.saveInBackground {
                if (it == null) {
                    Toast.makeText(context, "Invite Rejected", Toast.LENGTH_SHORT).show()
                    val position = adapterPosition
                    inviteList.removeAt(position)
                    notifyItemRemoved(position)
                } else {
                    Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }

        }

        private fun accpetInvite(invite: Invite) {
            invite.flag = 2
            invite.saveInBackground {
                if (it == null) {
                    Toast.makeText(context, "Invite Accepted", Toast.LENGTH_SHORT).show()
                    val position = adapterPosition
                    inviteList.removeAt(position)
                    notifyItemRemoved(position)
                } else {
                    Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    inner class SentInviteViewHolder(itemSentInvitationsBinding: ItemSentInvitationsBinding): ViewHolder(itemSentInvitationsBinding.root){
        private val itemSentInvitationsBinding: ItemSentInvitationsBinding

        init {
            this.itemSentInvitationsBinding = itemSentInvitationsBinding
        }

        override fun bind(invite: Invite) {
            itemSentInvitationsBinding.tvRequestedInviteTitle.text = invite.title
            DateUtil().setDate("MM/dd/yyyy",itemSentInvitationsBinding.tvRequestedInviteDate,"Date: ",invite.invitationDate)
            DateUtil().setDate("EEE, MMM d, yyyy 'at' hh:mm a",itemSentInvitationsBinding.tvInviteRequestComposeDate,"Sent at: ",invite.createdAt)
            itemSentInvitationsBinding.tvRequestedInviteeName.text = invite.receiver?.fetchIfNeeded()?.username.toString()
            itemSentInvitationsBinding.ivRequestsentUserImage.load(R.drawable.image_app){
                placeholder(R.drawable.image_app)
            }
            getStatus(invite)
            
//            itemSentInvitationsBinding.tvSentInviteReceiversName.text = invite.receiver?.fetchIfNeeded()?.username.toString()
//            itemSentInvitationsBinding.tvSentInviteReceiversAddress.text = invite.address
//            DateUtil().setDate("MM/dd/yyyy",itemSentInvitationsBinding.tvSentInviteInvitationDate,"Date: ",invite.invitationDate)
//            itemSentInvitationsBinding.ivSentInviteReceiversProfileImage.load(invite.receiver?.getParseFile("profileImage")?.url){
//                placeholder(R.drawable.image_app)
//            }
        }

        private fun getStatus(invite: Invite) {
            when(invite.flag){
                0 -> itemSentInvitationsBinding.tvInviteStatus.text = "Pending"
                1 -> itemSentInvitationsBinding.tvInviteStatus.text = "Rejected"
                2 -> itemSentInvitationsBinding.tvInviteStatus.text = "Accepted"
            }

        }
    }



}