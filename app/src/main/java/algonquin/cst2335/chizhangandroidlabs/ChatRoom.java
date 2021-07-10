package algonquin.cst2335.chizhangandroidlabs;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatRoom extends AppCompatActivity {
    MyChatAdapter adt;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    RecyclerView chatList;
    SQLiteDatabase db;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.newlayout);
        chatList = findViewById(R.id.myrecycler);
        chatList.setAdapter(new MyChatAdapter());

        EditText messageTyped = findViewById(R.id.messageEdit);
        Button send = findViewById(R.id.sendbutton);
        RecyclerView chatList = findViewById(R.id.myrecycler);

        Button receive = findViewById(R.id.receive);

        MyOpenHelper opener = new MyOpenHelper(this);
        db = opener.getWritableDatabase();

        Cursor results = db.rawQuery("Select * from " + MyOpenHelper.TABLE_NAME + ";", null);

        int _idCol = results.getColumnIndex("_id");
        int messageCol = results.getColumnIndex(MyOpenHelper.col_message);
        int sendCol = results.getColumnIndex(MyOpenHelper.col_send_receive);
        int timeCol = results.getColumnIndex(MyOpenHelper.col_time_sent);

        while (results.moveToNext()){
            long id = results.getInt(_idCol);
            String message = results.getString(messageCol);
            String time = results.getString(timeCol);
            int sendOrReceive = results.getInt(sendCol);
            messages.add(new ChatMessage(message, sendOrReceive, time, id));
        }


        adt = new MyChatAdapter();
        chatList.setAdapter(adt);
        //chatList.setLayoutManager(new LinearLayoutManager(this));
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        chatList.setLayoutManager(layoutManager);

        send.setOnClickListener( click -> {
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
                    String time = sdf.format(new Date());
                    ChatMessage cm = new ChatMessage(messageTyped.getText().toString(), 1, time);
                    ContentValues newRow = new ContentValues();
                    newRow.put(MyOpenHelper.col_message, cm.getMessage());
                    newRow.put(MyOpenHelper.col_send_receive, cm.getSendOrReceive());
                    newRow.put(MyOpenHelper.col_time_sent, cm.getTimeSent());
                    long newId = db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message, newRow);
                    cm.setId(newId);
                    messages.add(cm);//adds to array list
                    //clear the edittext:
                    messageTyped.setText("");
                    //refresh the list:
                    adt.notifyItemInserted( messages.size() - 1 ); //just insert the new row:
                }
        );

        receive.setOnClickListener( click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
            String time = sdf.format(new Date());
            ChatMessage cm = new ChatMessage(messageTyped.getText().toString(), 2, time);
            ContentValues newRow = new ContentValues();
            newRow.put(MyOpenHelper.col_message, cm.getMessage());
            newRow.put(MyOpenHelper.col_send_receive, cm.getSendOrReceive());
            newRow.put(MyOpenHelper.col_time_sent, cm.getTimeSent());
            long newId = db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message, newRow);
            cm.setId(newId);
            messages.add(cm);
            messageTyped.setText("");
            //refresh the list:
            adt.notifyItemInserted( messages.size() - 1 );

        });
    }

    private class MyRowViews extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;
        int position = -1;
        int row = getAbsoluteAdapterPosition();

        public MyRowViews(View itemView) {
            super(itemView);
            itemView.setOnClickListener(click -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this)
                        .setMessage("Do you want to delete the message? " + messageText.getText())
                        .setTitle("Question? ")
                        .setNegativeButton("No ", (dialog, cl) -> {
                        })
                        .setPositiveButton("Yes", (dialog, cl) -> {
                            position = getAbsoluteAdapterPosition();
                            ChatMessage removedMessage = messages.get(position);

                            messages.remove(position);
                            adt.notifyItemRemoved(position);


                            Snackbar.make(messageText, "you deleted message # " + position, Snackbar.LENGTH_LONG )
                                    .setAction("undo ", clk -> {
                                        messages.add(position, removedMessage);
                                        adt.notifyItemInserted(position);

                                    })
                                    .show();
                        });
                builder.create().show();
            });

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);

        }

        public void setPosition(int p) { position = p; }
    }
    private class MyChatAdapter extends RecyclerView.Adapter<MyRowViews> {

        @Override
        public MyRowViews onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            int layoutID;
            if(viewType == 1 )
                layoutID = R.layout.sent_message;
            else
                layoutID = R.layout.receive_message;
            View loadedRow = inflater.inflate(layoutID, parent, false);
            return new MyRowViews(loadedRow);
        }

        @Override
        public void onBindViewHolder(MyRowViews holder, int position){
            holder.messageText.setText(messages.get(position).getMessage());
            holder.timeText.setText(messages.get(position).getTimeSent());
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        @Override
        public int getItemViewType(int position){
            ChatMessage thisRow = messages.get(position);
            return thisRow.getSendOrReceive();
        };
    }


    private class ChatMessage{
        String message;
        int sendOrReceive;
        String timeSent;
        long id;

        public void setId(long l) { id = l;}
        public long getId() { return id;}

        public ChatMessage (String s)
        {
            message = s;
        }

        public ChatMessage(String message, int sendOrReceive, String timeSent) {
            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;
        }

        public ChatMessage(String message, int sendOrReceive, String timeSent, long id) {
            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;
            setId(id);
        }

        public String getMessage() {
            return message;
        }

        public int getSendOrReceive() {
            return sendOrReceive;
        }

        public String getTimeSent() {
            return timeSent;
        }
    }

}
