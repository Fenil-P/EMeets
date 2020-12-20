package Controllers;

import Exceptions.DoesNotExistError;

import java.io.FileNotFoundException;

/**
 * The current access point for the program in Phase 1. Run this to initialize the program and load the objects
 *
 * @version 0.1.0
 *
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException, DoesNotExistError {
        PresenterMain pm = new PresenterMain();
    }
}
