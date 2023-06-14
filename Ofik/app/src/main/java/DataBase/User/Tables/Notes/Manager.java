package DataBase.User.Tables.Notes;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Manager {
    Context objContext;

    public Manager(Context objContex) {
        this.objContext = objContex;
    }

    public Notes get() {
        Notes objNotes = new Notes();

        try {
            FileInputStream objFileRes = this.objContext.openFileInput("notesData.dat");
            ObjectInputStream objFileReader = new ObjectInputStream(objFileRes);
            objNotes = (Notes) objFileReader.readObject();
            objFileReader.close();
            objFileRes.close();
        } catch (Exception objEx) {

        }

        return objNotes;
    }

    public void save(Notes objNotes) throws Exception {
        FileOutputStream objFileRes = this.objContext.openFileOutput("notesData.dat", Context.MODE_PRIVATE);
        ObjectOutputStream objFileReader = new ObjectOutputStream(objFileRes);
        objFileReader.writeObject(objNotes);
        objFileReader.close();
        objFileRes.close();
    }
}
