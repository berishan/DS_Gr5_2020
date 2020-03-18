

public class Caesar {

public static String encrypt(String plainText, int key) {
	String cipherText="";
	for (int i=0;i<plainText.length();++i) {
		char ch=plainText.charAt(i);
		if (ch>='a' && ch<='z') {
			ch=(char)(ch+key);
			if (ch>'z') {
				ch=(char)(ch-'z'+'a'-1);
			}
			cipherText+=ch;
		}
		else if(ch>='A'&&ch<='Z') {
			ch=(char)(ch+key);
			if(ch>'Z') {
				ch=(char)(ch-'Z'+'A'-1);
			}
			cipherText+=ch;
		}
		else {
			cipherText+=ch;		
			}
	}
	return cipherText;
}



public static String decrypt(String cipherText, int key) {
	String dText="";
	for(int i=0;i<cipherText.length();i++) {
		char c=cipherText.charAt(i);
		if(c>='a'&&c<='z') {
			c=(char)(c-key);
			if(c<'a') {
				   c= (char)(c + 'z' - 'a' + 1);

			}
			dText+=c;
		}
		else if(c>='A' && c<= 'Z') {
			c= (char)(c-key);
			if(c<'A') {
				c=(char)(c+'Z'-'A'+1);
			}
			dText+=c;
		}
		else {
			dText+=c;
		}
	}
	return dText;
}



public static String bruteForce(String text) {
	
	
	for (int i=1;i<=26;i++) {
		System.out.println("celesi nr" + i + " "+ decrypt(text,i));
	}
	return text;
}




public static void main(String[] args) {
	
	
      if(args[2].equals("encrypt")) {
	   System.out.println(encrypt(args[4], Integer.parseInt(args[3])) );
		
	}else if (args[2].equals("decrypt")) {
		System.out.println(decrypt(args[4], Integer.parseInt(args[3])));
	}
	else if(args[2].equals("bruteForce")) {
		System.out.print(bruteForce(args[4]));
	}

}

}
