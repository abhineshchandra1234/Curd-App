package com.example.curdapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.curdapp.databinding.ActivityMainBinding
import com.example.curdapp.db.Subscriber
import com.example.curdapp.db.SubscriberDatabase
import com.example.curdapp.db.SubscriberRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel

    companion object {
        val TAG = "Main"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory).get(SubscriberViewModel::class.java)
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.subscriberRecyclerview.layoutManager = LinearLayoutManager(this)
        displaySubscribersList()
    }

    private fun displaySubscribersList() {
        subscriberViewModel.subscribers.observe(this, Observer {
            Log.d(TAG, it.toString())
            binding.subscriberRecyclerview.adapter = MyRecyclerViewAdapter(it,
                { selectedItem: Subscriber -> listItemClicked(selectedItem) })
        })
    }

    private fun listItemClicked(subscriber: Subscriber) {
        Toast.makeText(this, "selected name is ${subscriber.name}", Toast.LENGTH_SHORT).show()
    }
}