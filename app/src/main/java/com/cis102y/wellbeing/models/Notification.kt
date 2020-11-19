package com.cis102y.wellbeing.models

import android.net.Uri
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Notification(
    var imgUri: Uri? = null,
    var text: String = "",
    @ServerTimestamp
    var notifiedTimestamp: Date? = null
)
