package com.example.curdapp

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.curdapp.db.Subscriber
import com.example.curdapp.db.SubscriberRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel(), Observable {

    val subscribers = repository.subscribers
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete : Subscriber

    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        val name: String = inputName.value!!
        val email: String = inputEmail.value!!
        insert(Subscriber(0,name, email))
        inputName.value = null
        inputEmail.value = null
    }

    fun clearAllOrDelete() {
            clearAll()
    }

    fun insert(subscriber: Subscriber): Job =
        viewModelScope.launch {
            repository.insert(subscriber)
        }

    fun update(subscriber: Subscriber): Job =
        viewModelScope.launch {
            repository.update(subscriber)
        }

    fun delete(subscriber: Subscriber): Job =
        viewModelScope.launch {
            repository.delete(subscriber)
        }

    fun clearAll(): Job =
        viewModelScope.launch {
            repository.deleteAll()
        }

    fun initUpdateAndDelete(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}