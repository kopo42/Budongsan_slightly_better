
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BudongsanSource  {
	public static WebDriver driver;
	public static WebElement element;
	

	public static void main(String[] args) throws IOException, InterruptedException {
		//driver.get(url);
		BudongsanSource budongsan = new BudongsanSource();
		
		getDistrict();
	}
	//class
	public BudongsanSource() {
		
		//System Property SetUp
		System.setProperty(BudongsanOptions.WEB_DRIVER_ID, BudongsanOptions.WEB_DRIVER_PATH);
		
		//Driver SetUp
		ChromeOptions options = new ChromeOptions();
		options.setCapability("ignoreProtectedModeSettings", true);
		driver = new ChromeDriver(options);
	}
	
	
	
	
	
	public static String APTinfos(ArrayList APTlist) throws ElementNotInteractableException, IOException, InterruptedException {
		String infosPerAPT = "";
		List<String> APTinfoList = new ArrayList<String>();
		String APTstr = "";
		String str = "";
		
		while(true) {
			try {
				for(int i = 0; i < APTlist.size(); i++) {
					String APTlink = "https://m.land.naver.com/complex/info/" + APTlist.get(i) + "?ptpNo=1";
					driver.get(APTlink);
					
					WebDriverWait wait = new WebDriverWait(driver, 5);
		            WebElement parent = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#_basic_content_cd > div.detail_summary_area._sumarea_cd > h1")));

					String APTname1 = driver.findElement(By.cssSelector("#_basic_content_cd > div.detail_summary_area._sumarea_cd > h1")).getText();
		               String APTsede = driver.findElement(By.cssSelector("#_basic_content_cd > div.detail_summary_area._sumarea_cd > div.detail_summary > ul > li:nth-child(1)")).getText();
		               APTsede = APTsede.replace("세대", "");
		               APTsede = APTsede.replace(",", "");
		               String APTdate = driver.findElement(By.cssSelector("#_basic_content_cd > div.detail_summary_area._sumarea_cd > div.detail_summary > ul > li:nth-child(3)")).getText();            
		               String APTmae = driver.findElement(By.cssSelector("#_basic_content_cd > div.detail_summary_area._sumarea_cd > div.complex_summary_info > div > div > div > dl:nth-child(1) > dd")).getText();
		               String [] APTmae1 = APTmae.split("~");
		               APTmae = APTmae1[0];
		               APTmae = APTmae.replace("억","");
		               APTmae = APTmae.replace(",","");
		               APTmae = APTmae.replace("-","0");
		               
		               String Juso = driver.findElement(By.cssSelector("#_basic_content_cd > article.article_box.article_box--detail._article_location_cd > div.item_area > p.p_address_place._addr")).getText();
		               Thread.sleep(2000);
		               
		               //facilities
		               driver.findElement(By.linkText("시설")).click();
		               Thread.sleep(2000);
		               
		               String sisuldata = driver.findElement(By.cssSelector("#nf_inner_cd > ul")).getText()+"\n";
		               
		               sisuldata = sisuldata.replace("m", "");
		               sisuldata = sisuldata.replace("\n", ",");
		               sisuldata = sisuldata.replace(" ", ",");
		               
		               infosPerAPT =  Juso + ","+ APTname1+","+ APTmae +"," + APTsede+","+APTdate+"," + sisuldata;
		               System.out.println(APTlist.get(i));
		               System.out.println(infosPerAPT);
		               System.out.println("===============================");
		               
		               Thread.sleep(2000);
		               APTinfoList.add(infosPerAPT);
					}
				
				APTstr = APTinfoList.toString();
				str = APTstr.replace("[", "").replace("]", "");
				
				return str;
				} catch (NoSuchElementException e) {
					System.out.println("NoSuchElementException");
				} catch(NoSuchSessionException e) {
					System.out.println("NoSuchSessionException");
				} catch(StringIndexOutOfBoundsException e) {
					System.out.println("StringIndexOutOfBoundsException");
				} catch(TimeoutException e) {
					System.out.println("Time out");
					driver.get(driver.getCurrentUrl());
					Thread.sleep(2000);
					
				}
			}
	}
	

}
	


