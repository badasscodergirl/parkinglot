package com.gojek.parking.app.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class InputService {

    private static BufferedReader br;
    private static InputService instance;

    private InputService() {}

    public static InputService getInstance() {
        if(instance == null) {
            instance = new InputService();
            init();
        }
        return instance;
    }

    private static void init() {
        if(br == null)
            br = new BufferedReader(new InputStreamReader(System.in));
    }

    public String readLine() {
        String line = null;
        try {
            line = br.readLine();
        } catch (IOException e) {
            System.out.println("Error occurred while reading "+e.getMessage());
        }
        return line;
    }

    public List<String> readFileInput(String fileName) throws IOException {
        try (InputStream is = InputService.class.getResourceAsStream("/"+fileName)) {
            if (null == is) {
                throw new FileNotFoundException(fileName);
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                List<String> inputLines = new ArrayList<>();
                String line;
                while((line = br.readLine()) != null) {
                    inputLines.add(line);
                }
                return inputLines;
            }
        }
    }


    public void closeReader() throws IOException {
        if(br != null)
            br.close();
    }
}
