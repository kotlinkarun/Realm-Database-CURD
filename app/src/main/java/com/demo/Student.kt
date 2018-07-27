package com.demo

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Student : RealmObject() {

    var roll_no: Int = 0
    var name: String = ""
    var mobile: String = ""
    var address: String = ""

}