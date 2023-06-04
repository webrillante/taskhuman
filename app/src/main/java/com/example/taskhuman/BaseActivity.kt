package com.example.taskhuman

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.taskhuman.api.ApiHelper
import com.example.taskhuman.api.RetrofitBuilder
import com.example.taskhuman.databinding.BaseActivityBinding

class BaseActivity: AppCompatActivity() {

    private lateinit var binding: BaseActivityBinding
    private lateinit var viewModel: BaseActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BaseActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewModel()
        replaceFragment(DiscoverFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.discover -> replaceFragment(DiscoverFragment())
                R.id.reconnect -> replaceFragment(DiscoverFragment())
                R.id.bookings -> replaceFragment(DiscoverFragment())
                R.id.messages -> replaceFragment(DiscoverFragment())
                R.id.blogs -> replaceFragment(DiscoverFragment())
                else -> {}
            }
            true
        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            this,
            BaseActivityViewModel.ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        )[BaseActivityViewModel::class.java]
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}