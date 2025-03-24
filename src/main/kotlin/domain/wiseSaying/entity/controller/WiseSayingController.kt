package com.think.domain.wiseSaying.controller

import com.think.domain.wiseSaying.entity.global.Request
import com.think.global.SingletonScope

class WiseSayingController{
    private val wiseSayingService = SingletonScope.wiseSayingService

    fun write() {
        print("명언: ")
        val saying = readlnOrNull() ?: ""
        print("작가: ")
        val author = readlnOrNull() ?: ""

        val wiseSaying = wiseSayingService.write(saying, author)
        println("${wiseSaying.id}번 명언이 등록되었습니다.")
    }

    fun list() {
        println("번호 / 작가 / 명언")
        println("----------------------")
        wiseSayingService.getItems().forEach {
            println("${it.id} / ${it.author} / ${it.saying}")
        }
    }

    fun delete(rq: Request) {

        val id = rq.getParam("id")?.toIntOrNull()

        if (id == null) {
            println("삭제할 명언의 번호를 입력해주세요.")
            return
        }

        val wiseSaying = wiseSayingService.getItem(id)

        wiseSaying?.let {
            wiseSayingService.delete(it)
            println("${id}번 명언을 삭제했습니다.")
        } ?: println("${id}번 명언은 존재하지 않습니다.")

    }

    fun modify(rq: Request) {

        val id = rq.getParam("id")?.toIntOrNull()

        if (id == null) {
            println("수정할 명언의 번호를 입력해주세요.")
            return
        }

        val wiseSaying = wiseSayingService.getItem(id)

        wiseSaying?.let {
            println("명언(기존) : ${wiseSaying.saying}")
            print("명언: ")
            val saying = readlnOrNull() ?: ""

            println("작가(기존) : ${wiseSaying.author}")
            print("작가: ")
            val author = readlnOrNull() ?: ""

            wiseSayingService.modify(wiseSaying, saying, author)
            println("${id}번 명언을 수정했습니다.")
        } ?: println("${id}번 명언은 존재하지 않습니다.")

    }

    fun build() {
        wiseSayingService.build()
        println("data.json 파일의 내용이 갱신되었습니다.")
    }
}