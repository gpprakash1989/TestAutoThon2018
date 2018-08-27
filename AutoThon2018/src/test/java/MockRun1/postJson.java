package MockRun1;

import static com.jayway.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class postJson {
	@Test
	public void test() throws MalformedURLException, InterruptedException {
		Map<String, ArrayList<String>> postDataRow = new HashMap<String, ArrayList<String>>();	
		
		ArrayList<String> test1 = new ArrayList<String>();
		test1.add("samsung1-lin1_lin2");
		test1.add("samsung2-lin1_lin2");

		ArrayList<String> test2 = new ArrayList<String>();
		test2.add("vivo1-lin1_lin2");
		test2.add("vivo2-lin1_lin2");

		postDataRow.put("thread3", test1);
		postDataRow.put("thread4", test2);
		
		Map<String, Object> jsonAsMap = new HashMap<>();
		Iterator it = postDataRow.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			String ThreadID = (String) pair.getKey();
			jsonAsMap.put("ThreadID", ThreadID);

			ArrayList<String> listOfMobileInfo = (ArrayList<String>) pair.getValue();
			int i = 1;
			for (String rowDetail : listOfMobileInfo) {
				String MobileName = rowDetail.split("-")[0];
				String Description = rowDetail.split("-")[1].replaceAll("_", ",");
				jsonAsMap.put("MobileName" + i, MobileName);
				jsonAsMap.put("Description" + i, Description);
				i++;
			}
			given().contentType("application/json; charset=UTF-16").body(jsonAsMap).when()
					.post("http://localhost:3000/posts");
		}
	}

}
