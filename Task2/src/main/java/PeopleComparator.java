import java.util.Comparator;

/**
 * Created by Екатерина on 30.09.2015.
 */
public class PeopleComparator implements Comparator<People> {

    public int compare(People object1, People object2) {
        if (object1.getAge() < object2.getAge()) {
            return -1;
        }
        if (object1.getAge() > object2.getAge()) {
            return 1;
        } else {
            return 0;
        }
    }
}
