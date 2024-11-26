package com.example.damn_jmhg_examenll.room.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.damn_jmhg_examenll.room.entities.User

@Database(entities = [User::class], version =1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    // Definamos un singleton(patron) - tener una sola instancia de la base de datos
    // Para no tener que instanicar - solo usar la base de datos
    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            // Metodo 1 - Asi seria null
            // Metodo 2
            // Metodo 3
            val tempDB = INSTANCE
            if (tempDB != null) {
                return tempDB
            }
            // Solo se tenga un acceso al mismo tiempo
            // Solicitudes concurrentes
            // Solo el metodo 1 tenga la facultad de crear la instancia
            synchronized(this) {
                // Generamos una instancia de la base de datos
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "mascota_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}