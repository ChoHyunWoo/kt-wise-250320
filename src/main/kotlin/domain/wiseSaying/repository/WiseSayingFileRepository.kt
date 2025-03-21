package com.think.domain.wiseSaying.repository

import com.think.domain.wiseSaying.entity.WiseSaying
import java.nio.file.Path

class WiseSayingFileRepository : WiseSayingRepository {

    private var lastId: Int = 0

    override fun save(wiseSaying: WiseSaying): WiseSaying {
        if (wiseSaying.isNew()) {
            val new = wiseSaying.copy(id = ++lastId)
            saveOnDisk(new)
            return new
        }

        saveOnDisk(wiseSaying)
        return wiseSaying
    }

    fun saveOnDisk(wiseSaying: WiseSaying) {
        Path.of("data/dev/wiseSaying").toFile().writeText(wiseSaying.jsonStr)
    }

    override fun findAll(): List<WiseSaying> {
        return listOf()
    }

    override fun findById(id: Int): WiseSaying? {
        return null
    }

    override fun delete(wiseSaying: WiseSaying) {

    }

    override fun clear() {

    }
}