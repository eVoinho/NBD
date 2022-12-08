package model;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;


@Setter
@Getter
@EqualsAndHashCode(of = "id")
public class Book {
    @BsonId
    private ObjectId id;
    @BsonProperty("title")
    private String title;
    @BsonProperty("genre")
    private String genre;
    @BsonProperty("pageNumber")
    private Integer pageNumber;

    @BsonCreator
    public Book(@BsonProperty("title") String title,
                @BsonProperty("genre") String genre,
                @BsonProperty("pageNumber") Integer pageNumber){
        this.id = new ObjectId();
        this.title = title;
        this.genre = genre;
        this.pageNumber = pageNumber;
    }


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", pageNumber=" + pageNumber +
                '}';
    }
}