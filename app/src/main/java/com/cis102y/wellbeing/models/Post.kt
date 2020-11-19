package com.cis102y.wellbeing.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Post(
    var imgUri: String = "  ",
    var authorName: String = "",
    var text: String = "",
    @ServerTimestamp
    var createdTimestamp: Date? = null,
    var likeCount: Int = 0,
    var commentCount: Int = 0
)
