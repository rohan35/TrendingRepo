package com.raydevelopers.trendinggitrepos.adapter

import android.graphics.Canvas
import androidx.recyclerview.widget.RecyclerView

class RepoItemDecorator:RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if(parent.itemAnimator != null && parent.itemAnimator!!.isRunning) {
            return;
        }
        super.onDrawOver(c, parent, state)

    }
}