package liorcorporation.todolistmanager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lior.
 */
public class TodoListAdapter extends ArrayAdapter<String> {

    private ArrayList<String> arrList = new ArrayList<>();

    public TodoListAdapter(Context context, int resource, ArrayList<String> arrList) {
        super(context, resource, arrList);
        this.arrList = arrList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("LIOR", "getView was called");
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_layout, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.line);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.todo);
        textView.setTextColor(position % 2 == 0 ? Color.RED : Color.BLUE);
        textView.setText(arrList.get(position));
        imageView.setImageResource(R.drawable.todo);
        return convertView;
    }
}