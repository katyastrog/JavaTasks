/**
 * Created by Екатерина on 30.09.2015.
 */

import java.util.Comparator;

public class HumanComparator implements Comparator<Human> {

    public int compare(Human object1, Human object2) {
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
