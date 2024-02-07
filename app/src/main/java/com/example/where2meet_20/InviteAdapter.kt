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

class InviteAdapter(private val inviteList: ArrayList<Invite>, private val context: Context) : RecyclerView.Adapter<InviteAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InviteAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val inflate = InvitationItemBinding.inflate(inflater, parent, false)
        return ViewHolder(inflate);
    }

    override fun onBindViewHolder(holder: InviteAdapter.ViewHolder, position: Int) {
        val invite = inviteList.get(position)
        holder.bind(invite)
    }

    override fun getItemCount(): Int {
        return inviteList.size
    }

    inner class ViewHolder(invitationItemBinding: InvitationItemBinding):RecyclerView.ViewHolder(invitationItemBinding.root){

        private val invitationItemBinding: InvitationItemBinding

        init {
            this.invitationItemBinding = invitationItemBinding
//            itemView.setOnClickListener(this)
        }

        fun bind(invite: Invite) {
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



}