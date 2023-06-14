package DataBase.User;

import java.io.Serializable;

public class User implements Serializable {
    private String  strName        = "";  // Имя
    private Integer intAge         = 18;  // Возраст
    private String strWorkPosition = "";  // Должность на не любимой работе
    private String  strAddress     = "";  // Адрес никчемного бедолаги
    private Double  dblSalary      = 0.0; // Ставка на работе у этого бедолаги

    public String getName() {
        return this.strName;
    }

    public void setName(String strName) {
        this.strName = strName;
    }

    public Integer getAge() {
        return this.intAge;
    }

    public void setAge(Integer intAge) {
        this.intAge = intAge;
    }

    public String getWorkPosition() {
        return this.strWorkPosition;
    }

    public void setWorkPosition(String strJobTitle) {
        this.strWorkPosition = strJobTitle;
    }

    public String getAddress() {
        return this.strAddress;
    }

    public void setAddress(String strAddress) {
        this.strAddress = strAddress;
    }

    public Double getSalary() {
        return this.dblSalary;
    }

    public void setSalary(Double dblRate) {
        this.dblSalary = dblRate;
    }
}
