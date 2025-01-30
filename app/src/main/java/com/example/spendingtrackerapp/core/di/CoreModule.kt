package com.example.spendingtrackerapp.core.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.spendingtrackerapp.core.data.local.db.SpendingDatabase
import com.example.spendingtrackerapp.core.data.remote.ImageApi
import com.example.spendingtrackerapp.core.data.repository_impl.CoreRepositoryImpl
import com.example.spendingtrackerapp.core.data.repository_impl.ImageRepositoryImpl
import com.example.spendingtrackerapp.core.data.repository_impl.SpendingRepositoryImpl
import com.example.spendingtrackerapp.core.domain.repository.CoreRepository
import com.example.spendingtrackerapp.core.domain.repository.ImageRepository
import com.example.spendingtrackerapp.core.domain.repository.SpendingRepository
import com.example.spendingtrackerapp.utils.Constant
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.sin

val coreModule = module {
    val migration_1_2 = object :Migration(1,2){
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE spendingentity ADD COLUMN imageUrl TEXT NOT NULL DEFAULT'' ")
        }

    }
    single {
        Room.databaseBuilder(
            androidApplication(),
            SpendingDatabase::class.java,
            "spending_database_db"
        )
            .addMigrations(migration_1_2)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImageApi::class.java)
    }
    single {
        get<SpendingDatabase>().dao
    }
    single {
        androidApplication().getSharedPreferences(
            "spending_share_preferences",Context.MODE_PRIVATE
        )
    }

    single<ImageRepository> { ImageRepositoryImpl(get()) }
    single<CoreRepository> {CoreRepositoryImpl(get())  }
    single<SpendingRepository> {SpendingRepositoryImpl(get())  }
}