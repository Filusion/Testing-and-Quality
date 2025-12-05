package domaca_naloga2;

import org.junit.jupiter.api.Test;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;



public class SeznamiUVEasyMockTest {

    @Test
    public void testAddThrowsNoSuchElementException() throws Exception {
        Seznam<Diplomat> mockSeznam1 = createMock(Seznam.class);
        Seznam<Diplomat> mockSeznam2 = createMock(Seznam.class);

        mockSeznam1.add(anyObject(Diplomat.class));
        expectLastCall().andThrow(new NoSuchElementException());

        mockSeznam2.add(anyObject(Diplomat.class));
        expectLastCall().andThrow(new NoSuchElementException());

        replay(mockSeznam1, mockSeznam2);

        SeznamiUV su = new SeznamiUV();
        su.addImpl("23", mockSeznam1, mockSeznam2);
        su.processInput("uporabi 23");

        String simulatedUserInput =
                "63230453\n" +
                        "Tobey\n" +
                        "Maguire\n" +
                        "UNI RI\n" +
                        "2026\n" +
                        "Naslov\n";

        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        String result = su.processInput("dodaj");

        System.setIn(originalIn);

        assertEquals("Podatkovna struktura je prazna", result);

        verify(mockSeznam1);
    }
}
