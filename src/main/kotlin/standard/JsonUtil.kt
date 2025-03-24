package com.think.standard

object JsonUtil {

    fun listToJson(list: List<Map<String, Any>>): String {
        return list.joinToString(
            prefix = "[\n", postfix = "\n]", separator = ",\n"
        ) {
            mapToJson(it)
        }
    }

    fun mapToJson(map: Map<String, Any>): String {
        return map.entries.joinToString(
            prefix = "{\n", postfix = "\n}", separator = ",\n"
        ) { (key, value) ->
            {
                val formattedKey = "\"$key\""
                val formattedValue = when (value) {
                    is String -> "\"$value\""
                    else -> value
                }

                "    $formattedKey: $formattedValue"
            }.toString()
        }
    }



    fun jsonStrToMap(jsonStr: String): Map<String, Any> {

        val replacedJsonStr = jsonStr.replace("{", "")
            .replace("}", "")
            .replace("\n", "")
            .replace(":", ":")


        val result = replacedJsonStr.split(",").associate { pair ->
            val bits = pair.split(":", limit = 2)

            val key = bits[0].trim().replace("\"", "")
            val value = if (bits[1].trim().startsWith("\"")) {
                bits[1].trim().removeSurrounding("\"")
            } else {
                bits[1].trim().toInt()
            }


            key to value
        }

        println(result)
        return result
    }
}