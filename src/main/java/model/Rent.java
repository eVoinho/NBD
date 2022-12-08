package model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
@Setter
@EqualsAndHashCode(of = "Id")
public class Rent {

    @BsonId
    private Integer id;
    @BsonProperty("begin")
    private LocalDateTime begin;
    @BsonProperty("client")
    private Client client;
    @BsonProperty("books")
    private List<Book> book;

    @BsonCreator
    public Rent(@BsonId Integer id,
                @BsonProperty("begin") LocalDateTime begin,
                @BsonProperty("client") Client client,
                @BsonProperty("books") List<Book> book) {
        this.id = id;
        this.begin = begin;
        this.client = client;
        this.book = book;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "id=" + id +
                ", begin=" + begin +
                ", client=" + client +
                ", book=" + book +
                '}';
    }
}
