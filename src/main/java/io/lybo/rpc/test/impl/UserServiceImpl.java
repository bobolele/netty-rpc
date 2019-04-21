package io.lybo.rpc.test.impl;

import io.lybo.rpc.test.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public String getById(String id) {
        return "{\"userId\":\"" + id + "\",\"userName\":\"lybo\"}";
    }
}
