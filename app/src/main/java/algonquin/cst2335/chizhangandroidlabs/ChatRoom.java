package algonquin.cst2335.chizhangandroidlabs;

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

        adt = new MyChatAdapter();
        chatList.setAdapter(adt);
        //chatList.setLayoutManager(new LinearLayoutManager(this));
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        chatList.setLayoutManager(layoutManager);

        send.setOnClickListener( click -> {
                    ChatMessage nextMessage = new ChatMessage(messageTyped.getText().toString());
                    messages.add(nextMessage);//adds to array list
                    //clear the edittext:
                    messageTyped.setText("");
                    //refresh the list:
                    adt.notifyItemInserted( messages.size() - 1 ); //just insert the new row:
                }
        );

        receive.setOnClickListener( click -> {
            ChatMessage nextMessage = new ChatMessage(messageTyped.getText().toString());
            messages.add(nextMessage);
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
    private class MyChatAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View loadedRow = inflater.inflate(R.layout.sent_message, parent, false);
            return new MyRowViews(loadedRow);
        }

        @Override               //says ViewHolder, but it's acually MyRowViews object
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) { //position is which row we're building
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
            MyRowViews thisRowLayout = (MyRowViews) holder;
            thisRowLayout.messageText.setText("This is row " + position);//sets the text on the row
            thisRowLayout.timeText.setText("" + sdf);
            //set the date text:
            thisRowLayout.setPosition(position);

        }

         /*public void onBindViewHolder(MyRowViews holder, int position){
            holder.messageText.setText(messages.get(position).getMessage());
            holder.timeText.setText();

        }*/

        @Override
        public int getItemCount() {
            return messages.size();
        }

    }
        //ChatMessage thisRow = messages.get(postion);
        //@Override
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


    private class ChatMessage{
        String message;
        int sendOrReceive;
        Date timeSent;

        public ChatMessage (String s)
        {
            message = s;
        }

        public ChatMessage(String message, int sendOrReceive, Date timeSent) {
            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;
        }

        public String getMessage() {
            return message;
        }

        public int getSendOrReceive() {
            return sendOrReceive;
        }

        public Date getTimeSent() {
            return timeSent;
        }
    }

}
