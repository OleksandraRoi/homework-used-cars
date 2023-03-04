import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.v85.dom.model.ShapeOutsideInfo;
import org.openqa.selenium.support.ui.Select;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Cars {


    @Test
    public void usedCars() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("http://cargurus.com");

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.findElement(By.xpath("//label[@for='heroSearch-tab-1']")).click();

        String defaultMakes = new Select(driver.findElement(By.id("carPickerUsed_makerSelect"))).getFirstSelectedOption().getText();
        Assert.assertEquals(defaultMakes, "All Makes");

        new Select(driver.findElement(By.id("carPickerUsed_makerSelect"))).selectByValue("m34");

        String defaultModels = new Select(driver.findElement(By.id("carPickerUsed_modelSelect"))).getFirstSelectedOption().getText();
        Assert.assertEquals(defaultModels, "All Models");

        List<String> expectedModels = List.of("All Models","Aventador","Gallardo","Huracan","Urus",
                "400GT","Centenario","Countach","Diablo","Espada","Murcielago");
       List <WebElement> options = new Select(driver.findElement(By.id("carPickerUsed_modelSelect"))).getOptions();

       List <String> actualModels = new ArrayList<>();
       for(WebElement option : options){
           actualModels.add(option.getText());
       }

       Assert.assertEquals(expectedModels,actualModels);

        new Select(driver.findElement(By.id("carPickerUsed_modelSelect"))).selectByValue("d255");
        driver.findElement(By.name("zip")).sendKeys("22031", Keys.ENTER);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,300)");

        String title = driver.findElement(By.xpath("//h4[@title]")).getText();
        Assert.assertTrue(title.contains("Lamborghini Gallardo"));
        System.out.println(title);

        new Select(driver.findElement(By.id("sort-listing"))).selectByValue("PRICE_ASC");
        Thread.sleep(1000);
        List<WebElement> price = driver.findElements(By.xpath("//span[@class='JzvPHo']"));
        List<Integer> priceLowToHigh = new ArrayList<>();

        for(WebElement element : price){
              priceLowToHigh.add(Integer.parseInt(element.getText().substring(0,6).replaceAll("[$,]", "")));
        }

        List<Integer> copyPrice = new ArrayList<>(priceLowToHigh);
        Collections.sort(copyPrice);
        Assert.assertEquals(priceLowToHigh, copyPrice);

        new Select(driver.findElement(By.id("sort-listing"))).selectByValue("MILEAGE_DESC");
        Thread.sleep(1000);
        List<WebElement> mile = driver.findElements(By.xpath("//p[@class='JKzfU4 umcYBP']"));
        List<Integer> mileHighest = new ArrayList<>();

        for(WebElement element : mile){
            String text = element.getText();
            mileHighest.add(Integer.parseInt(text.substring(0,text.length()-3).replace(",", "")));
        }
        List<Integer> copyMile = new ArrayList<>(mileHighest);
        Collections.sort(copyMile, Collections.reverseOrder());
        Assert.assertEquals(mileHighest, copyMile);

        driver.findElement(By.xpath("(//p[@class='y0gbTE'])[2]")).click();
        Thread.sleep(1000);
        String st = driver.findElement(By.xpath("//div[@class='wFZMAc']")).getText();
        Assert.assertTrue(st.contains("Coupe AWD"));

        List<WebElement> results = driver.findElements(By.xpath("//div[@class='wFZMAc']"));
        int numOfResults = results.size();
        results.get(numOfResults -1).click();

        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[@class='r1inOn']")).click();
        Thread.sleep(1000);
        WebElement viewed = driver.findElement(By.xpath("//div[@class='HWinWE x1gK4I']"));
        Assert.assertEquals(viewed.getText(), "Viewed");
    }
}
