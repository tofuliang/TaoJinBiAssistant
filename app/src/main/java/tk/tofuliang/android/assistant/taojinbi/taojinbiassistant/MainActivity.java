package tk.tofuliang.android.assistant.taojinbi.taojinbiassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this,AutoReceiveTaoJinBiService.class);
        Log.d("ReceiveTaoJinBiService", "Activity:" + "MainActivity");
        Log.d("ReceiveTaoJinBiService", "starting service");
        startService(intent);
        Log.d("ReceiveTaoJinBiService", "started service");
    }
}
