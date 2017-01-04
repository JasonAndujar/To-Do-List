package todo.resume.com.todo.models.application;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Jason on 1/3/2017.
 */

public class NoteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
    }
}
