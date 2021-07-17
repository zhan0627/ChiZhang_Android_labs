package algonquin.cst2335.chizhangandroidlabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MessageListFragment extends Fragment {

    MyChatAdapter adt;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    RecyclerView chatList;
    //SQLiteDatabase db;

    Button send;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter, Bundle savedInstanceState) {
        View chatLayout = inflater.inflate(R.layout.chatlayout, containter, false);
        //send = chatLayout.findViewById(R.id.sendbutton);
        //setContentView(R.layout.chatlayout);
        chatList = chatLayout.findViewById(R.id.myrecycler);
        chatList.setAdapter(new MyChatAdapter());

        EditText messageTyped = chatLayout.findViewById(R.id.messageEdit);
        send = chatLayout.findViewById(R.id.sendbutton);
        RecyclerView chatList = chatLayout.findViewById(R.id.myrecycler);

        Button receive = chatLayout.findViewById(R.id.receive);

        adt = new MyChatAdapter();
        chatList.setAdapter(adt);
        //chatList.setLayoutManager(new LinearLayoutManager(this));
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        chatList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        send.setOnClickListener(click -> {
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
                    String time = sdf.format(new Date());
                    ChatMessage cm = new ChatMessage(messageTyped.getText().toString(), 1, time);

                    messages.add(cm);//adds to array list
                    //clear the edittext:
                    messageTyped.setText("");
                    //refresh the list:
                    adt.notifyItemInserted(messages.size() - 1); //just insert the new row:
                }
        );

        receive.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
            String time = sdf.format(new Date());
            ChatMessage cm = new ChatMessage(messageTyped.getText().toString(), 2, time);
            messages.add(cm);
            messageTyped.setText("");
            //refresh the list:
            adt.notifyItemInserted(messages.size() - 1);

        });

        return chatLayout;
    }

    private class MyRowViews extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;
        int position = -1;
        int row = getAbsoluteAdapterPosition();

        public MyRowViews(View itemView) {
            super(itemView);
            itemView.setOnClickListener(click -> {/*
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                        .setMessage("Do you want to delete the message? " + messageText.getText())
                        .setTitle("Question? ")
                        .setNegativeButton("No ", (dialog, cl) -> {
                        })
                        .setPositiveButton("Yes", (dialog, cl) -> {
                            position = getAbsoluteAdapterPosition();
                            ChatMessage removedMessage = messages.get(position);

                            messages.remove(position);
                            adt.notifyItemRemoved(position);

                            Snackbar.make(messageText, "you deleted message # " + position, Snackbar.LENGTH_LONG)
                                    .setAction("undo ", clk -> {
                                        messages.add(position, removedMessage);
                                        adt.notifyItemInserted(position);
                                    })
                                    .show();
                        });
                builder.create().show();*/

                ChatRoom parentActivity = (ChatRoom)getContext();
                int position = getAbsoluteAdapterPosition();
                parentActivity.userClickedMessage(messages.get(position), position);
            });

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);

        }

        public void setPosition(int p) {
            position = p;
        }
    }

    private class MyChatAdapter extends RecyclerView.Adapter<MyRowViews> {

        @Override
        public MyRowViews onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            int layoutID;
            if (viewType == 1)
                layoutID = R.layout.sent_message;
            else
                layoutID = R.layout.receive_message;
            View loadedRow = inflater.inflate(layoutID, parent, false);
            return new MyRowViews(loadedRow);
        }

        @Override
        public void onBindViewHolder(MyRowViews holder, int position) {
            holder.messageText.setText(messages.get(position).getMessage());
            holder.timeText.setText(messages.get(position).getTimeSent());
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        @Override
        public int getItemViewType(int position) {
            ChatMessage thisRow = messages.get(position);
            return thisRow.getSendOrReceive();
        }

        ;
    }


    public class ChatMessage {
        String message;
        int sendOrReceive;
        String timeSent;
        long id;

        public void setId(long l) {
            id = l;
        }

        public long getId() {
            return id;
        }

        public ChatMessage(String s) {
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

    public void notifyMessageDeleted (ChatMessage chosenMessage, int chosenPosition){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setMessage("Do you want to delete the message? " + chosenMessage.getMessage())
                .setTitle("Question? ")
                .setNegativeButton("No ", (dialog, cl) -> {
                })
                .setPositiveButton("Yes", (dialog, cl) -> {

                    ChatMessage removedMsg = messages.get(chosenPosition);

                    messages.remove(chosenPosition);
                    adt.notifyItemRemoved(chosenPosition);

                    Snackbar.make(send, "you deleted message # " + chosenPosition, Snackbar.LENGTH_LONG)
                            .setAction("undo ", clk -> {
                                messages.add(chosenPosition, removedMsg);
                                adt.notifyItemInserted(chosenPosition);
                            })
                            .show();
                });
        builder.create().show();
    }

}