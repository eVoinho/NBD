package model;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;


@Setter
@Getter

public class Book {
    @BsonId
    private Integer id;
    @BsonProperty("title")
    private String title;
    @BsonProperty("genre")
    private String genre;
    @BsonProperty("pageNumber")
    private Integer pageNumber;
    @BsonProperty("author")
    private String author;
    @BsonProperty("quantity")
    private Integer quantity;

    @BsonCreator
    public Book(@BsonId Integer id,
                @BsonProperty("title") String title,
                @BsonProperty("genre") String genre,
                @BsonProperty("pageNumber") Integer pageNumber,
                @BsonProperty("author") String author,
                @BsonProperty("quantity") Integer quantity){
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.pageNumber = pageNumber;
        this.author = author;
        this.quantity = quantity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return new EqualsBuilder().append(id, book.id).append(title, book.title).append(genre, book.genre).append(pageNumber, book.pageNumber).append(author, book.author).append(quantity, book.quantity).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(title).append(genre).append(pageNumber).append(author).append(quantity).toHashCode();
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", pageNumber=" + pageNumber +
                ", author='" + author + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}