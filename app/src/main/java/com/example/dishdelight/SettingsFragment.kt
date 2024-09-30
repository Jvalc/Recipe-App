package com.example.dishdelight

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Switch

class SettingsFragment : Fragment() {
    private lateinit var editProfile : LinearLayout
    private lateinit var terms : LinearLayout
    private lateinit var notification : Switch
    private lateinit var dietary : LinearLayout

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
        notification = view.findViewById(R.id.notifications_switch)
        dietary = view.findViewById(R.id.dietary)


        editProfile.setOnClickListener {
            val intent = Intent(it.context, EditProfileActivity::class.java)
            it.context.startActivity(intent)
        }

        terms.setOnClickListener{
            val intent = Intent(it.context, TermsActivity::class.java)
            it.context.startActivity(intent)
        }

        dietary.setOnClickListener{
            val intent = Intent(it.context,ChangeDietActivity::class.java)
            it.context.startActivity(intent)
        }
    }
}