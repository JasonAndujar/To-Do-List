package todo.resume.com.todo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;
import io.realm.RealmResults;
import todo.resume.com.todo.models.database.DatabaseHelper;
import todo.resume.com.todo.models.objects.Note;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RealmUnitTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("todo.resume.com.todo", appContext.getPackageName());
    }

    @Test
    public void createNote_isCorrect() throws Exception {
        Realm.init(InstrumentationRegistry.getTargetContext());
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        //delete all old notes first
        RealmResults<Note> notes = DatabaseHelper.getInstance().getRealmNotes();
        for (Note note : notes) {
            DatabaseHelper.getInstance().removeNote(note);
        }
        assertEquals(0, DatabaseHelper.getInstance().getRealmNotes().size());

        //test a new note
        DatabaseHelper.getInstance().addNote(new Note("Test", "Test"));
        assertEquals(1, DatabaseHelper.getInstance().getRealmNotes().size());

        //edit the note and confirm edit occurred
        Note editNote = DatabaseHelper.getInstance().getRealmNotes().first();
        editNote.setTitle("Test 2");
        editNote.setNoteDescription("Test 2");
        DatabaseHelper.getInstance().updateNote(editNote);

        realm.commitTransaction();

        Note newNote = DatabaseHelper.getInstance().getRealmNotes().first();
        assertEquals("Test 2", newNote.getTitle());
        assertEquals("Test 2", newNote.getNoteDescription());

    }
}
