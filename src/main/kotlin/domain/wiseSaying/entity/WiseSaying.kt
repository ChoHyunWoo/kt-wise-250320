package com.think.domain.wiseSaying.entity

import com.think.standard.JsonUtil

data class WiseSaying(
    val id: Int = 0,
    val saying: String,
    val author: String
) {
    fun isNew(): Boolean {
        return id == 0
    }

    val jsonStr: String
        get() = """
            {
                "id": $id,
                "saying": "$saying",
                "author": "$author"
            }
        """.trimIndent()

    val map: Map<String, Any>
        get() = mapOf(
            "id" to id,
            "saying" to saying,
            "author" to author
        )

    companion object {

        fun fromJson(jsonStr: String): WiseSaying {
            val jsonMap = JsonUtil.jsonStrToMap(jsonStr)

            val id = jsonMap["id"] as Int
            val saying = jsonMap["saying"] as String
            val author = jsonMap["author"] as String

            return WiseSaying(id, saying, author)
        }
    }
}