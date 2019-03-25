package bootcamp.wims.model;

import javax.persistence.*;
import java.util.Date;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
@Table(name = "Notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Field
    private String name;
    @Column(name = "noteDate")
    @Field
    public Date noteDate;
    @Column(name = "userID")
    private Integer userID;
    @Field
    private String text;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "tagID")
    @Field
    private Tag tag;

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Tag getTag() {
        return tag;
    }

    public Note() {
    }

    public Note(Integer id, String name, Date noteDate, Integer userID, Tag tag, String text) {
        this.id = id;
        this.name = name;
        this.noteDate = noteDate;
        this.userID = userID;
        this.text = text;
        this.tag = tag;
    }

    public Integer getId() {
        return id;
    }

    public Note setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Note setName(String name) {
        this.name = name;
        return this;
    }

    public Date getDate() {
        return noteDate;
    }

    public Note setDate(Date noteDate) {
        this.noteDate = noteDate;
        return this;
    }

    public Integer getUserID() {
        return userID;
    }

    public Note setUserId(Integer userID) {
        this.userID = userID;
        return this;
    }

    public String getText() {
        return text;
    }

    public Note setText(String text) {
        this.text = text;
        return this;
    }


}
