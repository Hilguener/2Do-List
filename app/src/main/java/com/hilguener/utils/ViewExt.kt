package com.hilguener.utils

import androidx.appcompat.widget.SearchView

inline fun SearchView.onQueryTextChanged(crossinline listener : (String) -> Unit){
    this.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(p0: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            listener(newText.orEmpty())
            return true
        }

    })
}