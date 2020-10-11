package com.cis102y.wellbeing.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue

class Post {
    lateinit var authorName : String
     lateinit var text : String
     lateinit var createdTimestamp : FieldValue
     var likeCount = 0
     var commentCount = 0

    fun Post() {

    }
    fun Post(
        authorName: String,
        text: String,
        timestamp: Timestamp,
        likeCount: Int,
        commentCount: Int
    ) {
        this.createdTimestamp = FieldValue.serverTimestamp()
        this.authorName = authorName
        this.text = text

    }

    fun toMap(): Map<String, Any> {
        return hashMapOf(
            "authorName" to authorName,
            "text" to text,
            "createdTimestamp" to createdTimestamp,
            "likeCount" to likeCount,
            "commentCount" to commentCount
        )
    }

}
