package com.mahdi.rostamipour.rpstore

import android.app.Application
import com.mahdi.rostamipour.rpstore.model.repository.CategoryRepository
import com.mahdi.rostamipour.rpstore.model.repository.FilterRepository
import com.mahdi.rostamipour.rpstore.model.repository.ProductRepository
import com.mahdi.rostamipour.rpstore.service.ApiService
import com.mahdi.rostamipour.rpstore.viewModel.CategoryViewModel
import com.mahdi.rostamipour.rpstore.viewModel.FilterViewModel
import com.mahdi.rostamipour.rpstore.viewModel.ProductViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val appModule = module {

            single { ApiService() }


            single { CategoryRepository(get()) }
            single { ProductRepository(get()) }
            single { FilterRepository(get()) }


            viewModel{ CategoryViewModel(get()) }
            viewModel{ ProductViewModel(get()) }
            viewModel{ FilterViewModel(get()) }

        }

        startKoin {
            androidContext(this@App)
            modules(appModule)
        }

    }

}