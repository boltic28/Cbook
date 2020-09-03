package com.boltic28.cbook

import java.io.Serializable

class Contact(): Serializable {
    constructor(
        id: Long,
        name: String,
        number: String,
        mail: String,
        remark: String,
        photo: String
    ): this(){
        this.id = id
        this.name = name
        this.number = number
        this.mail = mail
        this.remark = remark
        this.photo = photo
    }

    var id: Long = 0
    var name: String = "contact"
    var number: String = "+375 44 1234567"
    var mail: String = "contact@mail.com"
    var remark: String = "nothing"
    var photo: String = ""
    var isSelected = false
}