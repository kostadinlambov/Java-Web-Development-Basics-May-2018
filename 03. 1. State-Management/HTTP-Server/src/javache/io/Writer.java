package javache.io;

import java.io.*;

public final class Writer {
    private Writer() {}

    public static void writeBytes(byte[] byteData, OutputStream outputStream) throws IOException {
        DataOutputStream buffer = new DataOutputStream(outputStream);

        buffer.write(byteData);
    }
}
