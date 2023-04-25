package br.senai.sp.jandira.trip.repository

import android.content.Context
import br.senai.sp.jandira.trip.dao.TripDb
import br.senai.sp.jandira.trip.model.User

class userRepository(context: Context) {

    private val db = TripDb.getDatabase(context)

    fun save(user: User): Long{
        return db.userDao().save(user)
    }

    fun findUserByEmail(email: String): User {
        return db.userDao().findUserByEmail(email)
    }

    fun authenticate(email: String, password: String): User{
        return db.userDao().authenticate(email, password)
    }

}