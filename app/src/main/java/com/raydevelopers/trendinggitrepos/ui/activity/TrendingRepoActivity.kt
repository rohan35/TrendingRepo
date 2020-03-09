package com.raydevelopers.trendinggitrepos.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raydevelopers.trendinggitrepos.R
import com.raydevelopers.trendinggitrepos.ui.fragment.TrendingRepoFragment

class TrendingRepoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trending_repo_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TrendingRepoFragment.newInstance())
                .commitNow()
        }
    }

}
