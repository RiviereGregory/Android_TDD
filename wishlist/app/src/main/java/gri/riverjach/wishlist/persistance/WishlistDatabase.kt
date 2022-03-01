package gri.riverjach.wishlist.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import gri.riverjach.wishlist.Wishlist

@Database(entities = [Wishlist::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class WishlistDatabase : RoomDatabase() {
    abstract fun wishlistDao(): WishlistDao
}
