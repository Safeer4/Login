package com.example.login;


public class Validation {

    public boolean isEmpty(String value)
    {
        if (value.length() < 1)
            return true;
        else
            return false;
    }
}
