
package com.example.shikha.collegegossip.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shikha.collegegossip.MainActivity2
import com.example.shikha.collegegossip.MessageChatActivity
import com.example.shikha.collegegossip.ModelClasses.Chat
import com.example.shikha.collegegossip.ModelClasses.Users
import com.example.shikha.collegegossip.R
import com.example.shikha.collegegossip.VistUserProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.user_search_item_layout.view.*

class UserAdapter(
        mContext: Context,
        mUsers: List<Users>,
        isChatCheck: Boolean
        ) : RecyclerView.Adapter<UserAdapter.ViewHolder?>()

{
    private val mContext: Context
    private val mUsers: List<Users>
    private val isChatCheck: Boolean
    var lastmsg: String? = null

    init {
        this.mUsers = mUsers
        this.mContext = mContext
        this.isChatCheck = isChatCheck
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.user_search_item_layout, viewGroup,false)
        return UserAdapter.ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {

        val user: Users = mUsers[i]

        holder.userNameTxt.text = user!!.getUSERNAME()
        Picasso.get().load(user.getPROFILE()).into(holder.profileImageView)

        if (isChatCheck){
            retrivelastMessage(user.getUID(),holder.lastMessagetxt)
        }
        else{
            holder.lastMessagetxt.visibility = View.GONE
        }

        if (isChatCheck){
            if (user.getSTATUS() == "online"){
                holder.onlineImageView.visibility = View.VISIBLE
                holder.offlineImageView.visibility = View.GONE
            }else{
                holder.onlineImageView.visibility = View.GONE
                holder.offlineImageView.visibility = View.VISIBLE

            }
        }else{
            holder.onlineImageView.visibility = View.GONE
            holder.offlineImageView.visibility = View.GONE
        }

        holder.itemView.setOnClickListener{
            val options = arrayOf<CharSequence>(
                "Send Message",
                "View Profile"
            )
            val  builder: AlertDialog.Builder = AlertDialog.Builder(mContext)
            builder.setTitle("Choose")
            builder.setItems(options, DialogInterface.OnClickListener{ dialog, position ->
                if (position == 0){
                    val intent = Intent(mContext, MessageChatActivity::class.java)
                    intent.putExtra("visit_id", user.getUID())
                    mContext.startActivity(intent)
                }

                else if (position ==1){
                    val intent = Intent(mContext, VistUserProfileActivity::class.java)
                    intent.putExtra("visit_id", user.getUID())
                    mContext.startActivity(intent)

                }
            })
            builder.show()
        }

    }


    override fun getItemCount(): Int {
        return mUsers.size
    }


            class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
                var userNameTxt: TextView
                var profileImageView: CircleImageView

                var onlineImageView: CircleImageView

                var offlineImageView: CircleImageView

                var lastMessagetxt: TextView

                init {
                    userNameTxt = itemView.findViewById(R.id.search_username)
                    profileImageView = itemView.findViewById(R.id.search_profile_image)
                    onlineImageView = itemView.findViewById(R.id.image_online)
                    offlineImageView = itemView.findViewById(R.id.image_offline)
                    lastMessagetxt = itemView.findViewById(R.id.message_last)

                }

            }

    private fun retrivelastMessage(chatUserId: String?, lastMessagetxt: TextView) {
        lastmsg = "defaultMsg"

        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val reference = FirebaseDatabase.getInstance().reference.child("Chats")

        reference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                for (dataSnapshot in p0.children){
                    val chat: Chat? = dataSnapshot.getValue(Chat::class.java)

                    if (firebaseUser!=null && chat!=null){
                        if (chat.getReceiver() == firebaseUser!!.uid && chat.getSender() == chatUserId
                            || chat.getReceiver() == chatUserId && chat.getSender() == firebaseUser!!.uid){
                            lastmsg = chat.getMessage()!!
                        }
                    }
                }

                when(lastmsg){
                    "defaultMsg" ->lastMessagetxt.text = "No Message"
                    "sent you an image." ->lastMessagetxt.text = "Image sent."

                    else-> lastMessagetxt.text = lastmsg
                }
                lastmsg = "defaultMsg"
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

    }


}