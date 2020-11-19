package com.cis102y.wellbeing.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Appointment(
    @ServerTimestamp
    var scheduledTimestamp: Date? = null,
    var address: String = "",
    var doctorCategory: String = ""
)
