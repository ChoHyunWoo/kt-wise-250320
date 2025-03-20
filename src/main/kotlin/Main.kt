package com.think.domain.wiseSaying.entity


fun main() {

    val wiseSayings = mutableListOf<WiseSaying>()

    var lastId = 0

    println("== 명언 앱 ==")
    while (true) {
        print("명령) ")
        val input = readlnOrNull() ?: ""

        when (input) {
            "종료" -> break
            "등록" -> {
                print("명언: ")
                val saying = readlnOrNull() ?: ""
                print("작가: ")
                val author = readlnOrNull() ?: ""
                val id = ++lastId
                wiseSayings.add(WiseSaying(id, saying, author))

                println("${lastId}번 명언이 등록되었습니다.")
            }
            "목록" -> {
                println("번호 / 작가 / 명언")
                println("----------------------")
                wiseSayings.forEach {
                    println("${it.id} / ${it.author} / ${it.saying}")
                }
            }
        }
    }
}