package com.vityarthi.campuspulse;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException {
        EventService service = new EventService(new CsvEventRepository(Path.of("data")));
        new ConsoleMenu(service, new Scanner(System.in)).run();
    }
}
