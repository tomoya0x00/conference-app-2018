package io.github.droidkaigi.confsched2018

import io.github.droidkaigi.confsched2018.data.db.entity.LevelEntity
import io.github.droidkaigi.confsched2018.data.db.entity.RoomEntity
import io.github.droidkaigi.confsched2018.data.db.entity.SessionEntity
import io.github.droidkaigi.confsched2018.data.db.entity.SessionWithSpeakers
import io.github.droidkaigi.confsched2018.data.db.entity.SpeakerEntity
import io.github.droidkaigi.confsched2018.data.db.entity.TopicEntity
import io.github.droidkaigi.confsched2018.model.Level
import io.github.droidkaigi.confsched2018.model.Level.Companion.BEGINNER
import io.github.droidkaigi.confsched2018.model.Level.Companion.NICHE
import io.github.droidkaigi.confsched2018.model.Room
import io.github.droidkaigi.confsched2018.model.Session
import io.github.droidkaigi.confsched2018.model.Speaker
import io.github.droidkaigi.confsched2018.model.Topic
import io.github.droidkaigi.confsched2018.util.ext.atJST
import org.threeten.bp.LocalDateTime
import java.util.Date

const val DUMMY_SESSION_ID1 = "111111"
const val DUMMY_SESSION_ID2 = "222222"
const val DUMMY_SESSION_TITLE1 = "DroidKaigi"
const val DUMMY_SESSION_TITLE2 = "RejectKaigi"

fun createDummySession(sessionId: String = DUMMY_SESSION_ID1,
                       title: String = DUMMY_SESSION_TITLE1,
                       dayNumber: Int = 1,
                       startTime: Long = 10000,
                       endTime: Long = 10000): Session.SpeechSession {
    return Session.SpeechSession(
        id = sessionId,
        dayNumber = dayNumber,
        startTime = Date(startTime),
        endTime = Date(endTime),
        title = title,
        desc = "How to create DroidKaigi app",
        room = Room(1, "Hall"),
        format = "30分",
        language = "JA",
        topic = Topic(2, "Development tool"),
        level = Level.of(BEGINNER, "Beginner"),
        isFavorited = true, speakers = listOf(
        createDummySpeaker(),
        createDummySpeaker()
    ),
        message = null
    )
}

fun createDummySpecialSession(dayNumber: Int = 1,
                              startTime: Long = 10000,
                              endTime: Long = 10000): Session.SpecialSession {
    return Session.SpecialSession(
        id = DUMMY_SESSION_ID1,
        dayNumber = dayNumber,
        startTime = Date(startTime),
        endTime = Date(endTime),
        title = 0,
        room = Room(1, "Hall")
    )
}

fun createDummySpeaker(): Speaker {
    return Speaker(
        id = "tmtm",
        name = "tm",
        tagLine = "this is sample",
        imageUrl = "http://example.com",
        twitterUrl = "http://twitter.com/",
        githubUrl = null,
        blogUrl = null, companyUrl = null
    )
}

fun createDummySpeakerEntities(): List<SpeakerEntity> {
    return listOf(
        createDummySpeakerEntry1(),
        createDummySpeakerEntry2()
    )
}

fun createDummySpeakerEntry2(): SpeakerEntity {
    return SpeakerEntity(
        "bbbb", "hogehuga", "this is dummy", "https://example.com", "http://example.com/hoge", null, null, "http://example.github.com/hoge"
    )
}

fun createDummySpeakerEntry1(): SpeakerEntity {
    return SpeakerEntity(
        "aaaa", "hogehoge", "this is sample", "https://example.com", "http://example.com/hoge", null, null, "http://example.github.com/hoge"
    )
}

fun createDummySessionWithSpeakersEntities(): List<SessionWithSpeakers> {
    return listOf(
        SessionWithSpeakers(SessionEntity(DUMMY_SESSION_ID1,
            DUMMY_SESSION_TITLE1,
            "Endless battle",
            LocalDateTime.of(1, 1, 1, 1, 1).atJST().toInstant(),
            LocalDateTime.of(1, 1, 1, 1, 1).atJST().toInstant(),
            "30分",
            "日本語",
            LevelEntity(NICHE, "ニッチ / Niche"),
            TopicEntity(1, "開発環境 / Development"),
            RoomEntity(1, "ホール"),
            null),
            listOf("aaaa", "bbbb")),
        SessionWithSpeakers(SessionEntity(DUMMY_SESSION_ID2,
            DUMMY_SESSION_TITLE2,
            "Endless battle",
            LocalDateTime.of(1, 1, 1, 1, 1).atJST().toInstant(),
            LocalDateTime.of(1, 1, 1, 1, 1).atJST().toInstant(),
            "30分",
            "日本語",
            LevelEntity(NICHE, "ニッチ / Niche"),
            TopicEntity(1, "開発環境 / Development"),
            RoomEntity(1, "ホール"),
            null),
            listOf("aaaa"))
    )
}
