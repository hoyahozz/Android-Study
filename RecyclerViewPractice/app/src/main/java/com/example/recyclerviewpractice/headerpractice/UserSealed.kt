package com.example.recyclerviewpractice.headerpractice

import com.example.recyclerviewpractice.R
import com.example.recyclerviewpractice.model.User

sealed class UserSealed {
    abstract val user: User
    abstract val layoutId: Int

    data class Parent( // Name
        override val user: User,
        override val layoutId: Int = VIEW_TYPE
    ) : UserSealed() {
        companion object {
            const val VIEW_TYPE = R.layout.item_parent_user
        }
    }
    data class Child( // Phone
        override val user: User,
        override val layoutId: Int = VIEW_TYPE
    ) : UserSealed() {

        companion object {
            const val VIEW_TYPE = R.layout.item_child_user
        }
    }
}