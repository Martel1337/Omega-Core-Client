package me.ByteCoder.Core.Connections.Secure;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;

public class SCryptor {

	public static Cipher getCipherObject(String type, String skey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException{
		Cipher cipher;
		Key key = new SecretKeySpec(skey.getBytes(), "DES");
		cipher = Cipher.getInstance("DES");
		if(type.contentEquals("encryption"))
		{
			cipher.init(Cipher.ENCRYPT_MODE, key);
		}
		else if(type.contentEquals("decryption")){
			cipher.init(Cipher.DECRYPT_MODE, key);
		}
		return cipher;
		
	}

	public static SealedObject encryptObject(Serializable o, String skey) throws InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, IOException{
		
		SealedObject sealed = new SealedObject(o, getCipherObject("encryption", skey));
		
		System.out.println("Object Encrypted");
		return sealed;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T decryptObject(SealedObject sealed, String skey) throws InvalidKeyException, ClassNotFoundException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, IOException {
		System.out.println("Object Decrypted");
		return (T) sealed.getObject(getCipherObject("encryption", skey));
	}
}
