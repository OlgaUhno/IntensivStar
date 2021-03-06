package ru.androidschool.intensiv.ui

import androidx.navigation.navOptions
import ru.androidschool.intensiv.R

val navigationOptions = navOptions {
    anim {
        enter = R.anim.slide_in_right
        exit = R.anim.slide_out_left
        popEnter = R.anim.slide_in_left
        popExit = R.anim.slide_out_right
    }
}
