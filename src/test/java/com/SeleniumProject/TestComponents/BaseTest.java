package com.SeleniumProject.TestComponents;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.SeleniumProject.pageObjects.LandingPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public WebDriver driver;
	public LandingPage landingPage;
	private String userDataDir;
	private Properties prop;

	public WebDriver initializeDriver() throws IOException {
		prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ "//src//main//java//com//SeleniumProject//resources//GlobalData.properties");
		prop.load(fis);

		String browsername = prop.getProperty("browser");
		String headless = prop.getProperty("headless");

		if (browsername.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();

			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--disable-gpu");
			options.addArguments("--remote-allow-origins=*");

			// 🧠 Use headless if set to true in properties
			if (headless != null && headless.equalsIgnoreCase("true")) {
				options.addArguments("--headless=new");
			}

			// Avoid session conflicts in CI
			userDataDir = "/tmp/chrome-profile-" + UUID.randomUUID();
			Files.createDirectories(Path.of(userDataDir));
			options.addArguments("--user-data-dir=" + userDataDir);

			driver = new ChromeDriver();

		} else if (browsername.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		} else if (browsername.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		return driver;
	}

	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
	}

	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//Reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return file.getAbsolutePath();
	}

	@BeforeMethod(alwaysRun = true)
	public void launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}

		// Clean up the temporary Chrome profile
		if (userDataDir != null) {
			try {
				Path path = Path.of(userDataDir);
				if (Files.exists(path)) {
					Files.walk(path)
						.sorted((a, b) -> b.compareTo(a)) // delete child paths first
						.forEach(p -> {
							try {
								Files.delete(p);
							} catch (IOException e) {
								System.err.println("Failed to delete: " + p);
							}
						});
				}
			} catch (IOException e) {
				System.err.println("Failed to clean profile dir: " + userDataDir);
			}
		}
	}
}
