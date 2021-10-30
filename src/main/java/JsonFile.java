import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Object;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonFile implements JsonOperation {

    private long a;
    private long b;
    private String plainText;
    private String cryptogram;
    private String typeOfOperation;
    private int key1;
    private int key2;
    private static final String jsonFile = "src/jsonfiles/input_file.json";
    private static final String fileName = "src/jsonfiles/output_file";
    private static final String ext = ".json";

    public void jsonReader() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(jsonFile)) {
            Object object = jsonParser.parse(reader);
            JSONObject inputObject = (JSONObject) object;
            a = (long) inputObject.get("key1");
            b = (long) inputObject.get("key2");
            typeOfOperation = inputObject.get("operation").toString();
            if (typeOfOperation.equals(Operation.encrypt.name())) {
                plainText = inputObject.get("plainText").toString();
            } else if (typeOfOperation.equals(Operation.decrypt.name())) {
                cryptogram = inputObject.get("cryptogram").toString();
            }
            key1 = (int) a;
            key2 = (int) b;
            keysVerify(key1, key2);
            if (!keysVerify(key1, key2)) {
                System.out.println("Podana para kluczy jest niepoprawna");
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public boolean keysVerify(int a, int b) {
        Divisor divisor = new Divisor();
        return b >= 1 && b <= 26 && a >= 1 && a <= 26 && divisor.greatestCommonDivisor(a) == 1;
    }

    public void jsonWriter() {

        JSONObject jsonOutputObject = new JSONObject();
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        String formatDateTime = localDateTime.format(dateTimeFormatter);
        File jsonOutputFile = new File(fileName + formatDateTime + ext);
        try {
            FileWriter fileWriter = new FileWriter(jsonOutputFile);
            if (typeOfOperation.equals(Operation.encrypt.name())) {
                encrypt();
            } else if (typeOfOperation.equals(Operation.decrypt.name())) {
                decrypt();
            }
            jsonOutput(jsonOutputObject);
            fileWriter.write(jsonOutputObject.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void jsonOutput(JSONObject jsonOutputObject) {
        jsonOutputObject.put("a", a);
        jsonOutputObject.put("b", b);
        jsonOutputObject.put("operation", typeOfOperation);
        jsonOutputObject.put("plainText", plainText);
        jsonOutputObject.put("cryptogram", cryptogram);

    }

    public void encrypt() {
        AffineCipher affineCipher = new AffineCipher();
        cryptogram = affineCipher.encryption(plainText, key1, key2);
    }

    public void decrypt() {
        AffineCipher affineCipher = new AffineCipher();
        plainText = affineCipher.decryption(cryptogram, key1, key2);
    }
}
