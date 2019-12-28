package pl.kamilszustak.blog.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pl.kamilszustak.blog.model.Comment
import java.util.*

@Repository
interface CommentRepository : JpaRepository<Comment, Int> {

    @Query(
        value = "SELECT * FROM comments WHERE content LIKE %:text%",
        nativeQuery = true
    )
    fun findAllContaining(@Param("text") text: String): List<Comment>

    @Query(
        value = "SELECT * FROM comments WHERE created_at = :date",
        nativeQuery = true
    )
    fun findAllCreatedAt(@Param("date") date: Date): List<Comment>

    @Query(
        value = "SELECT * FROM comments comment INNER JOIN posts post ON comment.post_id = post.id",
        nativeQuery = true
    )
    fun findAllByPostId(@Param("postId") postId: Int): List<Comment>

    @Query(
        value = "SELECT TOP :amount * FROM comments",
        nativeQuery = true
    )
    fun findFirst(@Param("amount") amount: Int): List<Comment>

    @Query(
        value = "SELECT * FROM comments WHERE created_at BETWEEN :startDate AND :endDate",
        nativeQuery = true
    )
    fun findAllCreatedBetween(@Param("startDate") startDate: Date, @Param("endDate") endDate: Date): List<Comment>

    @Query(
        value = "SELECT COUNT(*) FROM comments",
        nativeQuery = true
    )
    fun countAll(): Int

    @Query(
        value = "SELECT * FROM comments WHERE id IN :ids",
        nativeQuery = true
    )
    fun findAllByIds(@Param("ids") ids: Array<Long>): List<Comment>

    @Query(
        value = "SELECT * FROM comments ORDER BY created_at DESC;",
        nativeQuery = true
    )
    fun findAllOrderedByCreatedAtDate(): List<Comment>

    @Query(
        value = "SELECT * FROM comments WHERE content IS NOT NULL AND LENGTH(content) > :length",
        nativeQuery = true
    )
    fun findAllLongerThan(@Param("length") length: Int): List<Comment>

    @Query(
        value = "SELECT TOP 1 * FROM comments WHERE (SELECT MAX(id) FROM comments) = id",
        nativeQuery = true
    )
    fun findTheNewest(): Comment

    @Query(
        value = "SELECT TOP 1 * FROM comments WHERE (SELECT MIN(id) FROM comments) = id",
        nativeQuery = true
    )
    fun findTheOldest(): Comment

    @Query(
        value = "SELECT * FROM comments GROUP BY post_id",
        nativeQuery = true
    )
    fun findAllGroupedByPost(): List<Comment>
}

/**
    SELECT * FROM posts;

    SELECT * FROM comments;
    
    SELECT * FROM posts WHERE title LIKE '%aa%' OR content LIKE '%aa%';

    SELECT * FROM posts WHERE created_at = '2019-12-05 12:42:48.720000000';

    SELECT * FROM posts post WHERE (SELECT COUNT(*) FROM comments comment WHERE comment.post_id = post.id) > 0;

    SELECT * FROM posts post WHERE (SELECT COUNT(*) FROM comments comment WHERE comment.post_id = post.id) = 0;

    SELECT * FROM posts LIMIT 3;

    SELECT * FROM posts WHERE created_at BETWEEN '2019-12-05 12:42:37.412000000' AND '2019-12-05 12:42:41.882000000';

    SELECT COUNT(*) FROM posts;

    SELECT COUNT(*) FROM comments WHERE post_id = 1;

    SELECT * FROM posts WHERE id IN (1, 2, 3);

    SELECT * FROM posts ORDER BY created_at DESC;

    SELECT * FROM posts WHERE content IS NOT NULL AND LENGTH(content) > 20;

    SELECT * FROM posts WHERE (SELECT MAX(id) FROM posts) = id LIMIT 1;

    SELECT * FROM posts WHERE (SELECT MIN(id) FROM posts) = id LIMIT 1;

    SELECT * FROM posts post INNER JOIN comments comment ON comment.id = 1;

    SELECT * FROM comments ORDER BY post_id;

    DELETE FROM posts WHERE id = 3;

    CREATE TABLE favourite_posts LIKE posts;

    SELECT * FROM favourite_posts;

    INSERT INTO favourite_posts SELECT * FROM posts post WHERE post.id = 2;

    SELECT * FROM favourite_posts fav_post WHERE EXISTS(SELECT * FROM posts post WHERE post.id = fav_post.id AND post.title = fav_post.title AND post.content = fav_post.content AND post.created_at = fav_post.created_at AND post.updated_at = fav_post.updated_at);

    SELECT * FROM favourite_posts fav_post INNER JOIN posts post ON fav_post.id = post.id;

    DROP TABLE favourite_posts;

 **/