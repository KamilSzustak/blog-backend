package pl.kamilszustak.blog.model

import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "roles")
class Role(
    var name: String
) : DatabaseEntity() {

    @ManyToMany(mappedBy = "roles")
    var users: Set<User> = setOf()
}