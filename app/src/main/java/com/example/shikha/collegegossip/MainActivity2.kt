package com.example.shikha.collegegossip

import android.content.Intent
import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TableLayout
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.appcompat.widget.DialogTitle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.shikha.collegegossip.Fragments.ChatFragment
import com.example.shikha.collegegossip.Fragments.FeedFragment
import com.example.shikha.collegegossip.Fragments.ProfileFragment
import com.example.shikha.collegegossip.Fragments.SreachFragment
import com.example.shikha.collegegossip.ModelClasses.Chat
import com.example.shikha.collegegossip.ModelClasses.Users
import com.example.shikha.collegegossip.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.signup_activity.*

class MainActivity2 : AppCompatActivity() {
    var refUsers: DatabaseReference? = null
    var firebaseUser: FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)



        firebaseUser = FirebaseAuth.getInstance().currentUser
        refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)

        val ref = FirebaseDatabase.getInstance().reference.child("Chats")
        ref!!.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {

                val adapter = ViewPagerAdapter(supportFragmentManager)
                adapter.addFragment(FeedFragment(),"")
                var countUnreadMessages = 0
                for (dataSnapshot in p0.children)
                {
                    val chat = dataSnapshot.getValue((Chat::class.java))
                    if (chat!!.getReceiver().equals(firebaseUser!!.uid) && !chat.isIsSeen()){
                        countUnreadMessages += 1
                    }
                }
                if (countUnreadMessages ==0){
                    adapter.addFragment(ChatFragment(),"")

                }
                else{
                    adapter.addFragment(ChatFragment(),"($countUnreadMessages) ")

                }

                adapter.addFragment(SreachFragment(),"")
                adapter.addFragment(ProfileFragment(),"")

                view_pager.adapter = adapter
                tab_layout.setupWithViewPager(view_pager)

                tab_layout.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_chat_24)
                tab_layout.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_dynamic_feed_24)
                tab_layout.getTabAt(2)!!.setIcon(R.drawable.ic_baseline_search_24)
                tab_layout.getTabAt(3)!!.setIcon(R.drawable.ic_baseline_perm_contact_calendar_24)


            }

            override fun onCancelled(error: DatabaseError) {


            }
        })


        //display username and profile picture

        refUsers!!.addValueEventListener(object : ValueEventListener{
            override  fun onDataChange(p0: DataSnapshot){
                if (p0.exists()){
                    val user: Users? = p0.getValue(Users::class.java)

                    user_name.text = user!!.getUSERNAME()
                    Picasso.get().load(user.getPROFILE()).into(profile_image)


                }
            }
            override fun onCancelled(p0: DatabaseError){

            }
        })

        logout_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@MainActivity2,LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_logout ->
            {

                return true
            }
        }
        return false
    }

    private fun updateStatus(status: String){
        val ref = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)

        val hashMap = HashMap<String,Any>()
        hashMap["status"] = status
        ref!!.updateChildren(hashMap)
    }

    override fun onResume() {
        super.onResume()

        updateStatus("online")
    }

    override fun onPause() {
        super.onPause()

        updateStatus("offline")
    }

}
