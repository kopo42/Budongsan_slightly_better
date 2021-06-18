import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BudongsanTXT {
	public static WebDriver driver;
	public static String url = "https://m.land.naver.com/index?focus=main";
	public static WebElement element;
	public static String urlForDistrict =  "https://m.land.naver.com/map/37.496437:127.077115:18/APT/A1:B1:B2:B3#regionStep1";
	//public static Document doc = null
	public static String[] NAMES = {"강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", 
			"도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", 
			"영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"};


	//Properties
	public static String WEB_DRIVER_ID = "webdriver.chrome.driver";
	public static String WEB_DRIVER_PATH = "C:\\Users\\J\\chromedriver_win32\\chromedriver.exe";

	public static void main(String[] args) throws IOException, ElementNotInteractableException, InterruptedException {
		BudongsanTXT budongsan = new BudongsanTXT();
		List<String> linkNum = new ArrayList<String>();
		linkNum = linkNumtxt();
		
		String APTstr = APTinfos(linkNum);
	}

	public static List<String> linkNumtxt () throws IOException {
		List <String> linkNum = new ArrayList<String>();
		//List<List<String>> linkNum = new ArrayList<List<String>>();
		File numbersTxt = new File("C:\\Users\\J\\Desktop\\output\\HSCPnumbers.txt");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(numbersTxt));
			Charset.forName("UTF-8");
			String line = ""; //null
			
			while((line=br.readLine()) != null) {
				String[] token = line.split(", ");
				for(int i = 0; i < token.length; i++) {
					linkNum.add(token[i]);
					//System.out.println(linkNum.get(i));
				}
			}
		} catch (FileNotFoundException e) {
		}
		return linkNum;
	}
	public static String APTinfos(List<String> linkNum) throws ElementNotInteractableException, IOException, InterruptedException {
		String infosPerAPT = "";
		String filename = "C:\\Users\\J\\Desktop\\output\\demo2.csv";
		
		List<String> APTinfoList = new ArrayList<String>();
		String APTstr = "";
		String str = "";
		int cnt = 0;
		
		while(true) {
			try {
				//파일명 바꾸기***
				for(int i = 100; i < linkNum.size() ; i++) {
					String APTlink = "https://m.land.naver.com/complex/info/" + linkNum.get(i) + "?ptpNo=1";
					driver.get(APTlink);
					Thread.sleep(3000);
					
					WebDriverWait wait = new WebDriverWait(driver, 30);
		            WebElement parent = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#_basic_content_cd > div.detail_summary_area._sumarea_cd > h1")));

					String APTname1 = driver.findElement(By.cssSelector("#_basic_content_cd > div.detail_summary_area._sumarea_cd > h1")).getText();
					Thread.sleep(200);
					
					String APTsede = driver.findElement(By.cssSelector("#_basic_content_cd > div.detail_summary_area._sumarea_cd > div.detail_summary > ul > li:nth-child(1)")).getText();
		            APTsede = APTsede.replace("세대", "");
		            APTsede = APTsede.replace(",", "");
		            Thread.sleep(200);
		           
		            String APTdate = driver.findElement(By.cssSelector("#_basic_content_cd > div.detail_summary_area._sumarea_cd > div.detail_summary > ul > li:nth-child(3)")).getText();            
		            Thread.sleep(200);
		            
		            String APTmae = driver.findElement(By.cssSelector("#_basic_content_cd > div.detail_summary_area._sumarea_cd > div.complex_summary_info > div > div > div > dl:nth-child(1) > dd")).getText();
		            String [] APTmae1 = APTmae.split("~");
		            Thread.sleep(1000);
		            APTmae = APTmae1[0];
		            APTmae = APTmae.replace("억","");
		            APTmae = APTmae.replace(",","");
		            APTmae = APTmae.replace("-","0");
		            Thread.sleep(1000);
		            
		            String Juso = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[5]/div/section/div/div/div[1]/article[4]/div[2]/p[1]")).getText();
		            Thread.sleep(500);
		            
		            //facilities
		            driver.findElement(By.linkText("시설")).click();
		            Thread.sleep(2000);

		            //link for Facilities article box
		            //driver.findElement(By.cssSelector("#_facil_trans_content_cd > article.article_box.article_box--detail._article_near_facility")).getText();
		            //driver.findElement(By.className("article_box article_box--detail _article_near_facility")).getText();
		            String sisul = driver.findElement(By.cssSelector("#nf_inner_cd > ul")).getText();
		            
		            sisul = sisul.replace(" ", ",");
		            sisul = sisul.replace("\n", ",");
		            sisul = sisul.replace("m", "");
		           
		            String newLine = System.lineSeparator();
		            
		            infosPerAPT =  Juso + ","+ APTname1+","+ APTmae +"," + APTsede+","+APTdate+"," + sisul;
		            System.out.println(linkNum.get(i));
		            System.out.println(infosPerAPT);
		            System.out.println("===============================");
		            Thread.sleep(2000);
		            
		            File file = new File(filename);
		            BufferedWriter bw = new BufferedWriter(new FileWriter(("C:\\Users\\J\\Desktop\\output\\demo2.csv"), true));
		            
		            bw.write(infosPerAPT);
		            bw.write(newLine);
		            
		            bw.flush();
		            bw.close();
		            
		            APTinfoList.add(infosPerAPT);
		            APTstr = APTinfoList.toString();
					}
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
				} finally {
					driver.quit();
				}
			}
	}

	
	public BudongsanTXT() {
		//System Property SetUp
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		
		//Driver SetUp
		ChromeOptions options = new ChromeOptions();
		options.setCapability("ignoreProtectedModeSettings", true);
		driver = new ChromeDriver(options);
	}
	
}
