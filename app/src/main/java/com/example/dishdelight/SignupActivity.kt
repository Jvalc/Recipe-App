package com.example.dishdelight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    // Declare variables for UI components
    private lateinit var fullNameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var signUpButton: Button
    private lateinit var googleSignUpButton: Button
    private lateinit var backIcon: ImageView
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id)) // Replace with your web client ID
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        fullNameInput = findViewById(R.id.fullNameInput)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        signUpButton = findViewById(R.id.signUpButton)
        googleSignUpButton = findViewById(R.id.googleSignUpButton)
        backIcon = findViewById(R.id.backIcon)


        googleSignUpButton.setOnClickListener { signInWithGoogle() }

        // Set up click listeners
        signUpButton.setOnClickListener {
            handleSignUp()
        }

        googleSignUpButton.setOnClickListener {
            // Handle Google Sign Up
        }

        backIcon.setOnClickListener {
            // Handle back navigation
            finish() // This will close the activity and return to the previous one
        }
    }

    private fun handleSignUp() {
        // Handle the sign-up logic
        val fullName = fullNameInput.text.toString()
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()
        val confirmPassword = confirmPasswordInput.text.toString()

        // Validate input and perform sign-up (e.g., API call, Firebase Authentication, etc.)
        if (password == confirmPassword) {
            // Proceed with sign-up logic
        } else {
            // Show error message
        }
    }

    private fun signInWithGoogle() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account)
        } catch (e: ApiException) {
            Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    Toast.makeText(this, "Google sign in successful", Toast.LENGTH_SHORT).show()
                    // Navigate to the main activity
                    navigateToMainActivity()
                } else {
                    // If sign in fails
                    Toast.makeText(this, "Google sign in failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java) // Replace with your main activity
        startActivity(intent)
        finish()
    }
}
