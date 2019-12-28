package pl.kamilszustak.blog.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*


@Entity
@Table(name = "users")
data class User(
    @Column(name = "email", nullable = false, unique = true)
    @JsonProperty("email")
    var email: String = "",

    @Column(name = "password", nullable = false)
    @JsonProperty("password")
    var password: String = "",

    @Column(name = "first_name")
    @JsonProperty("first_name")
    var firstName: String = "",

    @Column(name = "last_name")
    @JsonProperty("last_name")
    var lastName: String = ""
) : DatabaseEntity() {

    @ManyToMany(fetch = FetchType.EAGER)
    var roles: Set<Role> = setOf()

    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var posts: MutableList<Post> = mutableListOf()
}