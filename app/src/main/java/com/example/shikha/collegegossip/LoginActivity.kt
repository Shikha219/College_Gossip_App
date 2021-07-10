package com.example.shikha.collegegossip

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.signup_activity.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val button = findViewById<Button>(R.id.b1)
        button.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        val button2 = findViewById<Button>(R.id.fd)
        button2.setOnClickListener{
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }


        mAuth = FirebaseAuth.getInstance()

        login_button.setOnClickListener {
            loginUser()
        }

        fd.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Forgot Password")
            val view: View = layoutInflater.inflate(R.layout.dialog_forgot_password,null)
            val username: EditText = view.findViewById<EditText>(R.id.et_username)
            builder.setView(view)
            builder.setPositiveButton("Reset",DialogInterface.OnClickListener { dialog, which ->
                forgotPassword(username)
            })
            builder.setNegativeButton("Cancel",DialogInterface.OnClickListener{ dialogInterface: DialogInterface, i: Int ->

            })
            builder.show()
        }

            FirebaseAuth.getInstance()
        }



    private fun forgotPassword(username: EditText) {
        if (username.text.toString().isEmpty()){
            return

        }
        mAuth.sendPasswordResetEmail(username.text.toString())
            .addOnCompleteListener { task->
                if (task.isSuccessful){
                    Toast.makeText(this,"Email sent",Toast.LENGTH_LONG).show()
                }
            }

    }

    private fun loginUser() {
        val email = email_login.text.toString()
        val password = password_login.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this@LoginActivity,"Please enter text in email/password", Toast.LENGTH_SHORT).show()
        }
        else{
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener {task->
                        if (task.isSuccessful) {
                            val intent = Intent(this@LoginActivity,MainActivity2::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            Toast.makeText(this,"Please enter correct email/password", Toast.LENGTH_LONG).show()
                        }
                    }

        }

    }

}
