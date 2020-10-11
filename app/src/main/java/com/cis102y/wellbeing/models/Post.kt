package com.cis102y.wellbeing.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue

class Post {
    private lateinit var authorName : String
    private lateinit var text : String
    private lateinit var createdTimestamp : FieldValue
    private var likeCount = 0
    private var commentCount = 0

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
