/**
 * Created by Екатерина on 30.09.2015.
 */

import java.util.Comparator;

public class HumanAgeComparator implements Comparator<Human> {
    public int compare(Human human1, Human human2) {
        return Integer.compare(human1.getAge(), human2.getAge());
    }
}
