package basicSyntax;

import java.sql.SQLOutput;

public class IntegerTest {
    public static void main(String[] args) {
        Integer x = new Integer(3);
        System.out.println(x.intValue());
        System.out.println(x.compareTo(new Integer(4)));
    }

}
