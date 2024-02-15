package com.climus.climeet.presentation.ui.main.shorts.model

data class ShortsCommentUiData(
    val commentId: Long = -1L,
    val nickName: String = "",
    val profileImageUrl: String = "",
    val content: String = "",
    val commentLikeStatus: LikeStatus = LikeStatus.NONE,
    val likeCount: Int = 0,
    val dislikeCount: Int = 0,
    val type: Int = 0,
    val parentCommentId: Long = -1,
    val childCommentCount: Int = 0,
    val createDate: String = "",
    val changeLikeStatus: (Long) -> Unit,
    val showMoreComment: (Long) -> Unit,
    val addSubComment: (Long) -> Unit
)

enum class LikeStatus {
    LIKE,
    DISLIKE,
    NONE
}
