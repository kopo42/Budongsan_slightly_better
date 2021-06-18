import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class DongClass {
	public static ArrayList<String> getDong (String district_names_str, WebDriver driver) throws IOException, InterruptedException {
		ArrayList <String> APTnoList = new ArrayList();
		ArrayList<String> APTno = new ArrayList();
		GetLinkClass getlink = new GetLinkClass();
		
		int i = 1;
		int j = 1;
		
		while (true) {
			try {
				driver.findElement(By.linkText(district_names_str)).click();
				Thread.sleep(1000);
				
				while (i < 4) {
					String dongXpath = "/html/body/div[1]/div[1]/div[2]/div[2]/div[1]/section/div[1]/div[3]/div[2]/div/div/table/tbody/tr[" + j + "]/td[" + i + "]/a";
					driver.findElement(By.xpath(dongXpath)).click();
					Thread.sleep(500);
					
					String pagedata = driver.getPageSource();
					
					//if dong page contains zero complex data
					if (pagedata.contains("단지정보 없음")) {
						driver.navigate().back();
						Thread.sleep(500);
						i++;
						
						//move to next col or row from the table
						if (i > 3) {
							j++;
							i = 1;
						}
					} else {
					//get HSCP no. from page source of each dong
					APTno = getlink.getHscpno(pagedata);
					
					//add to APTnoList
					for (int k = 0; k < APTno.size(); k++) {
						APTnoList.add(APTno.get(k));
					}
					System.out.println("## Page Source Done");
					
					driver.navigate().back();
					Thread.sleep(500);
					i ++;
					//move to next col or row from the table
						if (i > 3) {
							j++;
							i = 1;
						}
					}
				}
			} catch (NoSuchElementException e) {
				System.out.println("###Next district");
				driver.navigate().back();
				Thread.sleep(2000);
				
				return APTnoList;
				
			} catch (ElementClickInterceptedException e) {
				driver.navigate().back();
				Thread.sleep(2000);
			} catch (ElementNotInteractableException e) {
				driver.navigate().back();
				Thread.sleep(2000);
				driver.findElement(By.linkText(district_names_str)).click();
				Thread.sleep(1000);
			}
		}
	}
}
