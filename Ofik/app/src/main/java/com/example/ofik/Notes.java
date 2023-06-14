package com.example.ofik;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import DataBase.User.Tables.Notes.Manager;
public class Notes extends AppCompatActivity {
    ArrayList<String> objTableListData;
    TableListAdapter objTableListAdapter;
    ListView objTableList;
    EditText objNoteTextBox;
    TextView objNotifyText;
    DataBase.User.Tables.Notes.Notes objNotes;
    Manager objNotesManager;
    Context objContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_notes);
        objContext = this;
        objNoteTextBox = findViewById(R.id.notesTextBox);
        objTableList   = findViewById(R.id.tableList);
        objNotifyText  = findViewById(R.id.notifyText);
        objNotesManager = new Manager(this);
        objNotes = objNotesManager.get();
        try {
            objTableListData = new ArrayList<>();
            // Добавляем столы в список
            for (int i = 0; i < 5; i++) objTableListData.add("Стол "+String.valueOf(i + 1));
            // Создаем адаптер для списка
            objTableListAdapter = new TableListAdapter(objTableListData, this, android.R.layout.simple_list_item_1);
            objTableListAdapter.setSelectedPosition(objNotes.getLastSelectedTableNum());
            String strNoteText = objNotes.getTextByTableNum(objNotes.getLastSelectedTableNum());
            if (strNoteText.equals("")) {
                objNoteTextBox.setHint("Укажите заметку для стола № "+(objNotes.getLastSelectedTableNum()+1));
            }
            // Устанавливаем адаптер листу
            objTableList.setAdapter(objTableListAdapter);
            objTableListAdapter.notifyDataSetChanged();
        } catch (Exception objEx) {
            objEx.printStackTrace();
        }
        objNoteTextBox.setText(objNotes.getTextByTableNum(objNotes.getLastSelectedTableNum()));

        // Если выбрали стол из списка
        objTableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String strNoteText = objNotes.getTextByTableNum(i);
                objNoteTextBox.setText(strNoteText);
                objTableListAdapter.setSelectedPosition(i);
                if (strNoteText.equals("")) {
                    objNoteTextBox.setHint("Укажите заметку для стола № "+(i+1));
                } else {
                    objNoteTextBox.setHint("");
                }
                try {
                    objNotes.setLastSelectedTableNum(i);
                    objNotesManager.save(objNotes);
                } catch (Exception objEx) {
                }
            }
        });
        findViewById(R.id.notes_btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objNotes.setTextByTableNum(objNotes.getLastSelectedTableNum(), objNoteTextBox.getText().toString());
                objNotifyText.setVisibility(TextView.VISIBLE);
                try {
                    objNotesManager.save(objNotes);
                    objNotifyText.setTextColor(Color.parseColor("#FF4CAF50"));
                    objNotifyText.setText("Успешно сохранено");
                } catch (Exception objEx) {
                    objNotifyText.setTextColor(Color.parseColor("#F44336"));
                    objNotifyText.setText("Не удалось сохранить, пожалуйста попробуйте ещё раз");
                    objEx.printStackTrace();
                }
            }
        });
        // Кнопка домой
        findViewById(R.id.notes_btnToHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Notes.this, MainActivity.class));
            }
        });

        findViewById(R.id.btnToDishesComposition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Notes.this, dishesComposition.class));
            }
        });


    }
}