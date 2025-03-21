package com.think

import com.think.domain.wiseSaying.entity.WiseSaying
import com.think.domain.wiseSaying.entity.global.Request


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    val wiseSayings = mutableListOf<WiseSaying>()

    var lastId = 0

    println("== 명언 앱 ==")
    while (true) {
        print("명령) ")
        val input = readlnOrNull() ?: ""

        val rq = Request(input)

        when (rq.actionName) {
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

            "삭제" -> {
                rq.getParam("id")?.let {
                    it.toIntOrNull()?.let {
                        wiseSayings.removeIf { saying -> saying.id == it }
                        println("${it}번 명언을 삭제했습니다.")
                    } ?: println("올바른 번호를 입력해주세요.")
                } ?: println("삭제할 명언의 번호를 입력해주세요.")
            }

            else -> {
                println("알 수 없는 명령입니다.")
            }
        }
    }
}