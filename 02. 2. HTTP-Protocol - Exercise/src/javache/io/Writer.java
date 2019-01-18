package javache.io;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class Writer {

    private Writer() {}

    public static void writeBytes(byte[] byteData, OutputStream clientSocketOutputStream) throws IOException {
        DataOutputStream buffer = new DataOutputStream(clientSocketOutputStream);

        buffer.write(byteData);
    }
}
