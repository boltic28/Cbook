package com.boltic28.kointest.repository.user

import com.boltic28.kointest.User

class UserRepository(private val dao: UserDao) {

    fun insert(user: User){
        dao.insert(userToEntity(user))
    }

    fun update(user: User){
        dao.update(userToEntity(user))
    }

    fun delete(user: User){
        dao.delete(userToEntity(user))
    }

    fun readAll(): List<User> =
        dao.getUsers().map { entityToUser(it) }

    override fun toString(): String = "repository is injected"
}