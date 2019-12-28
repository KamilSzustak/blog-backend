package pl.kamilszustak.blog.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.kamilszustak.blog.model.Comment
import pl.kamilszustak.blog.service.CommentService
import pl.kamilszustak.blog.service.PostService

@RestController
@RequestMapping("/comments")
@CrossOrigin
class CommentController @Autowired constructor(
    private val commentService: CommentService,
    private val postService: PostService
) {

    @GetMapping("/{id}")
    fun getCommentById(@PathVariable id: Int): ResponseEntity<Comment> {
        val comment = commentService.getById(id)

        return if (comment != null)
            ResponseEntity(comment, HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/{postId}")
    fun postComment(@PathVariable postId: Int, @RequestBody comment: Comment): ResponseEntity<Unit> {
        val post = postService.getById(postId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        comment.post = post
        commentService.add(comment)

        return ResponseEntity(HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteCommentById(@PathVariable id: Int): ResponseEntity<Unit> {
        commentService.deleteById(id)

        return ResponseEntity(HttpStatus.OK)
    }

    @PutMapping
    fun putCommentById(comment: Comment): ResponseEntity<Unit> {
        commentService.put(comment)

        return ResponseEntity(HttpStatus.OK)
    }
}