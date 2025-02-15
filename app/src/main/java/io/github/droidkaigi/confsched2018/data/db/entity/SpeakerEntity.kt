package io.github.droidkaigi.confsched2018.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "speaker")
class SpeakerEntity(
    @PrimaryKey var id: String,
    @ColumnInfo(name = "speaker_name")
    var name: String,
    @ColumnInfo(name = "speaker_tag_line")
    var tagLine: String,
    @ColumnInfo(name = "speaker_image_url")
    var imageUrl: String,
    @ColumnInfo(name = "speaker_twitter_url")
    var twitterUrl: String?,
    @ColumnInfo(name = "speaker_company_url")
    var companyUrl: String?,
    @ColumnInfo(name = "speaker_blog_url")
    var blogUrl: String?,
    @ColumnInfo(name = "speaker_github_url")
    var githubUrl: String?
)
