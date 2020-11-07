package com.lanswon.authserver.service;

import com.lanswon.base.support.SimpleResponse;

public interface UserService {

    SimpleResponse getUser(String phone);
}
