package com.example.listusers
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listusers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),UserAdapter.OnItemClickListener  {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private val userViewModel: UserViewModel by viewModels()

    private var currentPage = 1
    private val limit = 10
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter(emptyList(),this)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }

        userViewModel.usersLiveData.observe(this) { users ->
            userAdapter.updateData(users)
            isLoading = false
            Log.e("TAG", "onCreate: " + users.size.toString())
        }

        userViewModel.fetchUsers(limit, 0)


        userViewModel.loading.observe(this) { isLoad ->
            if (isLoad) {
                    binding.progressBar.visibility=View.VISIBLE
            } else {
                binding.progressBar.visibility=View.GONE
            }
        }
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val visibleItemCount = recyclerView.layoutManager?.childCount ?: 0
                val totalItemCount = recyclerView.layoutManager?.itemCount ?: 0
                val firstVisibleItemPosition =
                    (recyclerView.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition() ?: 0

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0 && totalItemCount >= limit
                ) {
                    isLoading = true
                    currentPage++
                    userViewModel.fetchUsers(limit, totalItemCount)
    }
            }
        })


    }

    override fun onItemClick(user: User) {
        Log.e("TAG", "onItemClick: "+user.firstName )

        val intent = Intent(this, UserDatailsActivity::class.java)
        intent.putExtra("USER_ID", user.id)
        startActivity(intent)
    }
}
