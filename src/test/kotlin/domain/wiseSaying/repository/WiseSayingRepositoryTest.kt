package domain.wiseSaying.repository

import com.think.domain.wiseSaying.entity.WiseSaying
import com.think.global.SingletonScope
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WiseSayingFileRepositoryTest {

    private val wiseSayingFileRepository = SingletonScope.wiseSayingFileRepository

    @BeforeEach
    fun setUp() {
        wiseSayingFileRepository.clear()
        wiseSayingFileRepository.initTable()
    }

    @Test
    fun `save`() {
        val wiseSaying = wiseSayingFileRepository
            .save(WiseSaying(saying = "인생은 짧고, 예술은 길다.", author = "헨리 장"))

        val filePath = wiseSayingFileRepository
            .tableDirPath
            .toFile()
            .listFiles()
            ?.find { it.name == "${wiseSaying.id}.json" }

        assertThat(filePath).isNotNull
    }

    @Test
    fun `saveLastId, loadLastId`() {
        wiseSayingFileRepository.saveLastId(10)
        assertThat(wiseSayingFileRepository.loadLastId()).isEqualTo(10)
    }

    @Test
    fun `명언 2개 저장`() {
        wiseSayingFileRepository
            .save(WiseSaying(saying = "인생은 짧고, 예술은 길다.", author = "헨리 장"))
        wiseSayingFileRepository
            .save(WiseSaying(saying = "내 죽음을 적에게 알리지 말라", author = "이순신"))

        val lastId = wiseSayingFileRepository.loadLastId()

        assertThat(lastId).isEqualTo(3)
    }

    @Test
    fun `findAlll`() {
     val w1 =   wiseSayingFileRepository
            .save(WiseSaying(saying = "인생은 짧고 예술은 길다.", author = "헨리 장"))
       val w2 =  wiseSayingFileRepository
            .save(WiseSaying(saying = "내 죽음을 적에게 알리지 말라", author = "이순신"))

        val result = wiseSayingFileRepository.findAll()
        val count = result.size


        assertThat(count).isEqualTo(2)
        assertThat(result).containsExactly(w1, w2)

    }


}