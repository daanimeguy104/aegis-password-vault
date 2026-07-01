import java.util.Arrays;

public class Blake2b {

    private static final long[] IV = {
        0x6a09e667f3bcc908L,
        0xbb67ae8584caa73bL,
        0x3c6ef372fe94f82bL,
        0xa54ff53a5f1d36f1L,
        0x510e527fade682d1L,
        0x9b05688c2b3e6c1fL,
        0x1f83d9abfb41bd6bL,
        0x5be0cd19137e2179L
    };
    
    private static final byte[][] SIGMA = {
        {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},
        {14,10,4,8,9,15,13,6,1,12,0,2,11,7,5,3},
        {11,8,12,0,5,2,15,13,10,14,3,6,7,1,9,4},
        {7,9,3,1,13,12,11,14,2,6,5,10,4,0,15,8},
        {9,0,5,7,2,4,10,15,14,1,11,12,6,8,3,13},
        {2,12,6,10,0,11,8,3,4,13,7,1,15,14,5,9},
        {12,5,1,15,14,13,4,10,0,7,6,3,9,2,8,11},
        {13,11,7,14,12,1,3,9,5,0,15,4,8,6,2,10},
        {6,15,14,9,11,3,0,8,12,2,13,7,1,4,10,5},
        {10,2,8,4,7,6,1,5,15,11,9,14,3,12,13,0},
        {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},
        {14,10,4,8,9,15,13,6,1,12,0,2,11,7,5,3}
    };
    
    private final long[] h;
    private final byte[] buffer;
    private int bufferPos;
    private long[] t;
    
    public Blake2b() {
        h = new long[IV.length];
        for(int i = 0; i < h.length; i++) {
            h[i] = IV[i];
        }
        h[0] ^= 0x01010040L;
        
        buffer = new byte[128];
        bufferPos = 0;
        t = new long[2];
    }
    
    private void g(long[] v, int a, int b, int c, int d, long x, long y) {
        v[a] += x + v[b];
        v[d] = Long.rotateRight(v[a] ^ v[d], 32);
        v[c] += v[d];
        v[b] = Long.rotateRight(v[b] ^ v[c], 24);
        v[a] += y + v[b];
        v[d] = Long.rotateRight(v[a] ^ v[d], 16);
        v[c] += v[d];
        v[b] = Long.rotateRight(v[b] ^ v[c], 31);
    }
    
    private void compress(long[] h, long[] m, long[] t, boolean isLast) {
        long[] v = new long[16];
        for(int i = 0; i < h.length; i++) {
            v[i] = h[i];
        }
        for(int i = 0; i < IV.length; i++) {
            v[i + h.length] = IV[i];
        }
        
        v[12] ^= t[0];
        v[13] ^= t[1];
        if(isLast) {
            v[14] ^= ~0L;
        }
        
        for(int i = 0; i < SIGMA.length; i++) {
            g(v, 0, 4, 8, 12, m[SIGMA[i][0]], m[SIGMA[i][1]]);
            g(v, 1, 5, 9, 13, m[SIGMA[i][2]], m[SIGMA[i][3]]);
            g(v, 2, 6, 10, 14, m[SIGMA[i][4]], m[SIGMA[i][5]]);
            g(v, 3, 7, 11, 15, m[SIGMA[i][6]], m[SIGMA[i][7]]);
            
            g(v, 0, 5, 10, 15, m[SIGMA[i][8]], m[SIGMA[i][9]]);
            g(v, 1, 6, 11, 12, m[SIGMA[i][10]], m[SIGMA[i][11]]);
            g(v, 2, 7, 8, 13, m[SIGMA[i][12]], m[SIGMA[i][13]]);
            g(v, 3, 4, 9, 14, m[SIGMA[i][14]], m[SIGMA[i][15]]);
        }
        
        for(int i = 0; i < h.length; i++) {
            h[i] ^= v[i] ^ v[i + 8];
        }
    }
    
    private void bytesToLongs(byte[] src, int srcPos, long[] dest, int toRead) {
        Arrays.fill(dest, 0L);
        
        for(int i = 0; i < toRead; i++) {
            int target = i / 8;
            int shift = (i % 8) * 8;
            
            dest[target] |= (long)(src[srcPos + i] & 0xFF) << shift;
        }
    }
    
    private byte[] longsToBytes(long[] src) {
        byte[] dest = new byte[src.length * 8];
        
        for(int i = 0; i < dest.length; i++) {
            int target = i / 8;
            int shift = (i % 8) * 8;
            
            dest[i] = (byte) ((src[target] >>> shift) & 0xFF);
        }
        
        return dest;
    }
    
    public void update(byte[] input) {
        if(input == null || input.length == 0) {
            return;
        }
        
        int length = input.length;
        int offset = 0;
        
        while(length > 0) {
            if(bufferPos == 128) {
                t[0] += 128;
                if(t[0] < 128) {
                    t[1]++;
                }
                
                long[] m = new long[16];
                bytesToLongs(buffer, 0, m, 128);
                compress(h, m, t, false);
                bufferPos = 0;
            }
            
            int chunk = Math.min(length, 128 - bufferPos);
            System.arraycopy(input, offset, buffer, bufferPos, chunk);
            
            bufferPos += chunk;
            offset += chunk;
            length -= chunk;
        }
    }
    
    public byte[] digest() {
        t[0] += bufferPos;
        if(t[0] < bufferPos) {
            t[1]++;
        }
        
        long[] m = new long[16];
        bytesToLongs(buffer, 0, m, bufferPos);
        compress(h, m, t, true);
        byte[] result = longsToBytes(h);
        
        return result;
    }
}
