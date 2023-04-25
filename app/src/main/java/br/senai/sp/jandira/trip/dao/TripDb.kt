package br.senai.sp.jandira.trip.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.senai.sp.jandira.trip.model.User

@Database(entities = [User::class], version = 1)
abstract class TripDb : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        private lateinit var instanceDb: TripDb

        fun getDatabase(context: Context): TripDb {

            if (!::instanceDb.isInitialized) {
                instanceDb = Room
                    .databaseBuilder(
                        context,
                        TripDb::class.java,
                        "bd_trip"
                    ).allowMainThreadQueries().build()

            }
            return instanceDb

        }
    }

}