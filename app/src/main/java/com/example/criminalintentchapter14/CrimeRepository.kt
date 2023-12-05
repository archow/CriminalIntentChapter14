package com.example.criminalintentchapter14

import android.content.Context
import androidx.room.Room
import com.example.criminalintentchapter14.database.CrimeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID

private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
){
    private val crimeDatabase: CrimeDatabase = Room
        .databaseBuilder(context, CrimeDatabase::class.java, DATABASE_NAME)
        .createFromAsset(DATABASE_NAME)
        .build()

    fun getCrimes(): Flow<List<Crime>> = crimeDatabase.crimeDao().getCrimes()

    suspend fun getCrime(id: UUID): Crime = crimeDatabase.crimeDao().getCrime(id)

    fun updateCrime(crime: Crime) {
        coroutineScope.launch {
            crimeDatabase.crimeDao().updateCrime(crime)
        }
    }

    companion object {
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository = INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initialized")
    }
}