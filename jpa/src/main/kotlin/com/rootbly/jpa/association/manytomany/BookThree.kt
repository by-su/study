package com.rootbly.jpa.association.manytomany

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.OrderBy

@Entity
class BookThree(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val isbn: String,
    val title: String,

) {

    @ManyToMany(mappedBy = "books")
    @OrderBy("name ASC")
    var authors: MutableSet<AuthorThree> = mutableSetOf()
        protected set
}
