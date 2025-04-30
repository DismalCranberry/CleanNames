import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Pattern;

public class StripCodesFromFile {

    private static final Pattern STRIP_TRAILING_CODE_PATTERN = Pattern.compile("[\\s.]+[A-Za-z0-9]+(?:\\s*[/-]\\s*[A-Za-z0-9]+)*(?:\\s+[xх]\\d+)?$");

    public static String stripTrailingCode(String line) {
        return STRIP_TRAILING_CODE_PATTERN.matcher(line).replaceAll("");
    }

    public static void main(String[] args) {

        File inputFile = new File("products.txt");
        File outputFile = new File("cleaned.txt");

        if (!inputFile.exists()) {
            System.err.println("Input file not found: " + inputFile);
            System.exit(2);
        }

        try (BufferedReader reader = Files.newBufferedReader(inputFile.toPath()); BufferedWriter writer = Files.newBufferedWriter(outputFile.toPath())) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                writer.write(stripTrailingCode(line));
                writer.newLine();
            }
            System.out.println("Processing complete. Cleaned names written to " + outputFile);
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            System.exit(3);
        }
    }
}
