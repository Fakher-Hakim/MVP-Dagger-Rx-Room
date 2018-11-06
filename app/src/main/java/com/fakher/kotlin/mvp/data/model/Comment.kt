package com.fakher.kotlin.mvp.data.model

import android.arch.persistence.room.*

@Entity(tableName = "COMMENT",
        foreignKeys =
        [ForeignKey(entity = Post::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("postId"))])
data class Comment(@PrimaryKey var postId: Int?, var id: Int, var name: String?, var email: String?, var body: String?)

@Dao
interface CommentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comment: Comment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comments: List<Comment>)

    @Update
    fun update(comment: Comment)

    @Delete
    fun delete(comment: Comment)

    @Query("SELECT * from COMMENT WHERE postId LIKE :postId ORDER BY id ASC")
    fun getCommentsForPost(postId: Int): List<Comment>

    @Query("SELECT * from COMMENT WHERE id LIKE :id")
    fun getById(id: Int): Comment

    @Query("DELETE FROM COMMENT")
    fun deleteAll()
}