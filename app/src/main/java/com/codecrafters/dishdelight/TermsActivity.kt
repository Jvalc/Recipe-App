package com.codecrafters.dishdelight

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class TermsActivity : AppCompatActivity() {
    private  lateinit var btnBack : ImageView
    private lateinit var tvContent : TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms)

        btnBack = findViewById(R.id.backBtn4)
        tvContent = findViewById(R.id.tvContent)

        btnBack.setOnClickListener{
            finish()
        }

        val termsAndPolicies = """
            Terms and Conditions

            1. Introduction
            Welcome to Dish Delight. These Terms and Policies govern your use of our services, including our website, applications, and any related services (collectively referred to as "Service"). By accessing or using our Service, you agree to be bound by these Terms. If you do not agree with any part of these Terms, please do not use our Service.
            
            2. Use of Service
            You may use our Service only for lawful purposes and in accordance with these Terms. You agree not to use the Service:

            * In any way that violates any applicable federal, state, local, or international law or regulation.
            * To transmit, or procure the sending of, any advertising or promotional material, including any "junk mail," "chain letter," "spam," or any other similar solicitation.
            * To impersonate or attempt to impersonate Dish Delight, a Dish Delight employee, another user, or any other person or entity.
            * To engage in any other conduct that restricts or inhibits anyone's use or enjoyment of the Service, or which, as determined by us, may harm Dish Delight or users of the Service or expose them to liability.
            
            3. Privacy Policy
            Your privacy is important to us. Our Privacy Policy explains how we collect, use, disclose, and protect your information when you use our Service. By using our Service, you consent to the collection and use of your information as outlined in our Privacy Policy. We encourage you to review our Privacy Policy to understand our practices regarding your personal information.
            
            4. Changes to Terms
            We may revise these Terms from time to time at our discretion. We will notify you of any changes by posting the new Terms on this page and updating the "Last Updated" date at the top of this document. You are encouraged to review these Terms periodically for any changes. Your continued use of the Service after the posting of changes constitutes your acceptance of those changes. If you do not agree to the new Terms, you must stop using the Service.


            Last updated: 22 October 2024
        """.trimIndent()

        tvContent.text = termsAndPolicies
    }
}