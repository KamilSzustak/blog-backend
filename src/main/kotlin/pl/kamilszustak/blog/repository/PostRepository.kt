package pl.kamilszustak.blog.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pl.kamilszustak.blog.model.Post
import java.util.*

@Repository
interface PostRepository : JpaRepository<Post, Int> {

    @Query(
        value = "SELECT * FROM posts WHERE title LIKE %:text% OR content LIKE %:text%",
        nativeQuery = true
    )
    fun findAllContaining(@Param("text") text: String): List<Post>

    @Query(
        value = "SELECT * FROM posts WHERE created_at = :date",
        nativeQuery = true
    )
    fun findAllCreatedAt(@Param("date") date: Date): List<Post>

    @Query(
        value = "SELECT * FROM posts post WHERE (SELECT COUNT(*) FROM comments comment WHERE comment.post_id = post.id) > 0",
        nativeQuery = true
    )
    fun findAllWithComments(): List<Post>

    @Query(
        value = "SELECT * FROM posts post WHERE (SELECT COUNT(*) FROM comments comment WHERE comment.post_id = post.id) = 0",
        nativeQuery = true
    )
    fun findAllWithoutComments(): List<Post>

    @Query(
        value = "SELECT TOP :amount * FROM posts",
        nativeQuery = true
    )
    fun findFirst(@Param("amount") amount: Int): List<Post>

    @Query(
        value = "SELECT * FROM posts WHERE created_at BETWEEN :startDate AND :endDate",
        nativeQuery = true
    )
    fun findAllCreatedBetween(@Param("startDate") startDate: Date, @Param("endDate") endDate: Date): List<Post>

    @Query(
        value = "SELECT COUNT(*) FROM posts",
        nativeQuery = true
    )
    fun countAll(): Int

    @Query(
        value = "SELECT COUNT(*) FROM comments WHERE post_id = :postId",
        nativeQuery = true
    )
    fun countComments(@Param("postId") postId: Long): Int

    @Query(
        value = "SELECT * FROM posts WHERE id IN :ids",
        nativeQuery = true
    )
    fun findAllByIds(@Param("ids") ids: Array<Long>): List<Post>

    @Query(
        value = "SELECT * FROM posts ORDER BY created_at DESC;",
        nativeQuery = true
    )
    fun findAllOrderedByCreatedAtDate(): List<Post>

    @Query(
        value = "SELECT * FROM posts WHERE content IS NOT NULL AND LENGTH(content) > :length",
        nativeQuery = true
    )
    fun findAllLongerThan(@Param("length") length: Int): List<Post>

    @Query(
        value = "SELECT TOP 1 * FROM posts WHERE (SELECT MAX(id) FROM posts) = id",
        nativeQuery = true
    )
    fun findTheNewest(): Post

    @Query(
        value = "SELECT TOP 1 * FROM posts WHERE (SELECT MIN(id) FROM posts) = id",
        nativeQuery = true
    )
    fun findTheOldest(): Post

    @Query(
        value = "SELECT * FROM posts post INNER JOIN comments comment ON comment.id = :commentId",
        nativeQuery = true
    )
    fun findByCommentId(@Param("commentId") commentId: Long): Post
}