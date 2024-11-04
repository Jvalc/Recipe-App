package com.codecrafters.dishdelight

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoldersFragment : Fragment() {
    private lateinit var tvName : TextView

    private lateinit var folderRecycler : RecyclerView
    private lateinit var folderAdapter: FolderAdapter

    private lateinit var timePicker: TimePicker
    private lateinit var startTimerButton: Button
    private lateinit var stopTimerButton: Button
    private lateinit var pauseTimerButton: Button
    private lateinit var tvCountdown:TextView

    private var timer: CountDownTimer? = null
    private var timerRunning = false
    private var timeLeftInMillis: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_folders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvName = view.findViewById(R.id.tvUserName)
        folderRecycler = view.findViewById(R.id.folders_recycler)
        timePicker = view.findViewById(R.id.timePicker)
        startTimerButton = view.findViewById(R.id.btnStartTimer)
        stopTimerButton = view.findViewById(R.id.btnStopTimer)
        pauseTimerButton = view.findViewById(R.id.btnPauseTimer)
        tvCountdown = view.findViewById(R.id.tvCountdown)

        folderRecycler.layoutManager = LinearLayoutManager(context)

        val userid = FirebaseAuth.getInstance().currentUser!!.uid

        fetchFolders(userid)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.hour = 0
            timePicker.minute = 0
        } else {
            timePicker.currentHour = 0
            timePicker.currentMinute = 0
        }
        timePicker.setIs24HourView(true)

        startTimerButton.setOnClickListener {
            val hours = timePicker.hour
            val minutes = timePicker.minute

            if (hours == 0 && minutes == 0) {
                // If both hours and minutes are zero, show a toast
                Toast.makeText(requireContext(), "Please enter a time.", Toast.LENGTH_SHORT).show()
            } else if (tvCountdown.visibility == View.VISIBLE) {
                Toast.makeText(requireContext(), "Timer has already started", Toast.LENGTH_SHORT).show()
            } else {
                startTimer()
                stopTimerButton.visibility = View.VISIBLE
                pauseTimerButton.visibility = View.VISIBLE
            }
        }
        stopTimerButton.setOnClickListener {
            stopTimer()
        }
        pauseTimerButton.setOnClickListener {
            pauseTimer()
        }
    }
    private fun fetchFolders( userid : String) {
        RetrofitClient.getClient().create(RecipeApiService::class.java)
            .getCategories(userid).enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        folderAdapter = FolderAdapter(requireContext(), it)
                        folderRecycler.adapter = folderAdapter
                    }
                } else{
                    Toast.makeText(requireContext(), "Failed to fetch folder names", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun startTimer() {
        val hours = timePicker.hour
        val minutes = timePicker.minute
        timeLeftInMillis = (hours * 3600000 + minutes * 60000).toLong() // Convert hours and minutes to milliseconds
        tvCountdown.visibility = View.VISIBLE
        timePicker.visibility = View.GONE

        timer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateCountdownUI()
            }

            override fun onFinish() {
                timerRunning = false
                updateCountdownUI() // Reset UI when finished
                tvCountdown.visibility = View.GONE
            }
        }.start()
        timerRunning = true
    }
    private fun stopTimer() {
        timer?.cancel()
        timerRunning = false
        tvCountdown.visibility = View.GONE
        timePicker.visibility = View.VISIBLE
    }
    private fun pauseTimer() {
        stopTimer() // Cancel and reset everything
        timeLeftInMillis = 0
        updateCountdownUI()
    }
    private fun updateCountdownUI() {
        val hours = (timeLeftInMillis / 1000) / 3600
        val minutes = ((timeLeftInMillis / 1000) % 3600) / 60
        val seconds = (timeLeftInMillis / 1000) % 60

        // Update timer UI (e.g., show hours, minutes, seconds)
        // Example:
        val timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        // Update your TextView or TimePicker with this value
        tvCountdown.text = timeFormatted
    }
    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel() // Clean up the timer to prevent memory leaks
    }
}