package tcc.bbshust.database

import androidx.room.*

@Dao
interface AccountDatabaseDao {
    @Insert
    fun insert(account: Account)

    @Update
    fun update(account: Account)

    @Delete
    fun delete(account: Account)

    @Query("SELECT * FROM user_account_table ORDER BY accountId DESC LIMIT 1")
    fun getAccount(): Account?

    @Query("DELETE from user_account_table")
    fun clear()
}
