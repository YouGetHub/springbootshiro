package com.demo;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.Date;

public class Salt {
    public static void main(String[] args) {
        // 生产salt
        String salt = new Date().toString();
        Md5Hash md5Hash = new Md5Hash("123",salt,1024);

        System.out.println(salt);
        System.out.println(md5Hash);
    }
}
