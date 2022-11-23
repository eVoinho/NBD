package model;


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
public class Rent {

    @BsonId
    private Integer id;
    @BsonProperty("begin")
    private LocalDateTime begin;
    @BsonProperty("end")
    private LocalDateTime end;
    @BsonProperty("totalPenalty")
    private Double totalPenalty;
    @BsonProperty("client")
    private Client client;
    @BsonProperty("books")
    private List<Book> book;

    @BsonCreator
    public Rent(@BsonId Integer id,
                @BsonProperty("begin") LocalDateTime begin,
                @BsonProperty("end") LocalDateTime end,
                @BsonProperty("totalPenalty") Double totalPenalty,
                @BsonProperty("client") Client client,
                @BsonProperty("books") List<Book> book) {
        this.id = id;
        this.begin = begin;
        this.end = end;
        this.totalPenalty = totalPenalty;
        this.client = client;
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Rent rent = (Rent) o;

        return new EqualsBuilder().append(id, rent.id).append(begin, rent.begin).append(end, rent.end).append(totalPenalty, rent.totalPenalty).append(client, rent.client).append(book, rent.book).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(begin).append(end).append(totalPenalty).append(client).append(book).toHashCode();
    }

    @Override
    public String toString() {
        return "Rent{" +
                "id=" + id +
                ", begin=" + begin +
                ", end=" + end +
                ", totalPenalty=" + totalPenalty +
                ", client=" + client +
                ", book=" + book +
                '}';
    }
}
