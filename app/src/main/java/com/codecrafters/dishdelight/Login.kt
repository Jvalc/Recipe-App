package com.codecrafters.dishdelight

import android.content.Intent
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class Login : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    // UI elements
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var loginButton: Button
    private lateinit var googleSignInButton: Button
    private lateinit var signUpTextView: TextView
    private lateinit var biometricPrompt: BiometricPrompt

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Configure Google Sign-In options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id)) // Replace with your web client ID
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        googleSignInButton = findViewById(R.id.googleSignInButton)
        signUpTextView = findViewById(R.id.signUpTextView)

        // Set onClick listeners for buttons and text views
        loginButton.setOnClickListener { logIn() }
        googleSignInButton.setOnClickListener { signInWithGoogle() }
        signUpTextView.setOnClickListener {
            val intent = Intent(this@Login, SignupActivity::class.java)
            startActivity(intent)
        }

    }

    // Check if biometric support is available
    private fun checkBiometricSupport(): Boolean {
        val biometricManager = BiometricManager.from(this)
        return when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> true // Biometric authentication is supported
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Toast.makeText(this, "No biometric hardware available", Toast.LENGTH_LONG).show()
                false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Toast.makeText(this, "Biometric hardware unavailable", Toast.LENGTH_LONG).show()
                false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Toast.makeText(this, "No biometric data enrolled", Toast.LENGTH_LONG).show()
                false
            }
            else -> false
        }
    }

    // Setup biometric authentication prompt
    private fun setupBiometricPrompt() {
        val executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(this@Login, "Authentication Successful!", Toast.LENGTH_LONG).show()
                // Log in the user with Firebase
                autoLoginWithFirebase()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(this@Login, "Error: $errString", Toast.LENGTH_LONG).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(this@Login, "Authentication Failed", Toast.LENGTH_SHORT).show()
            }
        })

        // Create the biometric prompt information
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Login with Fingerprint")
            .setSubtitle("Use biometric authentication to log in")
            .setNegativeButtonText("Cancel")
            .build()

        // Show biometric prompt on activity start if the user is already signed in
        if (auth.currentUser != null) {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    // Automatically log in with Firebase if the user is already authenticated
    private fun autoLoginWithFirebase() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is authenticated, proceed to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "No authenticated user found", Toast.LENGTH_LONG).show()
        }
    }

    // Method for email and password login
    fun logIn() {
        val email = emailEditText.text.toString()
        val pass = passwordEditText.text.toString()

        // Check for empty fields
        if (email.isBlank() || pass.isBlank()) {
            Toast.makeText(this, "Email and password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        // Authenticate user with email and password
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Log In Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Method to sign in with Google
    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent // Get Google sign-in intent
        startActivityForResult(signInIntent, 10001) // Start activity for result
    }

    // Handle the result of Google sign-in
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)

            // Sign in with Firebase using Google credentials
            auth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Automatically navigate to MainActivity if the user is already authenticated on start
    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            // User is already signed in, go to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
