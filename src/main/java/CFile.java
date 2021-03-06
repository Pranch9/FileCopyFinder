import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//import org.apache.commons.codec.binary.Hex;

public class CFile {
	private String Name;
	private String MDSum;
	private String FWay;


	private String getFileChecksum(String filepath, MessageDigest md) throws IOException {
		// file hashing with DigestInputStream
//		try (DigestInputStream dis = new DigestInputStream(new FileInputStream(filepath), md)) {
//			while (dis.read() != -1) ; //empty loop to clear the data
//			md = dis.getMessageDigest();
//		}
//		// bytes to hex
//		StringBuilder result = new StringBuilder();
//		for (byte b : md.digest()) {
//			result.append(String.format("%02x", b));
//		}
//		return result.toString();
		// DigestInputStream is better, but you also can hash file like this.

		try (InputStream fis = new FileInputStream(filepath)) {
			byte[] buffer = new byte[1024];
			int nread;
			while ((nread = fis.read(buffer)) != -1) {
				md.update(buffer, 0, nread);
			}
		}

		// bytes to hex
		StringBuilder result = new StringBuilder();
		for (byte b : md.digest()) {
			result.append(String.format("%02x", b));
		}
		return result.toString();
	}

	public CFile(String Name) throws NoSuchAlgorithmException, IOException {
		for (int i = Name.length() - 1; i > 0; i--) {
			if (Name.charAt(i) == '\\') {
				this.FWay = Name.substring(0, i + 1);
				break;
			}
		}
		this.Name = Name;
//		String fl=FWay+"\\"+Name;
		MessageDigest md = MessageDigest.getInstance("MD5");
		MDSum = getFileChecksum(Name, md);
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getMDSum() {
		return MDSum;
	}

	public void setMDSum(String MDSum) {
		this.MDSum = MDSum;
	}

	public String getFWay() {
		return FWay;
	}

	public void setFWay(String FWay) {
		this.FWay = FWay;
	}
}