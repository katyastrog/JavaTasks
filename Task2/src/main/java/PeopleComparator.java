import java.util.Comparator;

/**
 * Created by Екатерина on 30.09.2015.
 */
public class PeopleComparator implements Comparator<People> {

    public int compare(People o1, People o2) {
        if (o1.getAge() < o2.getAge()) {
            return -1;
        }
        if (o1.getAge() > o2.getAge()) {
            return 1;
        } else {
            return 0;
        }
    }
}
