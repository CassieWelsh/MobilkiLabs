package com.example.labfivesql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    final String LOG_TAG = "myLogs";
    Button mBtnAdd, mBtnRead, mBtnClear;
    EditText mEdtName, mEdtEmail, mId;
    TextView mRows;
    DBHelper mDbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnAdd = (Button) findViewById(R.id.buttonAdd);
        mBtnAdd.setOnClickListener(this::onClick);
        mBtnRead = (Button) findViewById(R.id.buttonEdit);
        mBtnRead.setOnClickListener(this::onClick);
        mBtnClear = (Button) findViewById(R.id.buttonClear);
        mBtnClear.setOnClickListener(this::onClick);
        mEdtName = (EditText) findViewById(R.id.editTextName);
        mEdtEmail = (EditText) findViewById(R.id.editTextEmail);
        mId = (EditText) findViewById(R.id.editTextId);
        mRows = findViewById(R.id.rows);
        mDbHelper = new DBHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        updateText(db);
        mDbHelper.close();
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        // создаем объект для данных
        ContentValues cv = new ContentValues();
        // получаем данные из полей ввода
        String id = mId.getText().toString();
        String name = mEdtName.getText().toString();
        String email = mEdtEmail.getText().toString();
        // подключаемся к БД
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        if (v.getId() == R.id.buttonAdd) {
            Log.d(LOG_TAG, "--- Insert in mytable: ---");
            // подготовим данные для вставки в виде пар: наименование столбца -
            // значение
            cv.put("name", name);
            cv.put("email", email);
            // вставляем запись и получаем ее ID
            long rowID = db.insert("mytable", null, cv);
            Log.d(LOG_TAG, "row inserted, ID = " + rowID);
        }
        if (v.getId() == R.id.buttonEdit) {
            int intId = -1;
            try {
                intId = Integer.parseInt(id);
            } catch (Exception e) {
                mDbHelper.close();
                return;
            }

            db.execSQL("update mytable " +
                    "set name='" + name + "', email='" + email + "'" +
                    "where id=" + id + "\n");
        }
        if (v.getId() == R.id.buttonClear) {
            Log.d(LOG_TAG, "--- Clear mytable: ---");
            // удаляем все записи
            int clearCount = db.delete("mytable", null, null);
            Log.d(LOG_TAG, "deleted rows count = " + clearCount);
        }
        // закрываем подключение к БД
        updateText(db);
        mDbHelper.close();
    }

    private void updateText(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- Rows in mytable: ---");
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        Cursor c = db.query("mytable", null, null, null, null, null, null);
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        int idColIndex = c.getColumnIndex("id");
        int nameColIndex = c.getColumnIndex("name");
        int emailColIndex = c.getColumnIndex("email");
        String result = "";
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            do {
                // получаем значения по номерам столбцов и пишем все в лог
                result += "ID = " + c.getInt(idColIndex) + ", name = "
                        + c.getString(nameColIndex) + ", email = "
                        + c.getString(emailColIndex) + "\n";
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false -
                // выходим из цикла
            } while (c.moveToNext());
        } else
            result = "0 rows";
        c.close();
        mRows.setText(result);
    }

    class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "email text" + ");");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}