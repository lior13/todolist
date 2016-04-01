package liorcorporation.todolistmanager;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TodoListManagerActivity extends AppCompatActivity {

    private TodoListAdapter arrAdapter;
    private ArrayList<Event> arrList;
    private static final int ADD_RESULT = 1;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView listView = (ListView) findViewById(R.id.listView);
        dbHandler = new DBHandler(this);
        arrList = (ArrayList<Event>)dbHandler.getAllEvents();
        arrAdapter = new TodoListAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrList);
        listView.setAdapter(arrAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Dialog dialog = new Dialog(TodoListManagerActivity.this);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_list_menu);
                TextView title = (TextView) dialog.findViewById(R.id.title);
                String event = arrList.get(position).getTitle();
                title.setText(event);
                if (event.toLowerCase().contains("call"))
            {
                Pattern pattern = Pattern.compile("(\\d+\\-*\\d+\\-?)+");
                final Matcher matcher = pattern.matcher(event);
                if (matcher.find()) {
                    TextView call = (TextView)dialog.findViewById(R.id.call);
                    call.setVisibility(View.VISIBLE);
                    call.setText("Call: " + matcher.group(0));
                }
                dialog.findViewById(R.id.call).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +
                                matcher.group(1).replaceAll("-", "")));
                        startActivity(dial);
                        dialog.dismiss();
                    }
                });
            }
                dialog.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbHandler.deleteEvent(arrList.get(position));
                        arrList.remove(position);
                        arrAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {
            Intent intent = new Intent(this, DateActivity.class);
            startActivityForResult(intent, ADD_RESULT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_RESULT) {
            switch (resultCode) {
                case RESULT_OK:
                    String event = data.getStringExtra("event");
                    Date date = (Date)data.getSerializableExtra("date");
                    dbHandler.addEvent(new Event(event, date));
                    arrList.add(new Event(event, date));
                    arrAdapter.notifyDataSetChanged();
                    break;
                case RESULT_CANCELED:
                    //Do nothing
                    break;
            }
        }
    }
}