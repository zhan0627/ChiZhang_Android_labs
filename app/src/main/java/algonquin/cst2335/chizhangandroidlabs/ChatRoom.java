package algonquin.cst2335.chizhangandroidlabs;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ChatRoom extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_layout);

        //getSupportFragmentMangaer().beginTranscation().add(R.id.fragmentRoom, new MessageListFragment()).commit();

        MessageListFragment chatFragment = new MessageListFragment();
        FragmentManager fMgr =  getSupportFragmentManager(); // only one object
        FragmentTransaction tx = fMgr.beginTransaction();

        tx.add(R.id.fragmentRoom, chatFragment);
        tx.commit();
    }
    
}
