


/**
 * Test encryption and decryption of strings
 */
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.SecureIt;

public class SecureItTest {
  
  @Test
  public void testEncryptDecrypt()  {
    String originalString = "MySecretPassword";
    
    String encryptedString = SecureIt.encrypt(originalString);
    assertTrue(encryptedString.contentEquals("ZzPUIEU7TT+3D2fZnQ35za0T8AChT6jolTqxWwwgSQQ="));    
    
    String decryptedString = SecureIt.decrypt(encryptedString);
     
    assertFalse(encryptedString.equals(decryptedString));
    assertTrue(decryptedString.equals(originalString));
  }

}
