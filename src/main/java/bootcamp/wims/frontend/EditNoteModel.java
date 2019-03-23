package bootcamp.wims.frontend;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditNoteModel {

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    public int id;
    public String date;
    public String name;
    public String tags;
    public String description;

    /**
     * default constructor to allow creation of new Notes
     */
    public EditNoteModel() {
    }

    public EditNoteModel(int id, String date, String name, String tags, String description) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.tags = tags;
        this.description = description;
    }

    public EditNoteModel(int id, Date date, String name, String tags, String description) {
        this.id = id;
        this.date = dateFormatter.format(date);
        this.name = name;
        this.tags = tags;
        this.description = description;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNew() {
        return id == 0;
    }

    @Override
    public String toString() {
        return "EditNoteModel{" + "date='" + date + '\'' + ", name='" + name + '\'' + ", tags='" + tags + '\'' + ", " + "description='" + description + '\'' + '}';
    }
}
