package com.example.samplemvvm.ui.user

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplemvvm.BaseActivity
import com.example.samplemvvm.R
import com.example.samplemvvm.adapter.RecyclerViewDecoration
import com.example.samplemvvm.adapter.UserAdapter
import com.example.samplemvvm.databinding.ActivityUserBinding
import org.koin.android.ext.android.inject

class UserActivity : BaseActivity<ActivityUserBinding, UserViewModel>() {

    override val layoutResourceId: Int = R.layout.activity_user

    override val viewModelFactory: ViewModelProvider.Factory by inject()
    override val viewModel: UserViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
    }

    private val adapter : UserAdapter by lazy {
        UserAdapter()
    }

    override fun initStartView() {

        binding.recyclerView.apply {
            this.layoutManager = LinearLayoutManager(this@UserActivity)
            this.adapter = this@UserActivity.adapter
            this.addItemDecoration(RecyclerViewDecoration(10))
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.userFavoriteList.observe(this) {
            adapter.submitList(it)
        }

    }

    override fun initAfterBinding() {

    }
}


//class UserActivity : AppCompatActivity() {
//
//    private lateinit var userViewModel : UserViewModel
//    private val viewModelFactory: ViewModelProvider.Factory by inject()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val binding = ActivityUserBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_user)
//
//        userViewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
//        val adapter = UserAdapter()
//
//        binding.recyclerView.apply {
//            this.layoutManager = LinearLayoutManager(this@UserActivity)
//            this.adapter = adapter
//            this.addItemDecoration(RecyclerViewDecoration(10))
//        }
//
//        userViewModel.userFavoriteList.observe(this) {
//            adapter.submitList(it)
//        }
//    }
//}