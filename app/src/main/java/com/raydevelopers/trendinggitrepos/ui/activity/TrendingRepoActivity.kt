package com.raydevelopers.trendinggitrepos.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.raydevelopers.trendinggitrepos.R
import com.raydevelopers.trendinggitrepos.ui.fragment.TrendingRepoFragment
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.ViewDataBinding
import com.raydevelopers.trendinggitrepos.BR
import com.raydevelopers.trendinggitrepos.ui.viewmodel.TrendingRepoActivityViewModel
import com.raydevelopers.trendinggitrepos.ui.viewmodel.TrendingRepoViewModel
import kotlinx.android.synthetic.main.trending_repo_activity.view.*


class TrendingRepoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.trending_repo_activity)
        binding.setVariable(BR.mainViewModel, TrendingRepoActivityViewModel())
        binding.executePendingBindings()
        setSupportActionBar(binding.root.toolBar)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        supportActionBar?.setDisplayShowTitleEnabled(false);
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TrendingRepoFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // not handled cases as it was not supoosed to do in the description

        return super.onOptionsItemSelected(item)
    }
}
