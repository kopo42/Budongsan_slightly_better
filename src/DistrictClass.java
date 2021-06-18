import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DistrictClass {
	public static void getDistrict(WebDriver driver) throws IOException, InterruptedException {
		DongClass Dong = new DongClass();
		Budongsan_Fileout Fileout = new Budongsan_Fileout();
		ArrayList <String> comp = new ArrayList();
		ArrayList <String> APTnoList = new ArrayList();
		String compstr = "";
		int i = 0;
		
		//enters land.naver.com
		driver.get(BudongsanOptions.url);
		
		Thread.sleep(4000);
		
		//select "지역별"
		System.out.println("지역명찾기");
		System.out.println("===============================");
		driver.findElement(By.xpath(BudongsanOptions.click_location)).click();
		
		Thread.sleep(4000);
		
		//select "Seoul"
		System.out.println("서울시");
		System.out.println("===============================");
		driver.findElement(By.linkText(BudongsanOptions.linktext_city)).click();
		
		Thread.sleep(1000);
		
		//find by link text "district names" (total 25 districts of Seoul city)
		while (i < 25) {
			try {
				String district_names_str = BudongsanOptions.District_NAMES[i];
				System.out.println(district_names_str);
				System.out.println("===============================");
				
				APTnoList = Dong.getDong(district_names_str, driver);
				
				for (int j = 0; j < APTnoList.size(); j++) {
					comp.add(APTnoList.get(j));
				}
				System.out.println("### District Done");
				
				Thread.sleep(1000);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			i++;
			if (i > 25) {
				break;
			}
		} 
		//final str into file
		
		compstr = comp.toString();
		/*
		 * district = compstr.replace("[", "").replace("]", ""); district =
		 * compstr.replace("\n", " ");
		 */
		
		//txt fileout
		Fileout.out_txt(compstr);
        driver.quit();
	}
}
