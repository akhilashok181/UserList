package com.example.listusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.listusers.databinding.ActivityMainBinding
import com.example.listusers.databinding.ActivityUserDatailsBinding


class UserDatailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDatailsBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDatailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getIntExtra("USER_ID",0)


        if (userId != null) {

            userViewModel.fetchUserDetails(userId)

        }

        userViewModel.userDetailsData.observe(this){
                binding.user=it

                Glide.with(binding.profileImage.rootView)
                    .load(it.image)
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .circleCrop()
                    .into(binding.profileImage)
        }

        userViewModel.loading.observe(this) { isLoad ->
            if (isLoad) {
                binding.progressBar.visibility= View.VISIBLE
            } else {
                binding.progressBar.visibility= View.GONE
            }
        }

        binding.imgBack.setOnClickListener()
        {
            finish()
        }
    }
}

