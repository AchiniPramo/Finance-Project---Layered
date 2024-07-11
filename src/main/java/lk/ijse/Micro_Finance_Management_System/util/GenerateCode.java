package lk.ijse.Micro_Finance_Management_System.util;

import java.util.Random;

public class GenerateCode {
    public static String generateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
