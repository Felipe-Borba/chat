package com.felipeborba;

import java.io.IOException;

public interface OnMessage {
    void onReceived(Message result) throws IOException;
}
