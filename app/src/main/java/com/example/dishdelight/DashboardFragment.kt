package com.example.dishdelight

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.dishdelight.RecipeAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DashboardFragment : Fragment() {
    private lateinit var recipeRecycler : RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recipeList: MutableList<Recipe>
    private val db: FirebaseFirestore = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize RecyclerView
        recipeRecycler = view.findViewById(R.id.recommendationsRecyclerView)
        recipeRecycler.layoutManager = LinearLayoutManager(requireContext())

        // Initialize recipe list and adapter
        recipeList = mutableListOf()
        recipeAdapter = RecipeAdapter(requireContext(), recipeList)
        recipeRecycler.adapter = recipeAdapter

        // Fetch recipes from Firestore
        fetchRecipes()
    }

    private fun fetchRecipes() {
        db.collection("recipes") // Replace with your collection name
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val recipe = document.toObject(Recipe::class.java)
                    recipeList.add(recipe)
                }
                recipeAdapter.notifyDataSetChanged() // Notify adapter of data changes
            }
            .addOnFailureListener { exception ->
                // Handle error
                exception.printStackTrace()
            }
    }

    /*private fun fetchRecommendedRecipes() {
        val apiService = RetrofitClient.getClient().create(RecipeApiService::class.java)
        val call = apiService.getRecipes() // Assuming getRecipes returns Call<List<RecipeResponse>>

        call.enqueue(object : Callback<List<RecipeResponse>> {
            override fun onResponse(call: Call<List<RecipeResponse>>, response: Response<List<RecipeResponse>>) {
                if (response.isSuccessful && response.body() != null) {
                    // Clear the current recipe list
                    recipeList.clear()

                    // Map RecipeResponse objects to Recipe objects and add them to recipeList
                    response.body()?.let { recipeResponseList ->
                        val recipeListMapped = recipeResponseList.map { recipeResponse ->
                            Recipe(
                                id = recipeResponse.recipes, // Assuming Recipe and RecipeResponse have similar fields
                                name = recipeResponse.name,
                                ingredients = recipeResponse.ingredients,
                                instructions = recipeResponse.instructions
                                // Add other fields if needed
                            )
                        }
                        recipeList.addAll(recipeListMapped)
                    }

                    recipeAdapter.notifyDataSetChanged() // Notify the adapter of data changes
                }
            }

            override fun onFailure(call: Call<List<RecipeResponse>>, t: Throwable) {
                // Handle failure
                t.printStackTrace() // or show an error message to the user
            }
        })
    }*/

    /*private fun fetchRecipes() {
        val apiService = RetrofitClient.getClient().create(RecipeApiService::class.java)
        val call = apiService.getRecipes() // API call

         call.enqueue(object : Callback<List<Recipe>> {
             override fun onResponse(call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                 if (response.isSuccessful){
                     val recipeResponse = response.body()
                     recipeResponse?.let{
                         for (Recipe in it){
                             recipeList.add(Recipe)
                         }
                     }
                    /* response.body()?.let {
                         for (Recipe in it){
                             recipeList.add(Recipe)
                         }
                     } */
                     recipeRecycler.layoutManager = LinearLayoutManager(context)

                     recipeAdapter = RecipeAdapter(requireContext(), recipeList)
                     recipeRecycler.adapter = recipeAdapter
                     recipeAdapter.notifyDataSetChanged()
                 }
             }
             override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                 // Handle failure (e.g., show an error message)
                 Log.i("GET_LIST", "onFailure: ${t.message}")
             }
         })
    }*/
}