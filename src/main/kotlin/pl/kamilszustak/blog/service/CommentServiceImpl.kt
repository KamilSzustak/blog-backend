package pl.kamilszustak.blog.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.kamilszustak.blog.model.Comment
import pl.kamilszustak.blog.repository.CommentRepository

@Service
@Primary
class CommentServiceImpl @Autowired constructor(
    private val commentRepository: CommentRepository
) : CommentService {

    init {
        //println(commentRepository.findAllByPostId(1))
        //println(commentRepository.findAllGroupedByPost())
    }

    override fun getAll(): List<Comment> = commentRepository.findAll()

    override fun getById(id: Int): Comment? = commentRepository.findByIdOrNull(id)

    override fun add(item: Comment) {
        commentRepository.save(item)
    }

    override fun deleteById(id: Int) {
        commentRepository.deleteById(id)
    }

    override fun put(item: Comment) {
        commentRepository.save(item)
    }
}