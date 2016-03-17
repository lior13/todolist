package liorcorporation.todolistmanager;

import java.util.Date;

/**
 * Created by lior.
 */
public class Event
{
    private Date date;
    private String eventTitle;

    public Event(String title, Date date)
    {
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
}
