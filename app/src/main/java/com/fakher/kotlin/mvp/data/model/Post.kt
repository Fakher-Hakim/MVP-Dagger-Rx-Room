package com.fakher.kotlin.mvp.data.model

import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable


@Entity(tableName = "POST")
data class Post(@PrimaryKey var id: Int, var title: String?, var body: String?) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id)
        dest?.writeString(title)
        dest?.writeString(body)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}

@Dao
interface PostDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Post): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<Post>)

    @Query("SELECT * from POST ORDER BY id ASC")
    fun getAll(): List<Post>

    @Query("SELECT * from POST WHERE id LIKE :id")
    fun getById(id: Int): Post
}