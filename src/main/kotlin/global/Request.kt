package com.think.domain.wiseSaying.entity.global

class Request(
    input: String
) {
    var actionName: String = ""
    var pararms = mutableMapOf<String, String>()

    init {

    }
}