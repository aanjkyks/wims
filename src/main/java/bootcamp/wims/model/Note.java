package bootcamp.wims.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.sun.istack.internal.NotNull;

import java.util.Date;

@Entity
@Table(name = "Notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private Date noteDate;
    @NotBlank
    @NotNull
    private Integer userID;
    @NotBlank
    @NotNull
    private String text;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "tagID")
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
