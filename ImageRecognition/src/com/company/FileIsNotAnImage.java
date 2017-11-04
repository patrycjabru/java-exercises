package com.company;

public class FileIsNotAnImage extends Exception {
    FileIsNotAnImage() {
        super();
        System.out.println("Input directory consist files other than image!");
    }
}
