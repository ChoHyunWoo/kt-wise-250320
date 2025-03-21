package com.think.domain.wiseSaying.repository

import com.think.domain.wiseSaying.entity.WiseSaying

class WiseSayingMemRepository : WiseSayingRepository {
    private var lastId: Int = 0
    private val wiseSayings = mutableListOf<WiseSaying>()

    override fun save(wiseSaying: WiseSaying): WiseSaying {

        if (wiseSaying.isNew()) {
            val new = wiseSaying.copy(id = ++lastId)
            wiseSayings.add(new)
            return new
        }

        wiseSayings.indexOfFirst { it.id == wiseSaying.id }.let {
            wiseSayings[it] = wiseSaying
        }

        return wiseSaying
    }

    override fun findAll(): List<WiseSaying> {
        return wiseSayings.toList()
    }

    override fun delete(wiseSaying: WiseSaying) {
        wiseSayings.remove(wiseSaying)
    }

    override fun findById(id: Int): WiseSaying? {
        return wiseSayings.find { it.id == id }
    }

    override fun clear() {
        wiseSayings.clear()
        lastId = 0
    }
}