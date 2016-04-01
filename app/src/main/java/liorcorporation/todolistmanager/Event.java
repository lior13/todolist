package liorcorporation.todolistmanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lior.
 */
public class Event
{
    private Date date;
    private String eventTitle;
    private int id;
    private static int eventCounter = 0;
    private static List<Integer> recycledId = new ArrayList<>();

    public Event()
    {
        eventCounter++;
        id = recycledId.isEmpty() ? eventCounter : recycledId.remove(0);
    }

    public Event(String title, Date date)
    {
        this();
        eventTitle = title;
        this.date = date;
    }

    public String getTitle()
    {
        return this.eventTitle;
    }

    public Date getDate()
    {
        return this.date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setTitle(String title)
    {
        this.eventTitle = title;
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public static void removedId(int id)
    {
        if (!recycledId.contains(id) && eventCounter >= id)
        {
            recycledId.add(id);
            eventCounter--;
        }
    }

    public static void clear()
    {
        recycledId.clear();
        eventCounter = 0;
    }
}
