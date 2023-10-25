package vsb.fei.tmz3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
    ListView dataView;
    Button backButton;
    Button clearButton;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        this.dataView = (ListView) findViewById(R.id.dataListView);
        this.backButton = (Button) findViewById(R.id.backButton);
        this.clearButton = (Button) findViewById(R.id.clearButton);
        this.sharedPreferences = getSharedPreferences("TAMZ3", Context.MODE_PRIVATE);

        this.backButton.setOnClickListener(v -> this.back());
        this.clearButton.setOnClickListener(v -> this.clear());
        this.load();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void back() {
        finish();
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void load() {
        Map<String, ?> keys = sharedPreferences.getAll();
        List<FinanceData> data = new ArrayList<FinanceData>();
        Gson gson = new Gson();
        for(Map.Entry<String, ?> entry : keys.entrySet())
            data.add(gson.fromJson(entry.getValue().toString(), FinanceData.class));
        ArrayAdapter<FinanceData> adapter = new ArrayAdapter<FinanceData>
                (this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, data);
        this.dataView.setAdapter(adapter);
    }
}










