package domaca_naloga2;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//class NonComparableClass {
//
//    public NonComparableClass() {
//    }
//}

class IntegerComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer s1, Integer s2) {
        return s1.compareTo(s2);
    }
}

public class Drevo23Test {
    private Drevo23<Integer> drevo = new Drevo23<>(new IntegerComparator());

    public Drevo23Test() {}

    @BeforeEach
    public void setUp() {
        drevo = new Drevo23<>(new IntegerComparator());
    }

    @ParameterizedTest
    @CsvSource({
            "'8', '[(8)]'",
            "'8,1', '[(1,8)]'",
            "'8,10', '[(8,10)]'",
            "'5,8,4', '[(5), (4), (8)]'",
            "'5,8,6', '[(6), (5), (8)]'",
            "'5,8,10', '[(8), (5), (10)]'",
            "'5,3,8,1', '[(5), (1,3), (8)]'",
            "'5,3,8,9', '[(5), (3), (8,9)]'",
            "'5,3,8,6,9', '[(5,8), (3), (6), (9)]'",
            "'5,3,8,6,9,1', '[(5,8), (1,3), (6), (9)]'",
            "'5,3,8,6,9,7', '[(5,8), (3), (6,7), (9)]'",
            "'5,3,8,6,9,10', '[(5,8), (3), (6), (9,10)]'",
            "'5,3,8,6,9,10,11', '[(8), (5), (3), (6), (10), (9), (11)]'",
            "'5,3,8,6,9,1,2', '[(5), (2), (1), (3), (8), (6), (9)]'",
            "'5,3,9,6,10,7,8', '[(7), (5), (3), (6), (9), (8), (10)]'",
    })
    public void testAdd(String input, String expectedOutput) {

        String[] inputValues = input.split(",");
        for (String value : inputValues) {
            drevo.add(Integer.parseInt(value));
        }

        List<Integer> actualList = drevo.asList();

        String actualOutput = actualList.toString();

        assertEquals(expectedOutput, actualOutput);
        if (input.length() >= 7) {
            try {
                drevo.save(new FileOutputStream("data.bin"));
                drevo.getElement(Integer.parseInt("10"));
                drevo.restore(new FileInputStream("data.bin"));
            } catch (Exception e) {

            }
        }
    }


