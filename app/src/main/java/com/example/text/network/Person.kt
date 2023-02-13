package com.example.text.network

//根据api结构第一级的模型类
class Person {
    val code : Int
    val msg : String
    val data :ArrayList<String>

    constructor(code: Int, msg: String, data: ArrayList<String>) {
        this.code = code
        this.msg = msg
        this.data = data
    }
}