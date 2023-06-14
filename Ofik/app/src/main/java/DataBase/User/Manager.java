package DataBase.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import android.content.Context;


public class Manager {
    Context objContext;

    public Manager(Context objContex) {
        this.objContext = objContex;
    }

    public User get() throws Exception {
        FileInputStream objFileRes = this.objContext.openFileInput("userData.dat");
        ObjectInputStream objFileReader = new ObjectInputStream(objFileRes);
        User objUser = (User) objFileReader.readObject();
        objFileReader.close();
        objFileRes.close();

        return objUser;
    }

    public void save(User objUser) throws Exception {
        FileOutputStream objFileRes = this.objContext.openFileOutput("userData.dat", Context.MODE_PRIVATE);
        ObjectOutputStream objFileReader = new ObjectOutputStream(objFileRes);
        objFileReader.writeObject(objUser);
        objFileReader.close();
        objFileRes.close();
    }
}
