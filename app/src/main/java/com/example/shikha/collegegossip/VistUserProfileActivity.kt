package com.example.shikha.collegegossip

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shikha.collegegossip.ModelClasses.Users
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_vist_user_profile.*

class VistUserProfileActivity : AppCompatActivity() {

    private var userVisitId: String = ""
    var user: Users? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vist_user_profile)

        userVisitId = intent.getStringExtra("visit_id").toString()

        val ref = FirebaseDatabase.getInstance().reference.child("Users").child(userVisitId)
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    user = p0.getValue(Users::class.java)

                    username_display.text = user!!.getUSERNAME()

                    Picasso.get().load(user!!.getPROFILE()).into(profile_display)
                    Picasso.get().load(user!!.getCOVER()).into(cover_display)

                    e1_display.text = user!!.getFULLNAME()
                    e2_display.text = user!!.getDOB()
                    e3_display.text = user!!.getHOMETOWN()
                    e4_display.text = user!!.getYEAR()
                    e5_display.text = user!!.getBIO()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        facebook_display.setOnClickListener {
            val uri = Uri.parse(user!!.getFACEBOOK())

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        instagram_display.setOnClickListener {
            val uri = Uri.parse(user!!.getINSTAGRAM())

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        website_display.setOnClickListener {
            val uri = Uri.parse(user!!.getWEBSITE())

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        send_msg_btn.setOnClickListener {
            val intent = Intent(this, MessageChatActivity::class.java)
            intent.putExtra("visit_id", user!!.getUID())
            startActivity(intent)
        }
    }
}