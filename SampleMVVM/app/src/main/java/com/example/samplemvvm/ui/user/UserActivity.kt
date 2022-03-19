package com.example.samplemvvm.ui.user

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplemvvm.BaseActivity
import com.example.samplemvvm.R
import com.example.samplemvvm.adapter.RecyclerViewDecoration
import com.example.samplemvvm.adapter.UserAdapter
import com.example.samplemvvm.databinding.ActivityUserBinding
import com.example.samplemvvm.util.log
import org.koin.android.ext.android.inject

class UserActivity : BaseActivity<ActivityUserBinding, UserViewModel>() {

    override val layoutResourceId: Int = R.layout.activity_user

    override val viewModelFactory: ViewModelProvider.Factory by inject()
    override val viewModel: UserViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
    }

    private lateinit var adapter: UserAdapter

    override fun initStartView() {
        log("UserActivity Create : initStartView")
        adapter = UserAdapter()

//        binding = ActivityUserBinding.inflate(layoutInflater)
//        val view = binding.root
//        setContentView(view)

        binding.textView.text = "zxcv"

        binding.recyclerView.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(this@UserActivity)
            this.addItemDecoration(RecyclerViewDecoration(10))
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        log("UserActivity Create : initDataBinding")
        viewModel.userFavoriteList.observe(this) {
            log("userFavoriteList")
            adapter.submitList(it)
        }

    }

    override fun initAfterBinding() {
        log("UserActivity Create : initAfterBinding")
    }
}
