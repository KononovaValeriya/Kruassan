package DataBase.User.Schedule;

import android.content.Context;

import com.example.ofik.AssocArray;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Manager {
    Context objContext;

    public Manager(Context objContex) {
        this.objContext = objContex;
    }

    public AssocArray get() throws Exception {
        FileInputStream objFileRes = this.objContext.openFileInput("userSchedule.dat");
        ObjectInputStream objFileReader = new ObjectInputStream(objFileRes);
        AssocArray objItem = (AssocArray) objFileReader.readObject();
        objFileReader.close();
        objFileRes.close();

        return objItem;
    }

    public void save(AssocArray objItem) throws Exception {
        FileOutputStream objFileRes = this.objContext.openFileOutput("userSchedule.dat", Context.MODE_PRIVATE);
        ObjectOutputStream objFileReader = new ObjectOutputStream(objFileRes);
        objFileReader.writeObject(objItem);
        objFileReader.close();
        objFileRes.close();
    }
}
