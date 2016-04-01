package liorcorporation.todolistmanager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lior.
 */
public class TodoListAdapter extends ArrayAdapter<Event> {

    private ArrayList<Event> arrList = new ArrayList<>();

    public TodoListAdapter(Context context, int resource, ArrayList<Event> arrList) {
        super(context, resource, arrList);
        this.arrList = arrList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_layout, parent, false);
        }
        TextView line = (TextView) convertView.findViewById(R.id.line);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.todo);
        Date curDate = new Date(System.currentTimeMillis());
        line.setTextColor(curDate.before(arrList.get(position).getDate()) ? Color.BLUE : Color.RED);
        line.setText(arrList.get(position).getTitle());
        date.setTextColor(curDate.before(arrList.get(position).getDate()) ? Color.BLUE : Color.RED);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        date.setText(format.format(arrList.get(position).getDate()));
        imageView.setImageResource(R.drawable.todo);
        return convertView;
    }
}