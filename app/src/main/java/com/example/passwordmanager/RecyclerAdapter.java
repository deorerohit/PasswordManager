package com.example.passwordmanager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.DataClassHolder> {

    private ArrayList<DataClass> dataClasses = new ArrayList<>();
    MainActivity mainActivity;
    ClipboardManager clipboardManager;

    private ItemClickListnerMY listnerMY;

    public RecyclerAdapter(MainActivity mainActivity,ArrayList<DataClass> dataClassArrayList) {
        this.mainActivity = mainActivity;
        this.dataClasses = dataClassArrayList;
    }

    @NonNull
    @Override
    public DataClassHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout, parent, false);
        return new DataClassHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataClassHolder holder, final int position) {

        final DataClass dataClass = dataClasses.get(position);
        Typeface typeface = ResourcesCompat.getFont(mainActivity,R.font.rubik_light);
        Typeface boldTypeface = ResourcesCompat.getFont(mainActivity,R.font.poppins_bold);
        Typeface regularTypeface = ResourcesCompat.getFont(mainActivity,R.font.poppins_regular);



        String title = dataClass.getTitle();
        String username = dataClass.getUsername();
        String password = dataClass.getPassword();

        if(title.isEmpty()) {
            title = "<no title>";
            holder.title_textview.setText(title);
            holder.title_textview.setAlpha(0.3f);
        }
        else {
            holder.title_textview.setText(title);
        }
        if(username.isEmpty()) {
            username = "<no username>";
            holder.username_textview.setText(username);
            holder.username_textview.setAlpha(0.3f);
        }
        else {
            holder.username_textview.setText(username);
        }
        if(password.isEmpty()) {
            password = "<no password>";
            holder.password_textview.setText(password);
            holder.password_textview.setAlpha(0.3f);
        }
        else {
            holder.password_textview.setText(password);
        }







        holder.optionMenu_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(mainActivity, holder.optionMenu_textview);
                popupMenu.inflate(R.menu.recycler_item_menu);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {

                            case R.id.copyUsername_menuitem:
                                copyUsername(holder.username_textview.getText().toString());
                                break;
                            case R.id.copuPassword_menuitem:
                                copyPassword(holder.password_textview.getText().toString());
                                break;
                            case R.id.delete_menuitem:
                                mainActivity.deleteItem(dataClass);
                                break;
                            default:
                                return false;
                        }
                        return false;
                    }
                });
                popupMenu.show();


            }
        });


    }

    @Override
    public int getItemCount() {
        return dataClasses.size();
    }

    public void setSingleData(DataClass toDelete, int position) {
       // int positionFromHere = this.dataClasses.indexOf(toDelete);
        this.dataClasses.remove(position);
        notifyItemRemoved(position);
        //notifyItemRangeChanged(position, getItemCount());
    }


    public void setData(ArrayList<DataClass> dataClasses) {
        this.dataClasses = dataClasses;
     //   notifyDataSetChanged();
    }

    public DataClass getDataAt(int position) {
        return dataClasses.get(position);
    }


    class DataClassHolder extends RecyclerView.ViewHolder {

        private TextView title_textview;
        private TextView username_textview;
        private TextView password_textview;
        private ImageView optionMenu_textview;


        public DataClassHolder(@NonNull View itemView) {
            super(itemView);

            title_textview = itemView.findViewById(R.id.title_textview);
            username_textview = itemView.findViewById(R.id.username_textview);
            password_textview = itemView.findViewById(R.id.password_textview);
            optionMenu_textview = itemView.findViewById(R.id.optionMenu_textview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listnerMY != null && position != RecyclerView.NO_POSITION)
                        listnerMY.onItemClick(dataClasses.get(position));
                }
            });

        }

    }

    public interface ItemClickListnerMY {
        void onItemClick(DataClass dataClass);
    }

    public void setOnItemClickMY(ItemClickListnerMY listnerMY) {
        this.listnerMY = listnerMY;
    }


    public void copyUsername(String copiedUsername) {

        if(!copiedUsername.isEmpty()) {
            clipboardManager = (ClipboardManager) mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("key", copiedUsername);
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(mainActivity,"Username copied", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mainActivity, "No text to be copied", Toast.LENGTH_SHORT).show();
        }

    }


    public void copyPassword(String copiedPassword) {

        if(!copiedPassword.isEmpty()) {
            clipboardManager = (ClipboardManager) mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("key", copiedPassword);
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(mainActivity,"Password copied", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mainActivity, "No text to be copied", Toast.LENGTH_SHORT).show();
        }
    }
}
