package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommonAPI {
    public static  WebDriver driver = null;

    @BeforeMethod
    public void initializeTest(){
        String getOsNameFromSystem = System.getProperty("os.name");
        System.out.println("Opening the browser Chrome");
        if(getOsNameFromSystem.contains("Mac")){
            System.setProperty("webdriver.chrome.driver","../TechnicalA/driver/chromedriver");
        }else if(getOsNameFromSystem.contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", "../TechnicalA/driver/chromedriver.exe");
        }
        driver= new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(35,TimeUnit.SECONDS);
        driver.get("https://www.makemysushi.com/");
        //driver.manage().window().fullscreen();
    }
    @AfterMethod
    public void tearDownTest(){
        System.out.println("closing the browser chrome");
        driver.quit();
    }
    public void brokenLink() throws IOException {
        //Step:1-->Get the list of all the links and images
        List<WebElement> linkslist = driver.findElements(By.tagName("a"));
        linkslist.addAll(driver.findElements(By.tagName("img")));

        System.out.println("Total number of links and images--------->>> "+ linkslist.size());

        List<WebElement> activeLinks = new ArrayList<WebElement>();
        //Step:2-->Iterate linksList: exclude all links/images which does not have any href attribute
        for(int i=0; i<linkslist.size(); i++){
            //System.out.println(linkslist.get(i).getAttribute("href"));
            if(linkslist.get(i).getAttribute("href") !=null && ( ! linkslist.get(i).getAttribute("href").contains("javascript") && ( ! linkslist.get(i).getAttribute("href").contains("mailto")))){
                activeLinks.add(linkslist.get(i));
            }

        }
        System.out.println("Total number of active links and images-------->>> "+ activeLinks.size());

        //Step:3--> Check the href url, with http connection api
        for(int j=0; j<activeLinks.size(); j++){

            HttpURLConnection connection = (HttpURLConnection)new URL(activeLinks.get(j).getAttribute("href")).openConnection();

            connection.connect();
            String response = connection.getResponseMessage();
            connection.disconnect();
            System.out.println(activeLinks.get(j).getAttribute("href")+"--------->>> "+response);

        }

    }

}