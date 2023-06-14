package com.example.ofik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class dishesComposition extends AppCompatActivity {

    ListView objDishesList;

    ListView objCompositionsText;
    EditText objSearchTextInput;
    CustomAdapter objDishesAdapter;
    CustomAdapter objCompositionsAdapter;
    ArrayList<String> arCompositionsData;

    AssocArray arDisches = new AssocArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dishes_composition);

        objDishesList      = findViewById(R.id.dishesList);
        objSearchTextInput = findViewById(R.id.searchTextInput);
        objCompositionsText = findViewById(R.id.dishesCompositionText);

        arDisches.set("Филе судака с приправленным японским рисом и томатной сальсой-150/240гр.",
                "Судак филе скожей-183гр.\n"+
                "Соль поваренная пещевая-1гр.\n"+
                "Масло оливковое-10мл.\n"+
                "Лимон-10гр.\n"+
                "Специи Лимонная приправа к рыбе-1гр.\n"+
                "Японский рис для суши п/ф-130гр.\n"+
                        "Томатная сальса п/ф-50гр.\n"+
                        "Зелень базилик-3гр.\n"+
                        "Зелень Салат-7гр.\n"+
                "Помидоры черри-20гр."
        );
        arDisches.set("Куриный паштет с брусникой-160гр.",
                "Хлеб Сарацин-60гр.\n"+
                        "Масло оливковое с ароматом трюфеля-2мл.\n"+
                        "Паштет куриный с брусничным желе-100гр.\n"+
                        "Микрозелень в ассортименте"
        );
        arDisches.set("Мини-веллингтон с говядиной-210гр.",
            "Говядина-110гр.\n"+
            "Шампиньоны-30гр.\n"+
            "Лук репчатый-15гр.\n"+
            "Чеснок-5гр.\n"+
            "Ветчина-20гр.\n"+
            "Горчица-5мл.\n"+
            "Масло оливковое-3мл.\n"+
            "Масло сливочное-20гр.\n"+
            "Яйцо куриное-3шт.\n"+
            "Тесто слоеное бездрожжевое-60гр.\n"+
            "Соль-1гр.\n"+
            "Перец свежемолотый-1гр."
        );
        arDisches.set("Куриные окорочка с овощами-270гр.",
            "Окорочка куриные-170гр.\n"+
            "Картофель-50гр.\n"+
            "Кабачок-30гр.\n"+
            "Помидор-30гр.\n"+
            "Лук репчатый-10гр.\n"+
            "Чеснок-5гр.\n"+
            "Зелень-3гр.\n"+
            "Мука пшеничная-20гр.\n"+
            "Кетчуп-20мл.\n"+
            "Яйцо куриное-4шт.\n"+
            "Соль-1гр.\n"+
            "Прованские травы-1гр."
        );
        arDisches.set("Мясной рулет с ананасами-150гр.",
            "Фарш-120гр.\n"+
            "Сыр-20гр.\n"+
            "Ананасы-10гр.\n"+
            "Чеснок-3гр.\n"+
            "Белок-5гр.\n"+
            "Зелень-3гр.\n"+
            "Соль-1гр.\n"+
            "Специи-2гр."
        );
        arDisches.set("Болоньезе-254гр.",
            "Масло оливковое-2мл.\n"+
            "Луковица-50гр.\n"+
            "Сельдерей, ствол-20гр.\n"+
            "Чеснок-5гр.\n"+
            "Чили сушеный-1гр.\n"+
            "Говяжий фарш-150гр.\n"+
            "Телятина-50гр.\n"+
            "Тимьян свежий, листья-3гр.\n"+
            "Тимьян свежий, листья-2гр.\n"+
            "Лавровый лист-1гр."
        );

        // Добавляем блюда в список для адаптера
        ArrayList<String> objDishesListData = new ArrayList<>(Arrays.asList(arDisches.getKeys()));

        // Добавляем список блюд в адаптер ListView
        objDishesAdapter = new CustomAdapter(this, android.R.layout.simple_list_item_1, objDishesListData);

        // Устанавливаем адаптер с данными в ListView
        objDishesList.setAdapter(objDishesAdapter);

        // Обновляем отображение списка, чтобы пункты отобразились
        objDishesAdapter.notifyDataSetChanged();


        /** Создаем адаптер для списка состава **/
        arCompositionsData = new ArrayList<>();
        objCompositionsAdapter = new CustomAdapter(this, android.R.layout.simple_list_item_1, arCompositionsData);
        objCompositionsText.setAdapter(objCompositionsAdapter);
        objCompositionsAdapter.notifyDataSetChanged();

        /** Обработка выбора блюда **/
        objDishesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                objDishesAdapter.setSelectedPosition(i);

                String[] arLines = arDisches.get(objDishesListData.get(i)).split("\n");
                arCompositionsData.clear();
                for (int j = 0; j < arLines.length; j++) arCompositionsData.add(
                        arLines[j]
                );
                objCompositionsAdapter.notifyDataSetChanged();
                objCompositionsText.setVisibility(ListView.VISIBLE);
                findViewById(R.id.textView4).setVisibility(TextView.VISIBLE);
            }
        });

        objSearchTextInput.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                objDishesListData.clear();
                String[] arKeys = arDisches.getKeys();
                String strSearchText = objSearchTextInput.getText().toString().toLowerCase();
                String strItemName = "";
                for (int i = 0; i < arKeys.length; i++) {
                    strItemName = arKeys[i].toLowerCase();
                    if (!strItemName.contains(strSearchText)) continue;
                    objDishesListData.add(arKeys[i]);
                }

                objDishesAdapter.notifyDataSetChanged();

                return false;
            }

            public boolean onKeyDown(int keyCode, KeyEvent event) {

//                objDishesCompositionText.setText(keyCode);
                return false;
            }
        });

        // Кнопка домой
        findViewById(R.id.dischesComp_homeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dishesComposition.this, MainActivity.class));
            }
        });
        findViewById(R.id.actDishesComp_btnToNotes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dishesComposition.this, Notes.class));
            }
        });
    }
}