package liorcorporation.todolistmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

public class DateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText editText = (EditText)findViewById(R.id.editText);
        Button okButton = (Button)findViewById(R.id.ok);
        Button cancelButton = (Button)findViewById(R.id.cancel);
        final DatePicker datePicker = (DatePicker)findViewById(R.id.datePicker);
        TextView title = (TextView)findViewById(R.id.title);
        title.setText("Add to the Todo List");

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToTodoActivity = new Intent();
                String event = editText.getText().toString();
                if (event.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "The text field is required",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Calendar c = Calendar.getInstance();
                    c.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                    backToTodoActivity.putExtra("event", event);
                    backToTodoActivity.putExtra("date", c.getTime());
                    setResult(Activity.RESULT_OK, backToTodoActivity);
                    finish();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToTodoActivity = new Intent();
                setResult(Activity.RESULT_CANCELED, backToTodoActivity);
                finish();
            }
        });
    }

}
