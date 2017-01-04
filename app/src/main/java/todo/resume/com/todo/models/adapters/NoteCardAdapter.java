package todo.resume.com.todo.models.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import todo.resume.com.todo.R;
import todo.resume.com.todo.models.objects.Note;
import todo.resume.com.todo.views.widgets.RobotoTextView;

/**
 * Created by Jason on 1/3/2017.
 */

public class NoteCardAdapter extends RealmBasedRecyclerViewAdapter<Note, NoteCardAdapter.NoteCardViewHolder> {//extends RecyclerView.Adapter<NoteCardAdapter.NoteCardViewHolder> {

    public interface OnNoteSelected {
        void onSelected(Note note);
    }

    private OnNoteSelected mListener;
    private RealmResults<Note> mNotes;

    public NoteCardAdapter(Context context, OnNoteSelected mListener, RealmResults<Note> mNotes) {
        super(context, mNotes, true, true);
        this.mListener = mListener;
        this.mNotes = mNotes;
    }

    @Override
    public NoteCardViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        return new NoteCardViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_cell, viewGroup, false));
    }

    @Override
    public void onBindRealmViewHolder(NoteCardViewHolder holder, int position) {
        Note note = mNotes.get(position);
        if(note != null) {
            holder.titleText.setText(note.getTitle());
            holder.descText.setText(note.getNoteDescription());
        }
    }

    @Override
    public int getItemCount() {
        return mNotes == null ? 0 : mNotes.size();
    }

    public class NoteCardViewHolder extends RealmViewHolder implements View.OnClickListener {

        @BindView(R.id.title_text)
        RobotoTextView titleText;
        @BindView(R.id.desc_text)
        RobotoTextView descText;

        public NoteCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mListener != null && mNotes != null)
                mListener.onSelected(mNotes.get(getAdapterPosition()));
        }
    }
}
