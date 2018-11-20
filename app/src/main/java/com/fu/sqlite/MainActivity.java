package com.fu.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SqlHelper mSqlHelper;
    private EditText mText;
    private Button insertdata, listdata, update,delete;
    private View contextView;
    private TextView tvId;

    private ListView mListView;
    private ListAdapter mListAdapter;
    private String id, name;
    private List<Employee> mEmployeeList;
    private Employee employee;

    private boolean insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSqlHelper = new SqlHelper(this);
        mText = findViewById(R.id.name);
        tvId = findViewById(R.id.id);


        insertdata = findViewById(R.id.button);
        listdata = findViewById(R.id.button2);
        update = findViewById(R.id.button3);
        delete = findViewById(R.id.button4);

        contextView = findViewById(R.id.context_view);



        mListView = findViewById(R.id.list);

        adddata();
        listdata();
        update();
        delete();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "" + mEmployeeList.get(position).getName() + "", Toast.LENGTH_LONG).show();
                mText.setText(mEmployeeList.get(position).getName());
                tvId.setText(mEmployeeList.get(position).getId().toString());
            }
        });
    }


    public void adddata() {
        insertdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert = mSqlHelper.insertdata(mText.getText().toString());
                if (insert = true) {
                    Snackbar.make(contextView, "Insert Successful", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(contextView, "Insert Failure", Snackbar.LENGTH_SHORT).show();
                }
                mText.setText(" ");
            }
        });
    }


    public void listdata() {
        listdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmployeeList = new ArrayList<>();
                SQLiteDatabase database = mSqlHelper.getReadableDatabase();
                String sql = " SELECT * FROM employee";
                Cursor cursor = database.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    id = cursor.getString(0);
                    name = cursor.getString(1);
                    employee = new Employee(Integer.valueOf(id), name);
                    mEmployeeList.add(employee);
                    mListAdapter = new ListAdapter(MainActivity.this, mEmployeeList);
                    mListView.setAdapter(mListAdapter);
                }

                cursor.close();
                database.close();
            }
        });

    }

    public void update() {
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert = mSqlHelper.update(mText.getText().toString(), Integer.valueOf(tvId.getText().toString()));
                if (insert = true) {
                    Snackbar.make(contextView, "Update Successful", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(contextView, "Update Failure", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void delete() {
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert = mSqlHelper.delete(Integer.valueOf(tvId.getText().toString()));
                if (insert = true) {
                    Snackbar.make(contextView, "Delete Successful", Snackbar.LENGTH_LONG).show();
                    mText.setText(" ");
                    tvId.setText(" ");
                } else {
                    Snackbar.make(contextView, "Delete  Failure", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSqlHelper.close();
    }
}
