package com.cis102y.wellbeing.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Notification(
    var photoUrl: String = "",
    var text: String = "",
    @ServerTimestamp
    var notifiedTimestamp: Date? = null
)
