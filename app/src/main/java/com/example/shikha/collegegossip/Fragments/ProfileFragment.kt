package com.example.shikha.collegegossip.Fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.shikha.collegegossip.ModelClasses.Users
import com.example.shikha.collegegossip.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    var userResference: DatabaseReference? = null
    var firebaseUser: FirebaseUser? = null
    private val RequestCode = 438
    private var imageURI: Uri? = null
    private var storageRef: StorageReference? = null
    private var coverChecker: String? = null
    private var socialChecker: String? = null
    private var editTextChecker: String? = null



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser
        userResference = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        storageRef = FirebaseStorage.getInstance().reference.child("User Images")
        userResference!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    val user: Users? = p0.getValue(Users::class.java)

                    if (context!=null){
                        view.username_profile.text = user!!.getUSERNAME()
                        Picasso.get().load(user.getPROFILE()).into(view.profile_pic)
                        Picasso.get().load(user.getCOVER()).into(view.cover_image)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        view.profile_pic.setOnClickListener{
            pickImage()
        }

        view.cover_image.setOnClickListener {
            coverChecker = "cover"
            pickImage()
        }

        view.set_facebook.setOnClickListener{
            socialChecker = "facebook"
            setSocialLinks()
        }

        view.set_instagram.setOnClickListener{
            socialChecker = "instagram"
            setSocialLinks()
        }

        view.set_website.setOnClickListener{
            socialChecker = "website"
            setSocialLinks()
        }

        view.e1.setOnClickListener {
            editTextChecker = "fullname"
            setEditText()
        }

        view.e2.setOnClickListener {
            editTextChecker = "dob"
            setEditText()
        }

        view.e3.setOnClickListener {
            editTextChecker = "hometown"
            setEditText()
        }

        view.e4.setOnClickListener {
            editTextChecker = "currentyear"
            setEditText()
        }

        view.e5.setOnClickListener {
            editTextChecker = "bio"
            setEditText()
        }


        return view
    }

    private fun setEditText() {

        val builder: AlertDialog.Builder =
            AlertDialog.Builder(requireContext(),R.style.ThemeOverlay_AppCompat_DayNight)
        if (editTextChecker == "fullname"){
            builder.setTitle("Write Full Name:")
        }
        else if (editTextChecker == "dob"){
            builder.setTitle("Write Date of Birth:")

        }
        else if (editTextChecker == "hometown"){
            builder.setTitle("Where are you from:")

        }
        else if (editTextChecker == "year"){
            builder.setTitle("What is your Current year in College:")

        }
        else{
            builder.setTitle("Write About Yourself:")

        }

        val editText= EditText(context)
        if (editTextChecker == "fullname"){
            editText.hint = "e.g John Smith"
        }
        else if (editTextChecker == "dob"){
            editText.hint = "e.g 12-12-2012"

        }
        else if (editTextChecker == "hometown"){
            editText.hint = "e.g patna"

        }
        else if (editTextChecker == "year"){
            editText.hint = "e.g 2nd"

        }

        else{
            editText.hint = "limit 80 words"

        }
        builder.setView(editText)

        builder.setPositiveButton("Create", DialogInterface.OnClickListener {
                dialog, which ->
            var str = editText.text.toString()

            if(str ==""){
                Toast.makeText(context, "Please write something...",Toast.LENGTH_LONG).show()
            }
            else{
                saveDetails(str)
            }
        })
        builder.setNegativeButton("Cancle", DialogInterface.OnClickListener {
                dialog, which ->
            dialog.cancel()
        })
        builder.show()

    }

    private fun saveDetails(str: String) {
        val mapDetail =HashMap<String,Any>()


        when (editTextChecker){
            "fullname" ->
            {
                mapDetail["fullname"] = "$str"
            }

            "dob" ->
            {
                mapDetail["dob"] = "$str"
            }

            "hometown" ->
            {
                mapDetail["hometown"] = "$str"
            }
            "year" ->
            {
                mapDetail["year"] = "$str"
            }
            "bio" ->
            {
                mapDetail["bio"] = "$str"
            }
        }

        userResference!!.updateChildren(mapDetail).addOnCompleteListener {
                task ->
            if (task.isSuccessful){
                Toast.makeText(context,"updated successfully",Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun setSocialLinks() {
        val builder: AlertDialog.Builder =
                AlertDialog.Builder(requireContext(),R.style.ThemeOverlay_AppCompat_DayNight)
        if (socialChecker == "website"){
            builder.setTitle("Write URL:")
        }
        else{
            builder.setTitle("Write username:")

        }

        val editText= EditText(context)
        if (socialChecker == "website"){
            editText.hint = "e.g www.google.com"
        }
        else{
            editText.hint = "e.g abc123"

        }
        builder.setView(editText)

        builder.setPositiveButton("Create", DialogInterface.OnClickListener {
            dialog, which ->
            var str = editText.text.toString()

            if(str ==""){
                Toast.makeText(context, "Please write something...",Toast.LENGTH_LONG).show()
            }
            else{
                saveSocialLink(str)
            }
        })
        builder.setNegativeButton("Cancle", DialogInterface.OnClickListener {
            dialog, which ->
            dialog.cancel()
        })
        builder.show()
    }

    private fun saveSocialLink(str: String) {
        val mapSocial =HashMap<String,Any>()


        when (socialChecker){
            "facebook" ->
            {
                mapSocial["facebook"] = "https://m.facebook.com/$str"
            }

            "instagram" ->
            {
                mapSocial["instagram"] = "https://m.instagram.com/$str"
            }

            "website" ->
            {
                mapSocial["website"] = "https://$str"
            }
        }

        userResference!!.updateChildren(mapSocial).addOnCompleteListener {
            task ->
            if (task.isSuccessful){
                Toast.makeText(context,"updated successfully",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun pickImage() {
        val intent = Intent()
        intent.type ="image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,RequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RequestCode && resultCode == Activity.RESULT_OK && data!!.data != null){
            imageURI = data.data
            Toast.makeText(context, "uploading...", Toast.LENGTH_LONG).show()
            uploadImageToDatabase()
        }
    }

    private fun uploadImageToDatabase() {
        val progressBar = ProgressDialog(context)
        progressBar.setMessage("image is uploading,Please wait")
        progressBar.show()

        if (imageURI != null){
            val fileRef = storageRef!!.child(System.currentTimeMillis().toString() + ".jpg")
            var uploadTask: StorageTask<*>
            uploadTask = fileRef.putFile((imageURI!!))

            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>{task ->
                if (!task.isSuccessful){
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation fileRef.downloadUrl

            }).addOnCompleteListener { task->
                if (task.isSuccessful){
                    val downloadUrl = task.result
                    val url = downloadUrl.toString()

                    if (coverChecker == "cover"){
                        val mapCoverImg = HashMap<String, Any>()
                        mapCoverImg["cover"] = url
                        userResference!!.updateChildren(mapCoverImg)
                        coverChecker = ""
                    }
                    else{
                        val mapProfileImg = HashMap<String, Any>()
                        mapProfileImg["profile"] = url
                        userResference!!.updateChildren(mapProfileImg)
                        coverChecker = ""
                    }
                    progressBar.dismiss()
                }
            }
        }
    }
}