    @Test
    public void testAddSame() {
        drevo.add(5);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            drevo.add(5);
        });
        assertEquals("Dvojnik ni dovoljen.", ex.getMessage());
    }

    @Test
    public void testAddSameFirst() {
        drevo.add(5);
        drevo.add(8);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            drevo.add(5);
        });
        assertEquals("Dvojnik ni dovoljen.", ex.getMessage());
    }

    @Test
    public void testAddSameSecond() {
        drevo.add(5);
        drevo.add(8);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            drevo.add(8);
        });
        assertEquals("Dvojnik ni dovoljen.", ex.getMessage());
    }

    @Test
    public void testIsEmptyTrue() {
        assertTrue(drevo.isEmpty());
    }

    @Test
    public void testIsEmptyFalse() {
        drevo.add(10);
        assertFalse(drevo.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "'', '0'",
            "'1', '1'",
            "'1,2,3,4,5', '5'",
    })
    public void testSize(String input, String expectedOutput) {
        if (!input.isEmpty()) {
            String[] inputValues = input.split(",");
            for (String value : inputValues) {
                drevo.add(Integer.parseInt(value));
            }
        }
        int expOutput = Integer.parseInt(expectedOutput);

        assertEquals(expOutput, drevo.size());
    }

    @ParameterizedTest
    @CsvSource({
            "'', '0'",
            "'1', '1'",
            "'1,2,3,4,5', '2'",
            "'5,3,8,6,9,10,11', '3'"
    })
    public void testDepth(String input, String expectedOutput) {
        if (!input.isEmpty()) {
            String[] inputValues = input.split(",");
            for (String value : inputValues) {
                drevo.add(Integer.parseInt(value));
            }
        }
        int expOutput = Integer.parseInt(expectedOutput);

        assertEquals(expOutput, drevo.depth());
    }

    @Test
    public void testExistsLeft() {
        drevo.add(5); drevo.add(3); drevo.add(8);
        drevo.add(6); drevo.add(9); drevo.add(1);
        assertTrue(drevo.exists(1));
    }

    @Test
    public void testExistsMiddle() {
        drevo.add(5); drevo.add(3); drevo.add(8);
        drevo.add(6); drevo.add(9); drevo.add(7);
        assertTrue(drevo.exists(7));
    }

    @Test
    public void testExistsRigt() {
        drevo.add(5); drevo.add(3); drevo.add(8);
        drevo.add(6); drevo.add(9); drevo.add(10);
        assertTrue(drevo.exists(10));
    }

    @Test
    public void testExistsFalse() {
        drevo.add(5); drevo.add(3); drevo.add(8);
        drevo.add(6); drevo.add(9); drevo.add(1);
        assertFalse(drevo.exists(0));
    }

    @Test
    public void getFirst() {
        drevo.add(5);
        drevo.add(3);
        drevo.add(8);
        assertEquals(5, drevo.getFirst());
    }

    @Test
    public void getFirstNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            drevo.getFirst();
        });
        assertEquals("Drevo je prazno.", ex.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "'', '', ''",
            "'5', '5', []",
            "'5,8', '5', '[(8)]'",
            "'5,8', '8', '[(5)]'",
            "'5,8,3', '3', '[(5,8)]'",
            "'5,8,3', '8', '[(3,5)]'",
            "'5,8,3,1', '1', '[(5), (3), (8)]'",
            "'5,8,3,9', '3', '[(8), (5), (9)]'",
            "'5,8,3,1', '8', '[(3), (1), (5)]'",
            "'30,50,20,40,60', '60', '[(30), (20), (40,50)]'",
            "'5,3,8,6,9', '3', '[(8), (5,6), (9)]'",
            "'5,3,8,6,9', '6', '[(5), (3), (8,9)]'",
            "'5,3,8,6,9', '9', '[(5), (3), (6,8)]'",
            "'5,3,8,6,9,7', '3', '[(6,8), (5), (7), (9)]'",
            "'5,3,8,6,9,1,10', '6', '[(3,8), (1), (5), (9,10)]'",
            "'30,10,20,40,5,15,25,35,45,100,55,43', '20', '[(25,40), (10), (5), (15), (30), (null), (35), (55), (43,45), (100)]'",
            "'20,30,40,35,45', '40', '[(30), (20), (35,45)]'"
    })
    public void remove(String toAdd, String toRemove, String expectedList) {
        if (!toAdd.isEmpty()) {
            String[] addingValues = toAdd.split(",");
            for (String value : addingValues) {
                drevo.add(Integer.parseInt(value));
            }

            int removeElement = Integer.parseInt(toRemove);
            drevo.remove(removeElement);

            List<Integer> actualList = drevo.asList();

            String actualOutputList = actualList.toString();
            assertEquals(expectedList, actualOutputList);
        }
    }

    @Test
    public void testRemoveNull() {
        drevo.add(5);
        assertThrows(NullPointerException.class, () -> {
            drevo.remove(null);
        });    }

    @Test
    public void testRemoveFromEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            drevo.remove(1);
        });
        assertEquals("Drevo je prazno.", ex.getMessage());
    }

    @Test
    public void testRemoveUnexistingEl() {
        drevo.add(5);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            drevo.remove(1);
        });
        assertEquals("Element ni v drevesu.", ex.getMessage());
    }

    @Test
    public void removeFirst() {
        drevo.add(1);
        drevo.add(2);
        drevo.add(3);
        drevo.removeFirst();
        List<Integer> actualList = drevo.asList();

        String actualOutputList = actualList.toString();
        assertEquals("[(1,3)]", actualOutputList);
    }

    @Test
    public void testPrint() {
        drevo.add(1); drevo.add(2); drevo.add(3); drevo.add(4);
        drevo.add(8); drevo.add(7); drevo.add(6); drevo.add(5);
        drevo.add(9); drevo.add(10); drevo.add(11); drevo.add(12);
        drevo.print();
        drevo.getElement(Integer.parseInt("12"));
    }
}