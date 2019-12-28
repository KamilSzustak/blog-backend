package pl.kamilszustak.blog.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import pl.kamilszustak.blog.model.Post
import pl.kamilszustak.blog.model.User
import pl.kamilszustak.blog.service.PostService

@RestController
@RequestMapping("/posts")
@CrossOrigin
class PostController @Autowired constructor(
    private val postService: PostService
) {

    @GetMapping
    fun getAllPosts(): ResponseEntity<List<Post>> {
        val posts = postService.getAll()

        return ResponseEntity(posts, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getPostById(@PathVariable id: Int): ResponseEntity<Post> {
        val post = postService.getById(id)

        return if (post != null)
            ResponseEntity(post, HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PostMapping
    fun postPost(@RequestBody post: Post, @AuthenticationPrincipal user: User?): ResponseEntity<Unit> {
        post.user = user
        postService.add(post)

        return ResponseEntity(HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deletePostById(@PathVariable id: Int): ResponseEntity<Unit> {
        postService.deleteById(id)

        return ResponseEntity(HttpStatus.OK)
    }

    @PutMapping
    fun putPostById(@RequestBody post: Post): ResponseEntity<Unit> {
        postService.put(post)

        return ResponseEntity(HttpStatus.OK)
    }
}