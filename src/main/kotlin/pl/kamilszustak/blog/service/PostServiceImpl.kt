package pl.kamilszustak.blog.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.kamilszustak.blog.model.Post
import pl.kamilszustak.blog.repository.PostRepository
import java.util.*

@Service
@Primary
class PostServiceImpl @Autowired constructor(
    private val postRepository: PostRepository
) : PostService {

    init {
        //println(postRepository.findContaining("afk"))
        //println(postRepository.findAllCreatedAt(Date()))
        //println(postRepository.findAllWithComments())
        //println(postRepository.findFirst(3))
        //println(postRepository.findAllCreatedBetween(Date(), Date()))
        //println(postRepository.countAll())
        //println(postRepository.countComments(1))
        //println(postRepository.findAllByIds(arrayOf(1, 2)))
        //println(postRepository.findAllWithoutComments())
        //println(postRepository.findAllOrderedByCreatedAtDate())
        //println(postRepository.findAllLongerThan(15))
        //println(postRepository.findTheNewest())
    }

    override fun getAll(): List<Post> = postRepository.findAll()

    override fun getById(id: Int): Post? = postRepository.findByIdOrNull(id)

    override fun add(item: Post) {
        postRepository.save(item)
    }

    override fun deleteById(id: Int) {
        postRepository.deleteById(id)
    }

    override fun put(item: Post) {
        postRepository.save(item)
    }
}