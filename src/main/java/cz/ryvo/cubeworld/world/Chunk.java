package cz.ryvo.cubeworld.world;

public class Chunk {

    /**
     * Chunk: 16^3 = 4096
     * Radius in chunks: 24
     * Word: 10^3 * 4096 = 4096000
     *
     * type: 256
     * direction: s j v z
     * damage: 100
     * quality: 100
     */
    private final int chunkSize;        // Size of chunk edge in voxels
    private final int tierDistance;     // Distance between the two neighbouring tiers in voxels

    private byte[] voxels;

    public Chunk(int size, byte[] voxels) {
        this.chunkSize = size;
        this.tierDistance = size * size;
        this.voxels = voxels;
    }

    public void set(int x, int y, int z, byte b) {
        voxels[z * tierDistance + y * chunkSize + x] = b;
    }

    public byte get(int x, int y, int z) {
        return voxels[z * tierDistance + y * chunkSize + x];
    }
}
