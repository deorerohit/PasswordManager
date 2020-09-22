package com.example.passwordmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<DataClass> list = new ArrayList<>();
    private long id;
    private static final int ADD_REQUEST_CODE = 1;
    private static final int UPDATE_REQUEST_CODE = 2;

    SQLiteHelperClass sqLiteHelperClass;
    SQLiteDatabase sqLiteDatabase;
    FloatingActionButton floatingActionButton;
    LinearLayout guidingLinearLayout;


    RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Key Storee");

        sqLiteHelperClass = new SQLiteHelperClass(this);
        sqLiteDatabase = sqLiteHelperClass.getWritableDatabase();

        list = getListOfDataClass();
        // recyclerAdapter.setData(list);

        guidingLinearLayout = findViewById(R.id.guiding_linearLayout);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        recyclerAdapter = new RecyclerAdapter(this, list);
        recyclerView.setAdapter(recyclerAdapter);

        floatingActionButton = findViewById(R.id.floatingBtn);


        if(list.isEmpty()) {
           guidingLinearLayout.setVisibility(View.VISIBLE);
        }
        else {
            guidingLinearLayout.setVisibility(View.GONE);
        }


       /* sqLiteHelperClass.insert("Google", "Rohit", "1", sqLiteDatabase);
        sqLiteHelperClass.insert("Micro", "Roh", "12", sqLiteDatabase);
        sqLiteHelperClass.insert("Googleeee", "RohitDEO", "13", sqLiteDatabase);*/


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0 && !floatingActionButton.isShown())
                    floatingActionButton.show();
                else if (dy > 0 && floatingActionButton.isShown())
                    floatingActionButton.hide();
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                AlertDialog.Builder deleteAlert = new AlertDialog.Builder(MainActivity.this);
                deleteAlert.setTitle("Delete Credentials");
                deleteAlert.setCancelable(false);
                deleteAlert.setMessage("Are you sure you want to delete?");
                deleteAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DataClass toDelete = recyclerAdapter.getDataAt(position);
                        sqLiteHelperClass.delete(toDelete.getId(), sqLiteDatabase);
                        Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        list.remove(toDelete);
                        recyclerAdapter.notifyItemRemoved(position);
                        //list = getListOfDataClass();
                        //  recyclerAdapter.setData(list);

                        if(list.isEmpty()) {
                            guidingLinearLayout.setVisibility(View.VISIBLE);
                        }
                        else {
                            guidingLinearLayout.setVisibility(View.GONE);
                        }
                    }
                });
                deleteAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        recyclerAdapter.notifyDataSetChanged();
                        // recyclerAdapter.setData(list);
                    }
                });
                AlertDialog alertDialog = deleteAlert.create();
                alertDialog.show();
            }
        }).attachToRecyclerView(recyclerView);


        recyclerAdapter.setOnItemClickMY(new RecyclerAdapter.ItemClickListnerMY() {
            @Override
            public void onItemClick(DataClass dataClass) {
                Intent intent = new Intent(MainActivity.this, AddCredentials.class);
                intent.putExtra(AddCredentials.EXTRA_TITLE, dataClass.getTitle());
                intent.putExtra(AddCredentials.EXTRA_USERNAME, dataClass.getUsername());
                intent.putExtra(AddCredentials.EXTRA_PASSWORD, dataClass.getPassword());
                intent.putExtra(AddCredentials.EXTRA_ID, dataClass.getId());

                startActivityForResult(intent, UPDATE_REQUEST_CODE);
            }
        });
        // sqLiteHelperClass.deleteAll(sqLiteDatabase);
    }//onCreate ends


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.settings_mainmenuItem:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.deleteAll_mainMenu:
                sqLiteHelperClass.deleteAll(sqLiteDatabase);
                list.clear();
                recyclerAdapter.notifyDataSetChanged();
               // recyclerAdapter.setData(list);
                guidingLinearLayout.setVisibility(View.VISIBLE);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public ArrayList<DataClass> getListOfDataClass() {
        ArrayList<DataClass> dataList = new ArrayList<>();
        Cursor cursor = sqLiteHelperClass.getAllData(sqLiteDatabase);
        try {
            cursor.moveToFirst();
            do {
                long id = cursor.getLong(0);
                String title = cursor.getString(1);
                String username = cursor.getString(2);
                String password = cursor.getString(3);

                DataClass dataClass = new DataClass(title, username, password);
                dataClass.setId(id);
                System.out.println(id + "*********************************************");

                dataList.add(dataClass);
            } while (cursor.moveToNext());
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "All Data Deleted", Toast.LENGTH_SHORT).show();
        }
        return dataList;
    }

    public DataClass getLatestData() {
        Cursor latestData = sqLiteHelperClass.latestInsertedData(sqLiteDatabase);
        latestData.moveToFirst();
        long Lid = latestData.getLong(0);
        String Ltitle = latestData.getString(1);
        String Lusername = latestData.getString(2);
        String Lpassword = latestData.getString(3);

        DataClass data = new DataClass(Ltitle, Lusername, Lpassword);
        data.setId(Lid);

        return data;
    }


    public void callAddCredentials(View view) {
        Intent intent = new Intent(MainActivity.this, AddCredentials.class);
        startActivityForResult(intent, ADD_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_REQUEST_CODE && resultCode == RESULT_OK) {

            String title = data.getStringExtra(AddCredentials.EXTRA_TITLE);
            String username = data.getStringExtra(AddCredentials.EXTRA_USERNAME);
            String password = data.getStringExtra(AddCredentials.EXTRA_PASSWORD);

            sqLiteHelperClass.insert(title, username, password, sqLiteDatabase);
            Toast.makeText(MainActivity.this, "Data saved", Toast.LENGTH_SHORT).show();

            list.add(getLatestData());
            recyclerAdapter.notifyItemInserted(list.size()-1);

            guidingLinearLayout.setVisibility(View.GONE);

            //list = getListOfDataClass();
            //recyclerAdapter.setData(list);
            // recyclerAdapter.notifyDataSetChanged();
        } else if (requestCode == UPDATE_REQUEST_CODE && resultCode == RESULT_OK) {

            String title = data.getStringExtra(AddCredentials.EXTRA_TITLE);
            String username = data.getStringExtra(AddCredentials.EXTRA_USERNAME);
            String password = data.getStringExtra(AddCredentials.EXTRA_PASSWORD);
            long id = data.getLongExtra(AddCredentials.EXTRA_ID, -1);

            sqLiteHelperClass.update(id, title, username, password, sqLiteDatabase);
            Toast.makeText(MainActivity.this, "Data updated", Toast.LENGTH_SHORT).show();

            list = getListOfDataClass();
            recyclerAdapter.setData(list);
            recyclerAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(MainActivity.this, "Not saved!!", Toast.LENGTH_SHORT).show();
        }
    }


    public void deleteItem(final DataClass toDelete) {
        AlertDialog.Builder deleteAlert = new AlertDialog.Builder(MainActivity.this);
        deleteAlert.setTitle("Delete Credentials");
        deleteAlert.setCancelable(false);
        deleteAlert.setMessage("Are you sure you want to delete?");
        deleteAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // DataClass toDelete = recyclerAdapter.getDataAt(position);
                sqLiteHelperClass.delete(toDelete.getId(), sqLiteDatabase);
                int position = list.indexOf(toDelete);
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                list.remove(toDelete);
                recyclerAdapter.notifyItemRemoved(position);
                //Toast.makeText(this,"list : "+list.size()+"\n Adapter list : "+recyclerAdapter.getItemCount(),Toast.LENGTH_SHORT).show();

                if(list.isEmpty()) {
                    guidingLinearLayout.setVisibility(View.VISIBLE);
                }
                else {
                    guidingLinearLayout.setVisibility(View.GONE);
                }
            }
        });
        deleteAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                recyclerAdapter.setData(list);
            }
        });
        AlertDialog alertDialog = deleteAlert.create();
        alertDialog.show();
    }

}