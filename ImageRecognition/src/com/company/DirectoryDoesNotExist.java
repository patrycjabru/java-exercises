package com.company;

public class DirectoryDoesNotExist extends Exception{
    DirectoryDoesNotExist() {
        super();
        System.out.println("Directory doesn't exist!");
    }
}
