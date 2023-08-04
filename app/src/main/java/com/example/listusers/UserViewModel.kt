package com.example.listusers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val _usersLiveData = MutableLiveData<List<User>>()
    val usersLiveData: LiveData<List<User>> get() = _usersLiveData

    private val _usersDetailLiveData = MutableLiveData<User>()
    val userDetailsData: LiveData<User> get() = _usersDetailLiveData

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading


    fun fetchUsers(limit: Int, skip: Int) {
        _loading.value = true
        Log.e("TAG", "fetchUsers: $limit--$skip")
        val call = RetrofitClient.apiService.getUsers(limit =limit, skip = skip)
        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    _loading.value = false
                   // _usersLiveData.value = response.body()?.users

                    val newUsers = response.body()?.users
                    val currentUsers = _usersLiveData.value ?: emptyList()
                    _usersLiveData.value = currentUsers + (newUsers ?: emptyList())

                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _loading.value = false
                Log.d("TAG", "onFailure: $t")
            }
        })


    }

     fun fetchUserDetails(userId: Int)  {
         _loading.value = true

         val call = RetrofitClient.apiService.getUserDetails(userId)

         call.enqueue(object : Callback<User> {
             override fun onResponse(call: Call<User>, response: Response<User>) {
                 if (response.isSuccessful) {
                     _loading.value = false

                     _usersDetailLiveData.value = response.body()
                 }
             }

             override fun onFailure(call: Call<User>, t: Throwable) {
                 Log.d("TAG", "onFailure: $t")
                 _loading.value = false

             }
         })


    }



}


