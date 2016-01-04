package cz.ryvo.cubeworld.world;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ChunkTest {

    @Test
    public void testGet() {
        Chunk chunk = new Chunk(3, new byte[] {
                 1,  2,  3,  4,  5,  6,  7,  8,  9, // The first tier  (z = 0)
                11, 12, 13, 14, 15, 16, 17, 18, 19, // The second tier (z = 1)
                21, 22, 23, 24, 25, 26, 27, 28, 29  // The third tier  (z = 2)
        });
        assertEquals(chunk.get(0, 0, 0),  1);
        assertEquals(chunk.get(2, 1, 1), 16);
        assertEquals(chunk.get(1, 2, 2), 28);
        assertEquals(chunk.get(2, 2, 2), 29);
    }
}
