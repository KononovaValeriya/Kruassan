package DataBase.User.Tables.Notes;

import java.io.Serializable;

public class Notes implements Serializable {
    private final String[] objArNotes = new String[10];
    private int intLastSelectedTable = 0;

    public Notes() {
        for (int i = 0; i < 10; i++) this.objArNotes[i] = "";
    }

    public String getTextByTableNum(int intTableNum) {
        return this.objArNotes[intTableNum];
    }

    public void setTextByTableNum(int intTableNum, String strText) {
        this.objArNotes[intTableNum] = strText;
    }

    public int getLastSelectedTableNum() {
        return this.intLastSelectedTable;
    }

    public void setLastSelectedTableNum(int intNum) {
        this.intLastSelectedTable = intNum;
    }
}
