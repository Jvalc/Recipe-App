package com.codecrafters.dishdelight

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment() {
    private lateinit var editProfile: LinearLayout
    private lateinit var terms: LinearLayout
    private lateinit var logOut: LinearLayout
    private lateinit var dietary: LinearLayout
    private lateinit var languageSelection: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editProfile = view.findViewById(R.id.editProfile)
        terms = view.findViewById(R.id.terms)
        logOut = view.findViewById(R.id.logOut)
        dietary = view.findViewById(R.id.dietary)
        languageSelection = view.findViewById(R.id.languageSelection)

        editProfile.setOnClickListener {
            val intent = Intent(it.context, EditProfileActivity::class.java)
            it.context.startActivity(intent)
        }
        terms.setOnClickListener {
            val intent = Intent(it.context, TermsActivity::class.java)
            it.context.startActivity(intent)
        }
        dietary.setOnClickListener {
            val intent = Intent(it.context, ChangeDietActivity::class.java)
            it.context.startActivity(intent)
        }
        languageSelection.setOnClickListener {
            val intent = Intent(context, LanguageSelectionActivity::class.java)
            startActivity(intent)
        }
        logOut.setOnClickListener {
            promptLogOut()
        }
    }
    private fun promptLogOut() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Are you sure you want to sign out?")

        builder.setPositiveButton("Yes") { _, _ ->
            val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut()

            val intent = Intent(requireContext(), Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        builder.show()
    }
}
