package com.boltic28.kointest.repository.user

import com.boltic28.kointest.User

fun userToEntity(user: User): UserEntity =
    UserEntity(
        id = user.id,
        name = user.name,
        age = user.age
    )

fun entityToUser(entity: UserEntity): User =
    User(
        id = entity.id,
        name = entity.name,
        age = entity.age
    )