import com.think.App
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class TestBot {

    companion object {

        val originalIn = System.`in` // 표준 입력 - 키보드
        val originalOut = System.out // 표준 출력 - 모니터

        fun run(input: String): String {

            val formattedInput = input
                .trimIndent()
                .plus("\n종료")

            ByteArrayOutputStream().use { out ->
                PrintStream(out).use {
                    try {
                        System.setOut(it)
                        System.setIn(formattedInput.byteInputStream()) // 커스텀 입력 - 매개변수 문자열

                        val app = App() // 앱 테스트
                        app.run()

                    } finally {
                        System.setIn(originalIn) // 표준 입력으로 원복
                        System.setOut(originalOut) // 표준 출력으로 원복
                    }
                }
                // 커스텀 출력 - 배열
                return out.toString().trim().replace("\\r\\n", "\\n")
            }
        }
    }
}