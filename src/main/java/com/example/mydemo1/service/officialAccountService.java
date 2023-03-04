package com.example.mydemo1.service;

import java.util.Map;

public interface officialAccountService {

    String subscribeForText(String toUserName, String fromUserName);

    String unsubscribe(String fromUserName);
}
