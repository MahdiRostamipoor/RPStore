package com.mahdi.rostamipour.rpstore

import android.app.Application
import androidx.room.Room
import com.mahdi.rostamipour.rpstore.model.repository.CategoryRepository
import com.mahdi.rostamipour.rpstore.model.repository.CommentsRepository
import com.mahdi.rostamipour.rpstore.model.repository.FavoriteRepository
import com.mahdi.rostamipour.rpstore.model.repository.FilterRepository
import com.mahdi.rostamipour.rpstore.model.repository.ProductRepository
import com.mahdi.rostamipour.rpstore.model.repository.ProfileRepository
import com.mahdi.rostamipour.rpstore.service.ApiService
import com.mahdi.rostamipour.rpstore.service.database.DBRoom
import com.mahdi.rostamipour.rpstore.viewModel.CategoryViewModel
import com.mahdi.rostamipour.rpstore.viewModel.CommentsViewModel
import com.mahdi.rostamipour.rpstore.viewModel.FavoriteViewModel
import com.mahdi.rostamipour.rpstore.viewModel.FilterViewModel
import com.mahdi.rostamipour.rpstore.viewModel.ProductViewModel
import com.mahdi.rostamipour.rpstore.viewModel.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module
import kotlin.jvm.java

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val appModule = module {

            single {
                Room.databaseBuilder(
                    androidContext(),
                    DBRoom::class.java, "RPStoreDB"
                ).build()
            }

            single { ApiService() }

            single { get<DBRoom>().favoriteDao() }

            single { CategoryRepository(get()) }
            single { ProductRepository(get()) }
            single { FilterRepository(get()) }
            single { CommentsRepository(get()) }
            single { ProfileRepository(get()) }
            single { FavoriteRepository(get()) }


            viewModel { CategoryViewModel(get()) }
            viewModel { ProductViewModel(get()) }
            viewModel { FilterViewModel(get()) }
            viewModel { CommentsViewModel(get()) }
            viewModel { ProfileViewModel(get()) }
            viewModel { FavoriteViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(appModule)
        }

    }

}