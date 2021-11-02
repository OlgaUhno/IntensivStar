package ru.androidschool.intensiv.ui.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProfileAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return arrayList.size
    }

    private val arrayList: ArrayList<Fragment> = ArrayList()

    fun addFragment(fragment: Fragment?) {
        fragment?.let {
            arrayList.add(it)
        }
    }

    override fun createFragment(position: Int): Fragment = arrayList[position]
}
