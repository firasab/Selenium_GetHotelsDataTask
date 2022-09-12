import com.opencsv.CSVWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;



public class getHotelData {

    private static final String SAMPLE_CSV_FILE_PATH = "C:\\Users\\firas\\Desktop\\Selenium_GetHotelsDataTask\\src\\test\\java\\hotelData.csv";
    private static final String API = "";
    List<String> writeLinks = new ArrayList<>();



    public List<String> getData () {
        String url;
        boolean bool = false;

        List<String> links = new ArrayList<>();


        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ) {
            for (CSVRecord csvRecord : csvParser) {

                //to skip the first line in the Csv file bcs it's a tittle .
                if (!bool) {
                    bool = true;
                    continue;
                }

                // get the link from the csv file
                String p = csvRecord.get(1);

                //get the status code from the csv file
                String statusCode = csvRecord.get(2);

                String fin = " expedia";

                //to create a link from the web link and the additional link from the csv file
                url = p + statusCode + fin;
                links.add(url);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return links;
    }

    public static void writeDataLineByLine(String filePath, List<String> data  ){
        File file = new File(filePath);

        try{
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile);


            for(String line : data){
                writer.writeNext(new String[]{line});
            }
            writer.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Test
    public void testBing() throws InterruptedException {

        //open the Chrome browser
        WebDriver driver = OpenBrowserByPara.openBrowser("chrome");

        //go to ping website
        driver.get("https://www.bing.com/");

        //wait 10 sec
        Thread.sleep(10000);

        //read the csv file and put it in a list
        List<String> urls = getData();

        //print the list
        for(String s : urls){
            System.out.println(s);
        }


        //every time we open the ping page
        for( int i =0 ; i<urls.size(); i++){

            //make the code write in the search bar
            WebElement search = driver.findElement(By.name("q"));

            //write in the search bar
            search.sendKeys(urls.get(i));

            //create a search button clicker
            WebElement btn = driver.findElement(By.xpath("//*[@id=\"search_icon\"]"));
            btn.click();

            //make a list of the titles of the first page
            List<WebElement> results = driver.findElements(By.xpath("//*[@id=\"b_results\"]//*/h2/a"));
            for(WebElement result: results) {

                //find the links
                String stat = result.getAttribute("href");

                //check if the links had this strings
                boolean isFound = stat.contains("https://www.expedia.com");
                boolean isFound2 = stat.contains(".Hotel-Information");
                if ( isFound == true && isFound2 == true ){
                    System.out.println(stat);
                    writeLinks.add(stat);
                }else{
                    continue;
                }

            }
            driver.get("https://www.bing.com/");
            Thread.sleep(10000);


        }
        writeDataLineByLine("C:\\Users\\firas\\Desktop\\Selenium_GetHotelsDataTask\\src\\test\\java\\writeDataHotel.csv", writeLinks);
        driver.quit();


    }
}




