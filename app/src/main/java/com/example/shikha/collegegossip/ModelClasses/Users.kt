package com.example.shikha.collegegossip.ModelClasses

class Users {
    private var uid: String = ""
    private var username: String = ""
    private var profile: String = ""
    private var cover: String =""
    private var status: String = ""
    private var search: String = ""
    private var facebook: String = ""
    private var instagram: String = ""
    private var website: String = ""
    private var bio: String = ""
    private var dob: String = ""
    private var year: String = ""
    private var fullname: String = ""
    private var hometown: String = ""

    constructor()
    constructor(
            uid: String,
            username: String,
            profile: String,
            cover: String,
            status: String,
            search: String,
            facebook: String,
            instagram: String,
            website: String,
            bio: String,
            dob: String,
            year: String,
            fullname: String,
            hometown: String) {
        this.uid = uid
        this.username = username
        this.profile = profile
        this.cover = cover
        this.status = status
        this.search = search
        this.facebook = facebook
        this.instagram = instagram
        this.website = website
        this.bio = bio
        this.dob = dob
        this.year = year
        this.fullname = fullname
        this.hometown = hometown
    }

    fun getUID(): String?{
        return uid
    }

    fun setUID(uid: String){
        this.uid = uid
    }

    fun getUSERNAME(): String?{
        return username
    }

    fun setUSERNAME(username: String){
        this.username = username
    }

    fun getPROFILE(): String?{
        return profile
    }

    fun setPROFILE(profile: String){
        this.profile = profile
    }

    fun getCOVER(): String?{
        return cover
    }

    fun setCOVER(cover: String){
        this.cover = cover
    }

    fun getSTATUS(): String?{
        return status
    }

    fun setSTATUS(status: String){
        this.status = status
    }

    fun getSEARCH(): String?{
        return search
    }

    fun setSEARCH(search: String){
        this.search = search
    }

    fun getFACEBOOK(): String?{
        return facebook
    }

    fun setFACEBOOK(facebook: String){
        this.facebook = facebook
    }

    fun getINSTAGRAM(): String?{
        return instagram
    }

    fun setINSTAGRAM(instagram: String){
        this.instagram = instagram
    }

    fun getWEBSITE(): String?{
        return website
    }

    fun setWEBSITE(website: String){
        this.website = website
    }

    fun getBIO(): String?{
        return bio
    }

    fun setBIO(bio: String){
        this.bio = bio
    }

    fun getDOB(): String?{
        return dob
    }

    fun setDOB(dob: String){
        this.dob = dob
    }

    fun getFULLNAME(): String?{
        return fullname
    }

    fun setFULLNAME(fullname: String){
        this.fullname = fullname
    }

    fun getHOMETOWN(): String?{
        return hometown
    }

    fun setHOMETOWN(hometown: String){
        this.hometown = hometown
    }

    fun getYEAR(): String?{
        return year
    }

    fun setYEAR(year: String){
        this.year = year
    }




}