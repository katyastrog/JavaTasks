import java.util.Comparator;

/**
 * Created by ��������� on 13.10.2015.
 */
public class HumanNameComparator implements Comparator<Human> {
    public int compare(Human human1, Human human2) {
        return (human1.getName()).compareTo(human2.getName());
    }
}
