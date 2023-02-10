package com.example.woodpeaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.woodpeaker.daos.FirebaseDao.auth
import com.example.woodpeaker.daos.UserDao
import com.example.woodpeaker.databinding.ActivityLoginBinding
import com.example.woodpeaker.models.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class Login : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.lv345)
        binding.signIn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java)) })
        binding.gotoSignUp.setOnClickListener(View.OnClickListener {
            binding.signinLayout.visibility=View.GONE
            binding.signupLayout.visibility=View.VISIBLE
        })
        binding.gotoSignin.setOnClickListener(View.OnClickListener {
            binding.signinLayout.visibility=View.VISIBLE
            binding.signupLayout.visibility=View.GONE
        })
        binding.google.setOnClickListener(View.OnClickListener { googleSignIn() })
    }
    private fun googleSignIn() {
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        var signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 123)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 123) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken)
            } catch (e: ApiException) {
                Log.d("TAG", "onActivityResult EXEPTION : " + e.localizedMessage)
                // Google Sign In failed, update UI appropriately
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        allButtonsVisibility(View.INVISIBLE)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this,
                OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "signInWithCredential:success")
                        Log.d("TAG", "task.getResult().toString()=" + task.result.toString())
                        updateUI(auth.currentUser)
                    } else {
                        Log.d("TAG", "signInWithCredential:failure", task.exception)
                        updateUI(null)
                    }
                })
    }


    override fun onStart() {
        super.onStart()
//        auth.currentUser.let { updateUI(it)}
        if(auth.currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else{
            updateUI(auth.currentUser)
        }
    }

    fun updateUI(firebaseUser: FirebaseUser?) {
        if(firebaseUser!=null){
            UserDao.getUser(firebaseUser!!.uid).addOnSuccessListener { document->
                if(document.exists()){
                    Toast.makeText(this,"welcome Back!", Toast.LENGTH_SHORT).show()
                }else{
                    val user= User()
                    user.id=firebaseUser.uid
                    user.name= firebaseUser.displayName!!
                    user.email= firebaseUser.email!!
                    UserDao.addUser(user)
                    startActivity(Intent(this, ProfileGeneral::class.java))
                }
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }.addOnFailureListener { exception-> Log.d("TAG","updateUI:onFailure:"+exception.localizedMessage) }
        }
    }

    fun allButtonsVisibility(visib: Int) {
        binding.signIn.visibility=visib
        binding.gotoSignUp.visibility=visib
        binding.google.visibility=visib
        binding.facebook.visibility=visib
        binding.twitter.visibility=visib
        binding.forgetPassword.visibility=visib
        binding.progressBar.visibility=when(visib==View.INVISIBLE){ true-> View.VISIBLE false-> View.INVISIBLE}
    }
    fun signinOrSignup(a:String){
        val email=binding.email.text.toString()
        val password=binding.password.text.toString()
        if(a=="signIn") signIn(email,password)
        else signUp(email,password)
    }

    private fun signIn(email:String,password:String){
        allButtonsVisibility(View.INVISIBLE)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithEmail:success")
                    updateUI(auth.currentUser)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }.addOnFailureListener {
                allButtonsVisibility(View.VISIBLE)
                Log.d("TAG",it.localizedMessage)
                Toast.makeText(this,it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }
    fun signUp(email:String,password:String){
        allButtonsVisibility(View.INVISIBLE)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    updateUI(auth.currentUser)
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }
}