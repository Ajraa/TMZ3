package vsb.fei.tmz3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SliderValuesActivity extends AppCompatActivity {

    EditText minObdobiText;
    float minObdobi;
    EditText maxObdobiText;
    float maxObdobi;
    EditText minUrokText;
    float minUrok;
    EditText maxUrokText;
    float maxUrok;
    EditText minSumaText;
    float minSuma;
    EditText maxSumaText;
    float maxSuma;
    Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_values);

        this.minObdobiText = (EditText) findViewById(R.id.minObdobiTextView);
        this.maxObdobiText = (EditText) findViewById(R.id.maxObdobiTextView);
        this.minUrokText = (EditText) findViewById(R.id.minUrokTextView);
        this.maxUrokText = (EditText) findViewById(R.id.maxUrokTextView);
        this.minSumaText = (EditText) findViewById(R.id.minSumaTextView);
        this.maxSumaText = (EditText) findViewById(R.id.maxSumaTextView);
        this.confirmButton = (Button) findViewById(R.id.confirmButton);

        this.setText();

        this.confirmButton.setOnClickListener(v ->
        {
            this.getText();
            this.sendText();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void setText() {
        Intent intent = getIntent();
        if (intent.hasExtra("urokMin"))
            this.minUrokText.setText(String.valueOf(intent.getFloatExtra("urokMin", 0)));
        if (intent.hasExtra("urokMax"))
            this.maxUrokText.setText(String.valueOf(intent.getFloatExtra("urokMax", 0)));
        if (intent.hasExtra("vkladMin"))
            this.minSumaText.setText(String.valueOf(intent.getFloatExtra("vkladMin", 0)));
        if (intent.hasExtra("vkladMax"))
            this.maxSumaText.setText(String.valueOf(intent.getFloatExtra("vkladMax", 0)));
        if (intent.hasExtra("obdobiMin"))
            this.minObdobiText.setText(String.valueOf(intent.getFloatExtra("obdobiMin", 0)));
        if (intent.hasExtra("obdobiMax"))
            this.maxObdobiText.setText(String.valueOf(intent.getFloatExtra("obdobiMax", 0)));
    }

    public void getText() {
        this.minObdobi = Float.parseFloat(this.minObdobiText.getText().toString());
        this.maxObdobi = Float.parseFloat(this.maxObdobiText.getText().toString());
        this.minUrok = Float.parseFloat(this.minUrokText.getText().toString());
        this.maxUrok = Float.parseFloat(this.maxUrokText.getText().toString());
        this.minSuma = Float.parseFloat(this.minSumaText.getText().toString());
        this.maxSuma = Float.parseFloat(this.maxSumaText.getText().toString());
    }

    public void sendText() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("urokMin", this.minUrok);
        returnIntent.putExtra("urokMax", this.maxUrok);
        returnIntent.putExtra("vkladMin", this.minSuma);
        returnIntent.putExtra("vkladMax", this.maxSuma);
        returnIntent.putExtra("obdobiMin", this.minObdobi);
        returnIntent.putExtra("obdobiMax", this.maxObdobi);

        setResult(RESULT_OK, returnIntent);
        finish();
    }
}




















