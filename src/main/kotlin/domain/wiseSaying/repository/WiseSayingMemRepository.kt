package com.think.domain.wiseSaying.repository

import com.think.domain.wiseSaying.entity.WiseSaying
import com.think.standard.Page

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

    override fun findByAuthorLike(keyword: String): List<WiseSaying> {
        TODO("Not yet implemented")
    }

    override fun findBySayingLike(keyword: String): List<WiseSaying> {
        TODO("Not yet implemented")
    }

    override fun findByAuthorLikePaged(keyword: String, page: Int, pageSize: Int): Page {
        TODO("Not yet implemented")
    }

    override fun findBySayingLikePaged(keyword: String, page: Int, pageSize: Int): Page {
        TODO("Not yet implemented")
    }
}