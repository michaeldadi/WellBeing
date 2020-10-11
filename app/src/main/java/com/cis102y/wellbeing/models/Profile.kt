package com.cis102y.wellbeing.models

class Profile {
    private lateinit var id:String
    private lateinit var firstName:String
    private lateinit var lastName:String
    private lateinit var email:String
    private lateinit var photoUrl:String

    fun Profile() {
        //Required default constructor
    }
    fun Profile(id:String) {
        this.id = id
    }

    fun getId():String {
        return id
    }
    fun setId(id: String) {
        this.id = id
    }

    fun getFirstName():String {
        return firstName
    }
    fun setFirstName(firstName:String) {
        this.firstName = firstName
    }

    fun getLastName():String {
        return lastName
    }
    fun setLastName(lastName: String) {
        this.lastName = lastName
    }

    fun getEmail():String {
        return email
    }
    fun setEmail(email:String) {
        this.email = email
    }

    fun getPhotoUrl():String {
        return photoUrl
    }
    fun setPhotoUrl(photoUrl:String) {
        this.photoUrl = photoUrl
    }
}
