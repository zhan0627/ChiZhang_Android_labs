package algonquin.cst2335.chizhangandroidlabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class MessageListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View chatLayout = inflater.inflate(R.layout.chatlayout, container, false);
        RecyclerView chatList = chatLayout.findViewById(R.id.myrecycler);
        Button send = chatLayout.findViewById(R.id.sendbutton);
        Button receive = chatLayout.findViewById(R.id.receive);
        EditText messageTyped = chatLayout.findViewById(R.id.messageEdit);
        //problem
        /*
        adt = new MyChatAdapter();
        chatList.setAdapter(adt);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        chatList.setLayoutManager (new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        send.setOnClickListener( click -> {
                    ChatMessage thisMessage = new ChatMessage(messageTyped.getText().toString(), 1, new Date());
                    messages.add(thisMessage);
                    adt.notifyItemInserted( messages.size() - 1 ); //just insert the new row:
                    messageTyped.setText("");

                }
        );
        receive.setOnClickListener( click -> {
            ChatMessage thisMessage = new ChatMessage(messageTyped.getText().toString(), 2, new Date());
            messages.add(thisMessage);
            adt.notifyItemInserted( messages.size() - 1 );
            messageTyped.setText("");
        });

         */
        return chatLayout;
    }
}
