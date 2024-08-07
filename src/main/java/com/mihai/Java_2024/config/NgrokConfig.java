package com.mihai.Java_2024.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PreDestroy;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Configuration
public class NgrokConfig {

    private Process ngrokProcess;
    private Path ngrokExecutable;

    @Bean
    public ApplicationRunner ngrokRunner() {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                startNgrok();
            }
        };
    }

    private void startNgrok() {
        try {
            // Extract ngrok binary from resources
            ngrokExecutable = extractNgrokExecutable();

            // Run ngrok
            ngrokProcess = new ProcessBuilder(ngrokExecutable.toString(), "http", "--domain=completely-notable-killdeer.ngrok-free.app", "8080")
                    .start();
            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(ngrokProcess.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path extractNgrokExecutable() throws IOException {
        InputStream ngrokStream = getClass().getResourceAsStream("/ngrok.exe");
        if (ngrokStream == null) {
            throw new FileNotFoundException("Ngrok executable not found in resources");
        }
        Path tempNgrok = Files.createTempFile("ngrok", ".exe");
        Files.copy(ngrokStream, tempNgrok, StandardCopyOption.REPLACE_EXISTING);
        tempNgrok.toFile().setExecutable(true);
        ngrokStream.close();
        return tempNgrok;
    }

    @PreDestroy
    public void stopNgrok() {
        if (ngrokProcess != null) {
            ngrokProcess.destroy();
            try {
                ngrokProcess.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            if (ngrokExecutable != null) {
                Files.deleteIfExists(ngrokExecutable);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
