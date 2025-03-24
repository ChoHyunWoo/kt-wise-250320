import com.think.global.SingletonScope
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AppTest {

    @BeforeEach
    fun setUp() {
        SingletonScope.wiseSayingRepository.clear()
        SingletonScope.wiseSayingRepository.initTable()
    }

    @Test
    fun `명언 등록`() {
        val out = TestBot.run(
            """
                등록
                인생은 짧고, 예술은 길다.
                헨리 장
            """
        )

        println(out)

        assertThat(out).contains("명언:")
        assertThat(out).contains("작가:")
        assertThat(out).contains("1번 명언이 등록되었습니다.")
    }

    @Test
    fun `명언 목록`() {
        val result = TestBot.run(
            """
            등록
            나의 죽음을 적들에게 알리지 말라.
            충무공 이순신
            등록
            천재는 99%의 노력과 1%의 영감이다.
            에디슨
            목록
        """
        )

        println(result)

        assertThat(result).contains("1 / 충무공 이순신 / 나의 죽음을 적들에게 알리지 말라.")
        assertThat(result).contains("2 / 에디슨 / 천재는 99%의 노력과 1%의 영감이다.")
    }


    @Test
    fun `명언 삭제`() {
        val result = TestBot.run(
            """
            등록
            나의 죽음을 적들에게 알리지 말라.
            충무공 이순신
            등록
            천재는 99%의 노력과 1%의 영감이다.
            에디슨
            삭제?id=1
            목록
        """
        )

        println(result)

        assertThat(result).contains("1번 명언을 삭제했습니다.")
        assertThat(result).doesNotContain("1 / 충무공 이순신 / 나의 죽음을 적들에게 알리지 말라.")
        assertThat(result).contains("2 / 에디슨 / 천재는 99%의 노력과 1%의 영감이다.")
    }

    @Test
    fun `명언 수정`() {
        val result = TestBot.run(
            """
            등록
            나의 죽음을 적들에게 알리지 말라.
            충무공 이순신
            수정?id=1
            나의 죽음을 적들에게 알리지 말라. 그리고 적들에게 나의 삶을 알리라.
            이순신 장군
            목록
        """
        )

        assertThat(result).contains("1번 명언을 수정했습니다.")
        assertThat(result).doesNotContain("1 / 충무공 이순신 / 나의 죽음을 적들에게 알리지 말라.")
        assertThat(result).contains("1 / 이순신 장군 / 나의 죽음을 적들에게 알리지 말라. 그리고 적들에게 나의 삶을 알리라.")
    }

    @Test
    fun `빌드`() {
        val result = TestBot.run(
            """
             등록
             나의 죽음을 적들에게 알리지 말라.
             충무공 이순신
             등록
             천재는 99%의 노력과 1%의 영감이다.
             에디슨
             빌드
         """
        )

        assertThat(result)
            .contains("data.json 파일의 내용이 갱신되었습니다.");
    }


    @Test
    fun `목록(검색)`() {
        val result = TestBot.run(
            """
             등록
             현재를 사랑하라.
             작자미상
             등록
             과거에 집착하지 마라.
             작자미상
             목록?keywordType=content&keyword=과거
         """
        )

        assertThat(result)
            .contains("----------------------")
            .contains("검색타입 : content")
            .contains("검색어 : 과거")

        assertThat(result)
            .doesNotContain("1 / 작자미상 / 현재를 사랑하라.")
            .contains("2 / 작자미상 / 과거에 집착하지 마라.")
    }


}