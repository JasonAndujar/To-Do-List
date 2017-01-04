package todo.resume.com.todo.models.objects;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Jason on 1/3/2017.
 * just a model for the data that will be stored in sqlite
 */

public class Note extends RealmObject {

    @Required
    private String title;
    @Required
    private String noteDescription;
    @Index
    private int noteId;
    @PrimaryKey
    private int id;

    public Note() {

    }

    public Note(String title, String noteDescription) {
        this.title = title;
        this.noteDescription = noteDescription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {

        this.noteDescription = noteDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }
}
