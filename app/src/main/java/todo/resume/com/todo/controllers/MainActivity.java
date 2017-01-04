package todo.resume.com.todo.controllers;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import todo.resume.com.todo.R;
import todo.resume.com.todo.models.adapters.NoteCardAdapter;
import todo.resume.com.todo.models.database.DatabaseHelper;
import todo.resume.com.todo.models.objects.Note;
import todo.resume.com.todo.views.widgets.RobotoEditText;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.realm_recycler_view)
    RealmRecyclerView mRecyclerview;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFloatingActionButton;
    @OnClick(R.id.fab) public void onFABClick() {
        showDialog(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setupRecyclerview();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            if(getSupportFragmentManager().getBackStackEntryCount() == 0)
                mFloatingActionButton.animate().translationY(0);
        } else
            super.onBackPressed();
    }

    public void showDialog(final Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.note_dialog, null);
        final RobotoEditText noteTitle = (RobotoEditText)dialogView.findViewById(R.id.noteTitleET);
        final RobotoEditText noteDescription = (RobotoEditText)dialogView.findViewById(R.id.noteDescriptionET);

        if(note == null)
            builder.setTitle(getString(R.string.create_note));
        else {
            builder.setTitle(R.string.edit_note);
            noteTitle.setText(note.getTitle());
            noteDescription.setText(note.getNoteDescription());
        }

        builder.setView(dialogView);
        builder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String title = noteTitle.getText().toString();
                String desc = noteDescription.getText().toString();
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(desc)) {
                    if (note == null)
                        addNote(title, desc);
                    else
                        updateNote(title, desc, note);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a title and description", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void addNote(String title, String desc) {
        DatabaseHelper.getInstance().addNote(new Note(title, desc));

    }

    public void updateNote(String newTitle, String newDescription, Note note) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        note.setTitle(newTitle);
        note.setNoteDescription(newDescription);
        realm.commitTransaction();
        DatabaseHelper.getInstance().updateNote(note);
        setupRecyclerview();
    }

    public void setupRecyclerview() {
        mRecyclerview.setAdapter(new NoteCardAdapter(this, new NoteCardAdapter.OnNoteSelected() {
            @Override
            public void onSelected(Note note) {
                showDialog(note);
            }
        }, DatabaseHelper.getInstance().getRealmNotes()));
    }
}
