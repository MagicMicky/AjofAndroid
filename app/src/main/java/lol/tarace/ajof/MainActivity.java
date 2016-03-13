package lol.tarace.ajof;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.FacebookSdk;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import lol.tarace.ajof.utils.ItemClickSupport;

public class MainActivity extends AppCompatActivity {
    private AjofPlayer mAjofPlayer;
    private int lastPlayed = -1;
    private AjofElementsAdapter mAdapter;
    private AjofSender mSender;
    private boolean mPicking = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //need to initialize facebook sdk
        FacebookSdk.sdkInitialize(getApplicationContext());

        //Retrieve assets from
        Field[] fields=R.raw.class.getFields();
        final List<String> myDataset = new ArrayList<String>();
        for (Field field : fields) {
            Log.i("Raw Asset: ", field.getName());
            myDataset.add(field.getName());
            int resID = this.getResources().getIdentifier(field.getName(), "raw", this.getPackageName());
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rv = (RecyclerView) findViewById(R.id.RV_ajof_elements);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);

        mAdapter = new AjofElementsAdapter(myDataset);
        rv.setAdapter(mAdapter);

        //Create the MediaPlayer and play wololo when oppening the app
        mAjofPlayer = new AjofPlayer(myDataset, this);
        mAjofPlayer.initialize();

        mSender = new AjofSender(this);

        if(Intent.ACTION_PICK.equals(getIntent().getAction())) {
            this.mPicking=true;
        }

        ItemClickSupport.addTo(rv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) throws IOException {
                mAjofPlayer.play(position);
                lastPlayed = position;
                mAdapter.setSelected(position);
                Snackbar.make(v, myDataset.get(position), Snackbar.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.messenger_send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSender.send(myDataset.get(lastPlayed), mPicking);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        this.mAjofPlayer.stop();
        super.onPause();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
