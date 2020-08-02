package tcc.bbshust.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_account_table")
data class Account(
    @PrimaryKey(autoGenerate = true)
    var accountId: Long = 0,

    @ColumnInfo(name = "email")
    var email: String = "",

    @ColumnInfo(name = "password")
    var password: String = "",

    @ColumnInfo(name = "user_id")
    var userId: String = ""
)
