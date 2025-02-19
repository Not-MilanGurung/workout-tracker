package beds.backend;

import java.sql.Time;

public class Test {
    public static void main(String[] args) {
        Set s1 = new Set(new Time(0));
        s1.setMetricA(23);
        System.out.println(s1.getMetricA());
    }
}
