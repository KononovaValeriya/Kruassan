package com.example.ofik;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import DataBase.User.Manager;
import DataBase.User.User;
public class KabinetActivity extends AppCompatActivity {
    User objUser = new User();
    Manager objUserManager;
    Context objFileContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_kabinet);
        objUserManager = new Manager(this);
        EditText objEditTextUserName          = findViewById(R.id.userName);
        EditText objEditTextUserAge           = findViewById(R.id.userAge);
        EditText objEditTextUserPositionWork  = findViewById(R.id.userPositionWork);
        EditText objEditTextUserAddress       = findViewById(R.id.userAddress);
        EditText objSalaryInput               = findViewById(R.id.salaryInput);
        TextView objTextViewNotification      = findViewById(R.id.textViewNotification);
        /** Получаем данные **/
        try {
            this.objUser = this.objUserManager.get();
            objEditTextUserName.setText(this.objUser.getName());
            objEditTextUserAge.setText(String.valueOf(this.objUser.getAge()));
            objEditTextUserPositionWork.setText(String.valueOf(this.objUser.getWorkPosition()));
            objEditTextUserAddress.setText(this.objUser.getAddress());
            objSalaryInput.setText(String.valueOf(this.objUser.getSalary()));
        } catch (Exception objEx) {
            objEx.printStackTrace();
        }
        findViewById(R.id.profile_saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Сохраняем данные пользователя
                objTextViewNotification.setVisibility(TextView.VISIBLE);
                try {
                    String  strName = objEditTextUserName.getText().toString();
                    if (strName.equals("")) throw new Exception("Пожалуйста, введите своё имя");
                    objUser.setName(strName);

                    Integer intAge = 18;
                    if (!objEditTextUserAge.getText().toString().equals(""))
                        intAge = Integer.valueOf(objEditTextUserAge.getText().toString());
                    objUser.setAge(intAge);

                    String  strPosWork = objEditTextUserPositionWork.getText().toString();
                    if (strPosWork.equals(""))          throw new Exception("Пожалуйста, введите свою должность");
                    objUser.setWorkPosition(strPosWork);

                    String  strAddress = objEditTextUserAddress.getText().toString();
                    if (strAddress.equals("")) throw new Exception("Пожалуйста, введите свой адрес");
                    objUser.setAddress(strAddress);

                    Double  dblSalary  = 0.0;
                    if (!objSalaryInput.getText().toString().equals(""))
                        dblSalary = Double.valueOf(objSalaryInput.getText().toString());
                    objUser.setSalary(dblSalary);

                    objTextViewNotification.setText("Успешно сохранено");
                    objTextViewNotification.setTextColor(Color.parseColor("#FF4CAF50"));
                    try {
                        objUserManager.save(objUser);
                    } catch (Exception objEx) {
                        objTextViewNotification.setTextColor(Color.parseColor("#F44336"));
                        objEx.printStackTrace();
                        objTextViewNotification.setText("Возникла системная ошибка при сохранении данных.\nПожалуйста, попробуйте ещё раз");
                    }
                } catch (Exception objEx) {
                    objTextViewNotification.setTextColor(Color.parseColor("#F44336"));
                    objTextViewNotification.setText(objEx.getMessage());
                }
            }
        });
        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KabinetActivity.this, MainActivity.class));
            }
        });
    }

}