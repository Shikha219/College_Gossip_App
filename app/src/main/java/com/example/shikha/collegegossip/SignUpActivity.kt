package com.example.shikha.collegegossip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.signup_activity.*


class SignUpActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var firebaseUserID: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_activity)

        val button = findViewById<Button>(R.id.b2)
        button.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        mAuth = FirebaseAuth.getInstance()

        signup.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val fullName = fullname.text.toString()
        val username = username_register.text.toString()
        val eMail = email.text.toString()
        val pHone = phone.text.toString()
        val passWord = password.text.toString()

        if (eMail.isEmpty() || passWord.isEmpty()) {
            Toast.makeText(this, "Please enter text in email/password", Toast.LENGTH_SHORT).show()
        } else if (username == "") {
            Toast.makeText(this, "Please enter text in username", Toast.LENGTH_SHORT).show()
        } else {
            mAuth.createUserWithEmailAndPassword(eMail, passWord)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            firebaseUserID = mAuth.currentUser!!.uid
                            refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUserID)

                            val userHashMap = HashMap<String, Any>()
                            userHashMap["uid"] = firebaseUserID
                            userHashMap["username"] = username
                            userHashMap["fullname"] = fullName
                            userHashMap["profile"] = "https://firebasestorage.googleapis.com/v0/b/college-gossip-10f04.appspot.com/o/profile-2398782_1280.png?alt=media&token=3459ca90-a7b4-4727-a019-bc190dfc504d"
                            userHashMap["cover"] = "https://firebasestorage.googleapis.com/v0/b/college-gossip-10f04.appspot.com/o/canva-simple-work-linkedin-banner-o8RYd-hyoQI.jpg?alt=media&token=4b35f31a-75c3-482a-871a-dc4921d473d8"
                            userHashMap["hometown"] = username
                            userHashMap["status"] = "offline"
                            userHashMap["search"] = username.toLowerCase()
                            userHashMap["dob"] = username
                            userHashMap["year"] = username
                            userHashMap["bio"] = username
                            userHashMap["facebook"] = "https://m.facebook.com"
                            userHashMap["instagram"] = "https://m.instagram.com"
                            userHashMap["website"] = "https://www.google.com"

                            refUsers.updateChildren(userHashMap)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            val intent = Intent(this@SignUpActivity, MainActivity2::class.java)
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                            startActivity(intent)
                                            finish()
                                        }

                                    }
                        } else {
                            Toast.makeText(this, "Error Message: " + task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                        }

                    }
        }
    }
}