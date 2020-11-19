package com.cis102y.wellbeing.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class ChatMessage(
    var messageUid: String = "",
    var messageText: String = "",
    var messageAuthor: String = "",
    @ServerTimestamp
    var messageTimestamp: Date? = null
)
