package pl.devcezz.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.*;

public class TestFiles {

    private TestFiles() {}

    public static Path createTestFileInDir(String name, Path dir) {
        try {
            Path dirPath = dir.resolve(name);
            return Files.createFile(dirPath);
        } catch (InvalidPathException | IOException e) {
            throw new FailTestException("Cannot create empty file for test", e);
        }
    }

    public static void writeMessageToFile(Path path, String message) {
        try {
            Files.write(path, List.of(message), CREATE, APPEND);
        } catch (IOException e) {
            throw new FailTestException("Cannot write message to file: " + path.getFileName(), e);
        }
    }
}
