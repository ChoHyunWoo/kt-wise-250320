package com.think.domain.wiseSaying.entity.global

class Request(
    val input: String,

    ) {

    var actionName: String = ""
    val paramMap: Map<String, String>  //불변 맵

    init {
        val inputBits = input.split("?", limit = 2)
        actionName = inputBits[0]
//key1=value1 key2=value2
      paramMap = if (inputBits.size == 2) {
            inputBits[1].split("&").associate {
                val bits = it.split("=", limit = 2)
                bits[0] to bits[1]
            }
                .toMap()
        }else{
            emptyMap()   //비어있는 맵
      }

//        if (inputBits.size == 2) {
//            val params = inputBits[1].split("&")
//
//            params.forEach { param ->
//                val paramBits = param.split("=", limit = 2)
//
//                if (paramBits.size == 2) {
//                    paramMap[paramBits[0]] = paramBits[1]
//                }
//            }
//        }
    }

    fun getParam(key: String): String? {
        return paramMap[key]
    }
}