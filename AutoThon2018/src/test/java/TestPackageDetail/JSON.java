package TestPackageDetail;

import static com.jayway.restassured.RestAssured.given;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.jayway.restassured.response.Response;

public class JSON {

	public Properties readProp() throws IOException {
		InputStream input = null;
		Properties prop = new Properties();
		input = new FileInputStream("prop.properties");
		prop.load(input);
		return prop;
	}

	public static void main2(String arg[]) {

		Map<String, ArrayList<String>> postDataRow = new HashMap<String, ArrayList<String>>();

		ArrayList<String> test1 = new ArrayList<String>();
		test1.add("samsung1-lin1_lin2");
		test1.add("samsung2-lin1_lin2");

		ArrayList<String> test2 = new ArrayList<String>();
		test2.add("vivo1-lin1_lin2");
		test2.add("vivo2-lin1_lin2");

		// ArrayList<String> sample = new ArrayList<String>();

		postDataRow.put("thread3", test1);
		postDataRow.put("thread4", test2);

		Map<String, Map<String, ArrayList<String>>> postDataRow1 = new HashMap<String, Map<String, ArrayList<String>>>();
		postDataRow1.put("Hello", postDataRow);

		/*
		 * Map<String, Object> jsonAsMap = new HashMap<>(); Map<String, Object>
		 * jsonAsMap = new HashMap<>(); jsonAsMap.put("firstName", "Sumit");
		 * jsonAsMap.put("lastName", "Ghosh");
		 */

		given().contentType("application/json; charset=UTF-16").body(postDataRow1).when()
				.post("http://localhost:3000/posts");

	}

	public void get() {
		Response response = given().when().get("https://jsonplaceholder.typicode.com/posts/?id=" + "n3").then()
				.assertThat().statusCode(200).and().extract().response();
	}

	public static void main(String arg[]) {
		// [Patch-notchaning the structure, Put update allresource]
		HashMap<String, String> map = new HashMap<String, String>();
		// map.put("title","Learning-java");
		map.put("Name", "Sumit1");
		given().pathParam("id", 1).contentType("application/json").body(map).when()
				.patch("http://localhost:3000/posts/{id}").then().statusCode(200);
	}

	public void delete() {
		int response = given().when().get("http://localhost:3000/posts").then().assertThat().statusCode(200).and()
				.extract().jsonPath().getList("$").size();
		System.out.println(response);
		for (int i = 2; i <= response; i++) {
			given().pathParam("id", i).when().delete("http://localhost:3000/posts/{id}").then().statusCode(200);
			System.out.println("deleted");
		}
	}

	public dummyClass readMethod(Response response) throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		dummyClass data = new dummyClass();
		Object obj = parser.parse(response.asString());
		JSONArray array = (JSONArray) obj;
		JSONObject jsonObject = (JSONObject) array.get(0);
		String userId = jsonObject.get("userId").toString();
		data.setUserId(userId);
		String id = jsonObject.get("id").toString();
		data.setID(id);
		String job = (String) jsonObject.get("title");
		data.setTitle(job);
		String body = (String) jsonObject.get("body");
		data.setBody(body);
		return (data);

	}

	public static void main1(String arg[]) {
		int response = given().when().get("http://localhost:3000/posts").then().assertThat().statusCode(200).and()
				.extract().jsonPath().getList("$").size();
		System.out.println(response);
		for (int i = 2; i <= response; i++) {
			given().pathParam("id", i).when().delete("http://localhost:3000/posts/{id}").then().statusCode(200);
			System.out.println("deleted");
		}
	}
}
