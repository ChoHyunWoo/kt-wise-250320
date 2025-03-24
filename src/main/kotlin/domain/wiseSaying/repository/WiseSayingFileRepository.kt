package com.think.domain.wiseSaying.repository

import com.think.domain.wiseSaying.entity.WiseSaying
import com.think.global.AppConfig
import com.think.standard.JsonUtil
import com.think.standard.Page
import java.nio.file.Path

class WiseSayingFileRepository : WiseSayingRepository {

    init {
        initTable()
    }

    val tableDirPath: Path
        get() = AppConfig.tableDirPath.resolve("wiseSaying")

    override fun save(wiseSaying: WiseSaying): WiseSaying {

        val target = if (wiseSaying.isNew()) wiseSaying.copy(id = getNextId()) else wiseSaying

        return target.also {
            saveOnDisk(it)
        }
    }

    private fun saveOnDisk(wiseSaying: WiseSaying) {
        tableDirPath.resolve("${wiseSaying.id}.json").toFile().writeText(wiseSaying.jsonStr)
    }

    override fun findAll(): List<WiseSaying> {
        return tableDirPath.toFile()
            .listFiles()
            ?.filter { it.extension == "json" }
            ?.map { WiseSaying.fromJson(it.readText()) }
            ?.sortedByDescending { it.id }
            .orEmpty()
    }

    override fun findById(id: Int): WiseSaying? {
        return tableDirPath.resolve("${id}.json").toFile()
            .takeIf { it.exists() }
            ?.let {
                WiseSaying.fromJson(it.readText())
            }
    }

    override fun delete(wiseSaying: WiseSaying) {
        tableDirPath.resolve("${wiseSaying.id}.json").toFile().delete()
    }

    override fun clear() {
        tableDirPath.toFile().deleteRecursively()
    }

    override fun findByAuthorLike(keyword: String): List<WiseSaying> {

        if (keyword.isBlank()) {
            return findAll()
        }

        return findAll()
            .filter { it.author.contains(keyword) }
    }

    override fun findBySayingLike(keyword: String): List<WiseSaying> {
        if (keyword.isBlank()) {
            return findAll()
        }

        return findAll()
            .filter { it.saying.contains(keyword) }
    }

    fun findAllPaged(page: Int, pageSize: Int): List<WiseSaying> {
        return findAll()
            .drop((page - 1) * pageSize)
            .take(pageSize)
    }

    override fun findByAuthorLikePaged(keyword: String, page: Int, pageSize: Int): Page {

        var totalCount = findAll().size

        if (keyword.isBlank()) {
            return findAllPaged(page, pageSize).let {
                Page(it, totalCount, page, pageSize)
            }
        }

        val searchedWiseSayings = findAll()
            .filter { it.author.contains(keyword) }

        totalCount = searchedWiseSayings.size

        val content = searchedWiseSayings
            .drop((page - 1) * pageSize)
            .take(pageSize)

        return Page(content,totalCount, page, pageSize)
    }

    override fun findBySayingLikePaged(keyword: String, page: Int, pageSize: Int): Page {
        var totalCount = findAll().size

        if (keyword.isBlank()) {
            return findAllPaged(page, pageSize).let {
                Page(it, totalCount, page, pageSize)
            }
        }

        val searchedWiseSayings = findAll()
            .filter { it.saying.contains(keyword) }

        totalCount = searchedWiseSayings.size

        val content = searchedWiseSayings
            .drop((page - 1) * pageSize)
            .take(pageSize)

        return Page(content,totalCount, page, pageSize)
    }

    fun saveLastId(id: Int) {
        tableDirPath.resolve("lastId.txt").toFile().writeText(id.toString())
    }

    fun loadLastId(): Int {
        tableDirPath.resolve("lastId.txt").toFile().run {
            if (!exists()) {
                return 1
            }
            return readText().toInt()
        }
    }

    private fun getNextId(): Int {
        return loadLastId().also {
            saveLastId(it + 1)
        }
    }

    fun initTable() {
        tableDirPath.toFile().run {
            if (!exists()) {
                mkdirs()
            }
        }
    }

    fun build() {
        val mapList = findAll().map {
            it.map
        }

        val rst = JsonUtil.listToJson(mapList).also {
            tableDirPath.resolve("data.json").toFile().writeText(it)
        }

        println(rst)

    }
}