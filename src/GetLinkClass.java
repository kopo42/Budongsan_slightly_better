import java.util.ArrayList;

public class GetLinkClass {
	public static ArrayList<String> getHscpno (String pagedata) {
		ArrayList <String> list = new ArrayList();
		String TAG = "hscpno";
		String temp = "";
		String listSTR = "";
		String str = "";
		while(true) {
			try {
				pagedata = pagedata.substring(pagedata.indexOf(TAG));
				pagedata = pagedata.substring(TAG.length() + 2);
				temp = pagedata.substring(0, 20);
				
				if (temp.charAt(0) != '\"') {
					temp = temp.substring(0, temp.indexOf(" ") - 1);
					//list 위로 옮기기, 계속 추가
					list.add(temp);
				}
				pagedata = pagedata.substring(pagedata.indexOf(temp) + temp.length());
			}catch(Exception e) {
				break;
			}
		}
		//System.out.println(list.get(1));
		return list;
	}
}
