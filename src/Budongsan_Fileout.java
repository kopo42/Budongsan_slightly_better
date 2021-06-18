import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Budongsan_Fileout {

	public static void out_txt(String compstr) throws FileNotFoundException {
		FileOutputStream output = new FileOutputStream("C:\\Users\\J\\Desktop\\output\\HSCP.txt");
			try {
				output.write(compstr.getBytes());
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
