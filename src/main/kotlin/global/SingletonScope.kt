package com.think.global

import com.think.domain.system.controller.SystemController
import com.think.domain.wiseSaying.controller.WiseSayingController
import com.think.domain.wiseSaying.repository.WiseSayingMemRepository
import com.think.domain.wiseSaying.service.WiseSayingService

object SingletonScope {
    val wiseSayingRepository by lazy {WiseSayingMemRepository()}
    val wiseSayingService by lazy {WiseSayingService()}
    val wiseSayingController by lazy {WiseSayingController()}
    val systemController by lazy {SystemController()}
}