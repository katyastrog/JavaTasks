

/**
 * Created by Екатерина on 13.10.2015.
 */
package ru.spbstu.appmath.strogalshchikova;

import java.util.Comparator;

public class HumanNameComparator implements Comparator<Human> {
    public int compare(Human human1, Human human2) {
        return (human1.getName()).compareTo(human2.getName());
    }
}
