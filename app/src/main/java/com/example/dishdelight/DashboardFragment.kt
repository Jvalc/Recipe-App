package com.example.dishdelight

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.dishdelight.RecipeAdapter
import com.google.firebase.auth.FirebaseAuth
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

        val userid = FirebaseAuth.getInstance().currentUser!!.uid

        // Fetch recipes from Firestore
        fetchRecipes()
    }

    private fun fetchRecipes() {
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
    }
}