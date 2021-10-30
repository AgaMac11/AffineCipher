public class AffineCipher {
    private int ainverse;
    private int f;


    public AffineCipher() {
    }

    public String encryption(String text, int a, int b) {

        StringBuilder cipheredText = new StringBuilder();
        char[] charArray = text.toCharArray();
        for (char c : charArray) {

            int charNumber = (int) c - 97;
            int cipherNumber = ((a * charNumber) + b) % 26;
            char cipheredChar = (char) (cipherNumber + 97);

            cipheredText.append(cipheredChar);
        }
        return cipheredText.toString();
    }

    public String decryption(String cipheredText, int a, int b) {
        StringBuilder decryptionText = new StringBuilder();
        ainverse = 0;
        char[] charArray = cipheredText.toCharArray();
        for (int i = 0; i < 26; i++) {
            f = (a * i) % 26;
            if (f == 1) {

                ainverse = i;
            }
        }
        for (char c : charArray) {
            int charNumber = (int) c - 97;
            int decryptionNumber = Math.floorMod((ainverse * (charNumber - b)), 26);
            char decryptionChar = (char) (decryptionNumber + 97);
            decryptionText.append(decryptionChar);
        }
        return decryptionText.toString();
    }
}

