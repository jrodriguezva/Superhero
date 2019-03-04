package com.jrodriguezva.superhero.data.local

/**
 * Florina Muntenescu
 * https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#dd40
 */

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface BaseDao<T> {

    /**
     * Insert an object in the database.
     *
     * @param obj the object to be inserted.
     */
    @Insert
    fun insert(obj: T)

    /**
     * Insert an array of objects in the database.
     *
     * @param obj the objects to be inserted.
     */
    @Insert
    fun insert(vararg obj: T)

    /**
     * Insert an list of objects in the database.
     *
     * @param obj the objects to be inserted.
     */
    @Insert
    fun insert(obj: List<T>)

    /**
     * Update an object from the database.
     *
     * @param obj the object to be updated
     */
    @Update
    fun update(obj: T)

    /**
     * Delete an object from the database
     *
     * @param obj the object to be deleted
     */
    @Delete
    fun delete(obj: T)

    /**
     * Clear table
     */
    @Query("DELETE FROM SUPERHEROES")
    fun clear()
}