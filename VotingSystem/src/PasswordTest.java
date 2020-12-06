import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * Class for calculating passwords for auditors.
 */
public class PasswordTest {
    /**
     * Method which will take in a command line string and convert it into an auditor password.
     * @param args
     * @throws UnsupportedEncodingException
     */
    public static void main(String args[]) throws UnsupportedEncodingException {
        Scanner scanner = new Scanner(System.in);
        String a = scanner.nextLine();

        byte[] bytes = a.getBytes("US-ASCII");
        byte[] nArray = new byte[]{1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3};
        byte[] bytes2 = new byte[a.length()];
        for(int i = 0; i < bytes.length;i++){
            bytes2[i] = (byte) (bytes[i]+nArray[i]);
        }
        String password ="";
        for(int i: bytes2) {
            password += Character.toString((char) i);
        }

        System.out.println(password);
    }
}
