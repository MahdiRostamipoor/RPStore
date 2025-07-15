package com.mahdi.rostamipour.rpstore.service.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mahdi.rostamipour.rpstore.model.FavoriteModel
import com.mahdi.rostamipour.rpstore.service.database.dao.FavoriteDao

@Database(entities = [FavoriteModel::class] , version = 2)
abstract class DBRoom : RoomDatabase() {

    abstract fun favoriteDao() : FavoriteDao
}