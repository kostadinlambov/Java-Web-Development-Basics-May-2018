package org.softuni.broccolina.solet;

import java.io.IOException;

public interface HttpSolet {
    void init(SoletConfig soletConfig);

    boolean isInitialized();

    SoletConfig getSoletConfig();

    void service(HttpSoletRequest request, HttpSoletResponse response) throws IOException;
}
