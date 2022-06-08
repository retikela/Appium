package test;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import Common.GenericFunctions;
import Common.Setup;
import io.appium.java_client.MobileElement;

public class TestCases extends Setup{
	GenericFunctions generic = new GenericFunctions();
	
	@Test(description = "Eneter keyword to perform search")
	public void Search() throws InterruptedException{
		String search = obj.getvalue("main_id_Search");
		generic.waitforId(search, 5);
		generic.clearText(generic.findElementById(search));
		generic.enterText(generic.findElementById("android:id/search_src_text"),"watch");
		generic.pressEnter();
		generic.waitforProgressbarDismiss();
		Assert.assertTrue(generic.waitforId("android:id/action_bar_title",1), "Unable to navigate to search list screen");
		sa.assertAll();
	}
	
	@Test(description = "Sort the list with low to hihg", dependsOnMethods = {"Search"})
	public void SortList() throws InterruptedException{
		String sort =obj.getvalue("SearchResults_id_Sortbtn");
		String sortoption = "Lowest Price + Shipping";
		generic.waitforId(sort, 2);
		generic.findElementById(sort).click();
		String option = "android.widget.CheckedTextView";
		generic.waitforClassName(option, 2);
		List<MobileElement> options = generic.findElementsByClassName(option);
		for (MobileElement object : options) {
			if(object.getText().equalsIgnoreCase(sortoption)) {
				object.click();
				break;
			}
		}
		sa.assertTrue(generic.waitforId(obj.getvalue("Search_id_Summary"), 3),"Summary is not displayed");
		generic.waitforProgressbarDismiss();
		sa.assertEquals(generic.findElementById(sort).findElementById("android:id/text1").getText(), sortoption,"selected sort options is not highlighted");
		Assert.assertTrue(generic.waitforId("android:id/action_bar_title",1), "Unable to navigate to search list screen");
		sa.assertAll();
	}
	
	@Test(description = "select first froduct from the sorted list",dependsOnMethods = {"Search"})
	public void Select1stProduct() throws InterruptedException{
 		String Results = generic.findElementById("android:id/action_bar_title").getText();
		int count = Integer.parseInt(Results.split(" ")[0].replaceAll(",", ""));
		if(count<0) {
			throw new SkipException("skipping the test case as the result is 0");
		}
		MobileElement productsGrid = generic.findElementById(obj.getvalue("SearchResults_id_productsGrid"));
		String productName = productsGrid.findElementsById("android:id/text1").get(0).getText();
		generic.findElementsById(obj.getvalue("SearchResults_id_price")).get(0).click();
		generic.waitforProgressbarDismiss();
		Assert.assertTrue(generic.waitforId(obj.getvalue("ProductDetails_id_watch"),1), "Unable to navigate to product details screen");
		sa.assertEquals(productName, generic.findElementById(obj.getvalue("ProductDetails_id_title")).getText(),"selected product details are not displayed" );
		sa.assertAll();
	}
	
	@Test(description = "click on watch and verify navigation to Sign in page", dependsOnMethods = {"Select1stProduct"})
	public void clickWatch() throws InterruptedException{
		generic.findElementById(obj.getvalue("ProductDetails_id_watch")).click();
		Assert.assertTrue(generic.waitforId(obj.getvalue("signin_id_button"),1), "Unable to navigate to Sign In screen");
	}
	
	@Test(description = "click on back button in Sign in page and verify user navigated to product details screen", dependsOnMethods = {"clickWatch"})
	public void clickBack() throws InterruptedException{
		generic.findElementById(obj.getvalue("signin_id_username_entry")).sendKeys("example");
		generic.findElementById("android:id/up").click();
		Assert.assertTrue(generic.waitforId(obj.getvalue("ProductDetails_id_watch"),1), "Unable to navigate to product details screen");
	}
	
	@Test(description = "To return products names and price based on the count",dependsOnMethods = {"clickBack"})
	public void NamePriceList() throws InterruptedException{
		generic.findElementById("android:id/up").click();
 		String Results = generic.findElementById("android:id/action_bar_title").getText();
		int count = Integer.parseInt(Results.split(" ")[0].replaceAll(",", ""));
		if(count<0) {
			throw new SkipException("skipping the test case as the result is 0");
		}
		int input = 20;
		List<String> products = productsPriceNames(input);
		System.out.println("list of products with name and price"+products);
		Assert.assertEquals(input, products.size()/2);
		System.out.println("count of products : "+products.size()/2);
	}
	
	
	List<MobileElement> ProductNames= new ArrayList<>();
	List<MobileElement> ProductPrice= new ArrayList<>();
	public List<String> productsPriceNames(int count) {
		MobileElement productsGrid = generic.findElementById(obj.getvalue("SearchResults_id_productsGrid"));
		int first=0;
		int second=0;
		ArrayList<String>Products=new ArrayList<>(); 
		do{
			ProductNames=productsGrid.findElementsById("android:id/text1");
			ProductPrice=productsGrid.findElementsById(obj.getvalue("SearchResults_id_price"));
//			if(ProductNames.size()==ProductPrice.size()) {
			try {
			 first=second;
			   for(int i=0; i<ProductNames.size();i++){
			   if(!Products.contains(ProductNames.get(i).getText())){
				   Products.add(ProductPrice.get(i).getText());
				   Products.add(ProductNames.get(i).getText());
			   }
			   if((count)==Products.size()/2) {
				   return Products;
			   }
			   }
			   second=Products.size();
			   generic.swipeUp();
			}
			catch (Exception e) {
				second=Products.size();
				generic.swipeUp();
			}
			}while(first!=second);
		return Products;
	}
	



}
