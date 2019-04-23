package stayohreorttest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
 
public class ReportStayoh {
	private String today;
	public static void main(String[] args) {
	
		ReportStayoh rs=new ReportStayoh();
		rs.reportdesc();

	}
	public void reportdesc()
	 {   System.setProperty("webdriver.chrome.driver", "C:\\SeleniumDriver\\chromedriver.exe");
		  WebDriver driver = new ChromeDriver();
	      driver.get("https://18.222.224.239/admin/login.html");
	      WebElement id = driver.findElement(By.name("username"));
	      WebElement pass = driver.findElement(By.name("password"));
	      WebElement button = driver.findElement(By.xpath("//*[@id=\"btn-login\"]\r\n" +""));         

	      id.sendKeys("qaauto");
	      pass.sendKeys("qaauto");
	      button.click();
	      
	      driver.navigate().to("https://18.222.224.239/admin/report.html");
	      
	      WebElement reportbar=driver.findElement(By.id("view_co_guest"));
	      reportbar.click();
	   
	     
	      
	  //   WebElement datestart=driver.findElement(By.id("start_date"));
	    Select stdate=new Select(driver.findElement(By.id("start_date"));
	    
	      datestart.click();
	    
	      WebElement dateend=driver.findElement(By.id("end_date"));
	    
	   dateend.click();
	     
	        
	      
	      
	      Select reportodgu = new Select(driver.findElement(By.id("analytics-type")));
	      reportodgu.selectByVisibleText("Orders");
	      reportodgu.selectByIndex(1);
	      WebElement button1=driver.findElement(By.id("submit_dates"));
	      button1.submit();
	      
	      

	 }
   }



