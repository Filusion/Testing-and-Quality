package domaca_naloga2;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

class Drevo23MockTest {

    @Test
    public void testAddThrowsOutOfMemoryWithEasyMock() {
        Seznam<Diplomat> mockSeznam1 = createMock(Seznam.class);
        Seznam<Diplomat> mockSeznam2 = createMock(Seznam.class);

        mockSeznam1.add(anyObject(Diplomat.class));
        expectLastCall().andThrow(new OutOfMemoryError()).once();

        mockSeznam2.add(anyObject(Diplomat.class));
        expectLastCall().andThrow(new OutOfMemoryError()).once();

        expect(mockSeznam1.isEmpty()).andReturn(false).anyTimes();
        expect(mockSeznam2.isEmpty()).andReturn(false).anyTimes();

        replay(mockSeznam1, mockSeznam2);

        SeznamiUV su = new SeznamiUV();
        su.addImpl("23", mockSeznam1, mockSeznam2);

        String simulatedUserInput =
                "63230453\n" +
                        "Tobey\n" +
                        "Maguire\n" +
                        "UNI RI\n" +
                        "2026\n" +
                        "Naslov\n";

        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        su.processInput("uporabi 23");

        String result = su.processInput("dodaj");

        assertEquals("Premalo pomnilnika, operacija ni uspela", result);

        verify(mockSeznam1);
    }
}