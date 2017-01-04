package todo.resume.com.todo.models.database;

import io.realm.Realm;
import io.realm.RealmResults;
import todo.resume.com.todo.models.objects.Note;

/**
 * Created by Jason on 1/3/2017.
 */

public class DatabaseHelper {

    private static DatabaseHelper _dbHelper;
    private Realm mRealm = Realm.getDefaultInstance();

    public static synchronized DatabaseHelper getInstance() {
        if(_dbHelper == null)
            _dbHelper = new DatabaseHelper();
        return _dbHelper;
    }

    public void addNote(Note note) {
        note.setId(getRealmNotes().size());
        if(!mRealm.isInTransaction())
            mRealm.beginTransaction();
        mRealm.copyToRealm(note);
        mRealm.commitTransaction();
    }

    public RealmResults<Note> getRealmNotes() {
        //empty array is returned instead of null
        return mRealm.where(Note.class).findAll();
    }

    public void updateNote(Note note) {
        if(!mRealm.isInTransaction())
            mRealm.beginTransaction();
        Note toEdit = mRealm.where(Note.class).equalTo("id", note.getId()).findFirst();
        toEdit.setTitle(note.getTitle());
        toEdit.setNoteDescription(note.getNoteDescription());
        mRealm.commitTransaction();
    }

    public void removeNote(final Note note) {
        if(mRealm.isInTransaction()) {
            RealmResults<Note> notes = mRealm.where(Note.class).equalTo("id", note.getId()).findAll();
            notes.deleteAllFromRealm();
        }
    }
}
