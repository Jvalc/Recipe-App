package com.example.dishdelight

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchFragment : Fragment() {

    private lateinit var searchIngredient: EditText
    private lateinit var searchDiet: EditText
    private lateinit var searchCuisine: EditText
    private lateinit var searchBtn: Button
    private val db: FirebaseFirestore = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchIngredient = view.findViewById(R.id.searchIngredient)
        searchDiet = view.findViewById(R.id.searchDiet)
        searchCuisine = view.findViewById(R.id.searchCuisine)
        searchBtn = view.findViewById(R.id.searchBtn)

        searchBtn.setOnClickListener {
            performSearch()
        }
    }

    private fun performSearch() {
        val ingredientsInput = searchIngredient.text.toString().trim()
        val dietInput = searchDiet.text.toString().trim()
        val cuisineInput = searchCuisine.text.toString().trim()

        // Split the ingredients into a list
        val ingredientsList = ingredientsInput.split(",").map { it.trim() }

        // Perform the search in Firestore
        db.collection("recipes")
            .whereArrayContainsAny("ingredients", ingredientsList) // Use ingredients array
            .whereEqualTo("dietaryPreferences", dietInput) // Adjust the field name accordingly
            .whereEqualTo("cuisineType", cuisineInput) // Adjust the field name accordingly
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Toast.makeText(requireContext(), "No recipes found", Toast.LENGTH_SHORT).show()
                } else {
                    val foundRecipes = documents.toObjects(Recipe::class.java)
                    val intent = Intent(requireContext(), SearchRecipeActivity::class.java)
                    intent.putParcelableArrayListExtra("recipeList", ArrayList(foundRecipes))
                    startActivity(intent)
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Error fetching recipes: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}