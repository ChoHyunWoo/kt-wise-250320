package com.think.global

import java.nio.file.Path

object AppConfig {

    private var mode: String = "dev"

    fun setTestMode() {
        mode = "test"
    }

    fun setDevMode() {
        mode = "dev"
    }

    val tableDirPath: Path
        get() = Path.of("data/${mode}")

}