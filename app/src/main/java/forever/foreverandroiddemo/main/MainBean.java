package forever.foreverandroiddemo.main;

/**
 * Created by forever on 16/7/8.
 */
public class MainBean {

    private String time;
    private String text;
    private Class clazz;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public MainBean(String time, String text, Class clazz) {
        this.time = time;
        this.text = text;
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return "MainBean{" +
                "time='" + time + '\'' +
                ", text='" + text + '\'' +
                ", clazz=" + clazz +
                '}';
    }
}
