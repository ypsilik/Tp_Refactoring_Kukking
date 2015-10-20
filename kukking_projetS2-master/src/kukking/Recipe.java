package kukking;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * class to get every elements recipe and write in (to favoris)
 * @author RO
 */
public class Recipe {
	public final static String sourcePath = "./recipes.xls";
	private String nameSheetRecipe;	
	private WritableWorkbook workbook = null;
	
	/** get name of the recipe
	 * @return nameSheetRecipe **/
	public String getNameSheetRecipe() {
		return nameSheetRecipe;
	}

	/** to set recipe of favoris
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void setFavoris () throws RowsExceededException, WriteException {
		updateFavoris(new Label(3, 1, "Favoris"));
	}
	
	/** to delete recipe of favoris
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void deleteFavoris () throws RowsExceededException, WriteException {
		updateFavoris(new Label(3, 1, ""));
	}

	/** update favoris
	 * @param labelFavori
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	private void updateFavoris(Label labelFavori) throws WriteException, RowsExceededException
	{
		try {
			this.workbook = Workbook.createWorkbook(new File(sourcePath),Workbook.getWorkbook(new File(sourcePath)));
			WritableSheet recipe = this.workbook.getSheet(this.nameSheetRecipe);
			recipe.addCell(labelFavori);
			this.workbook.write();
		} catch (IOException e) {e.printStackTrace();} catch (BiffException e) {e.printStackTrace();}
		finally {
			closeWorkbook();
		}
	}

	/**
	 * to get recipe name
	 * @return recipe name
	 */
	public String getNameRecipe() {
		try {
			Workbook workbook = Workbook.getWorkbook(new File(sourcePath));
			Sheet recipe = workbook.getSheet(this.nameSheetRecipe);
			return recipe.getCell("D1").getContents();
		} catch (BiffException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();} 
		return nameSheetRecipe;
	}
	
	/** @return quantity of person **/
	public int getNbPers() {
		return getDigitalDataRecipe("F3");
	}
	/** @return preparation time **/
	public int getPreparationTime() {
		return getDigitalDataRecipe("D1");
	}

	/** get digital data in a recipe
	 * @param cellNum
	 * @return digital data or 0
	 */
	private int getDigitalDataRecipe(String cellNum)
	{
		try {
			Workbook workbook = Workbook.getWorkbook(new File(sourcePath));
			Sheet recipe = workbook.getSheet(this.nameSheetRecipe);
			return Integer.parseInt(recipe.getCell(cellNum).getContents());
		}
		catch (BiffException |IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/** @return cooking time **/
	public int getCookingTime() {
		try {
			Workbook workbook = Workbook.getWorkbook(new File(sourcePath));
			Sheet recipe = workbook.getSheet(this.nameSheetRecipe);
			if (!recipe.getCell("F6").getContents().equals(""))
			{
				return Integer.parseInt(recipe.getCell("F6").getContents());
			}
		}
		catch (BiffException | IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/** @return recipe's cost **/
	public String getCost() {
		try {
			Workbook workbook = Workbook.getWorkbook(new File(sourcePath));
			Sheet recipe = workbook.getSheet(this.nameSheetRecipe);
			if (!recipe.getCell("F8").getContents().equals("")) return recipe.getCell("F8").getContents();
		}
		catch (BiffException | IOException e) {
			e.printStackTrace();
		}
		return "aucun";
	}

	/** @return categories list **/
	public ArrayList<String> getCategories() {
		ArrayList <String> categories= new ArrayList<String>();
		try {
			Workbook workbook = Workbook.getWorkbook(new File(sourcePath));
			Sheet recipe = workbook.getSheet(this.nameSheetRecipe);
			int numRow = 5;
			while (!recipe.getCell(3, numRow).getContents().equals("")){
				categories.add(recipe.getCell(3, numRow).getContents());
				numRow ++;
			}
		}
		catch (BiffException | IOException e) {
			e.printStackTrace();
		}
		return categories;
	}

	/** @return ingredients list **/
	public ArrayList<String> getIngredients() {
		ArrayList <String> ingredients= new ArrayList<String>();
		try {
			Workbook workbook = Workbook.getWorkbook(new File(sourcePath));
			Sheet recipe = workbook.getSheet(this.nameSheetRecipe);
			int numRow = 5;
			while (!recipe.getCell(0, numRow).getContents().equals("")){
				ingredients.add(recipe.getCell(0, numRow).getContents());
				numRow ++;
			}
		} 
		catch (BiffException | IOException e) {
			e.printStackTrace();
		}
		return ingredients;
	}

	/** @return quantities list **/
	public ArrayList<String> getQuantities() {
		ArrayList <String> quantities= new ArrayList<String>();
		try {
			Workbook workbook = Workbook.getWorkbook(new File(sourcePath));
			Sheet recipe = workbook.getSheet(this.nameSheetRecipe);
			for (int numRow=5;numRow<this.getIngredients().size()+5;numRow++){
				quantities.add(recipe.getCell(1, numRow).getContents());
			}
		} 
		catch (BiffException | IOException e) {
			e.printStackTrace();
		}
		return quantities;
	}

	/** @return units list **/
	public ArrayList<String> getUnits() {
		ArrayList <String> units= new ArrayList<String>();
		try {
			Workbook workbook = Workbook.getWorkbook(new File(sourcePath));
			Sheet recipe = workbook.getSheet(this.nameSheetRecipe);
			for (int numRow=5;numRow<this.getIngredients().size()+5;numRow++){
				units.add(recipe.getCell(2, numRow).getContents());
			}
		} 
		catch (BiffException | IOException e) {
			e.printStackTrace();
		}  
		return units;
	}

	/** @return preparation's steps **/
	public ArrayList<String> getPreparation() {
		ArrayList <String> preparation= new ArrayList<String>();
		try {
			Workbook workbook = Workbook.getWorkbook(new File(sourcePath));
			Sheet recipe = workbook.getSheet(this.nameSheetRecipe);
			int numRow = 15;
			while (!recipe.getCell(4, numRow).getContents().equals("")){
				preparation.add(recipe.getCell(4, numRow).getContents());
				numRow ++;
			}
		} catch (BiffException | IOException e) {
			e.printStackTrace();
		} 
		return preparation;
	}

	/**
	 * constructor to the application's launcher to create receipts from already existing receipts in file
	 * @param nameRecipe
	 */
	public Recipe(String nameRecipe){
		this.nameSheetRecipe=nameRecipe;
	}
	
	/**
	 * constructor when we create a new recipe
	 * @param title
	 * @param nbPers
	 * @param preparationTime
	 * @param cookingTime
	 * @param cost
	 * @param categories
	 * @param ingredients
	 * @param quantities
	 * @param units
	 * @param preparation
	 */
	public Recipe(String title, int nbPers, int preparationTime, int cookingTime, String cost, ArrayList<String> categories, ArrayList<String> ingredients, ArrayList<String> quantities, ArrayList<String> units, ArrayList<String> preparation)
	{
		this.nameSheetRecipe = title;
		try {
			this.workbook = Workbook.createWorkbook(new File(sourcePath),Workbook.getWorkbook(new File(sourcePath)));

			WritableSheet recipe = this.workbook.createSheet(title, 0); 

			Label label = new Label(3,0, title);
			recipe.addCell(label);
			label = new Label(5, 7, cost);
			recipe.addCell(label);
			
			addCellOneCase(2,nbPers, recipe);
			addCellOneCase(4,preparationTime, recipe);
			addCellOneCase(5,cookingTime, recipe);

			addGroupsCell(3,categories, recipe);
			addGroupsCell(0,ingredients, recipe);
			addGroupsCell(1,quantities, recipe);
			addGroupsCell(2,units, recipe);
			
			int indexEndPreparation = 15;
			for (int stepNum=0;stepNum<preparation.size();stepNum++)
			{
				label = new Label(4, 15+stepNum, preparation.get(stepNum));
				recipe.addCell(label);
				indexEndPreparation = 16+stepNum;
			}
			label = new Label(4, indexEndPreparation, "");
			recipe.addCell(label);
			
			this.workbook.write(); 

		} 
		catch (IOException | RowsExceededException e) {
			e.printStackTrace();
		} 
		catch (WriteException | BiffException e) {
			e.printStackTrace();
		}
		finally {
				closeWorkbook(); 
		}
	}

	private void closeWorkbook() {
		try {
			this.workbook.close();
		} 
		catch (WriteException | IOException e) {
			e.printStackTrace();
		}
	}

	private void addCellOneCase(int column,int RecipeOnbject, WritableSheet recipe) throws WriteException, RowsExceededException {
		Number number = new Number(5, column, RecipeOnbject);
		recipe.addCell(number);
	}

	private void addGroupsCell(int numLabel,ArrayList<String> typeCategories,WritableSheet recipe) throws WriteException, RowsExceededException {
		Label label;
		for (int categorieNum=0;categorieNum<typeCategories.size();categorieNum++)
		{
			label = new Label(numLabel, 5+categorieNum, typeCategories.get(categorieNum));
			recipe.addCell(label);
		}
	}

	/** to know if the recipe is favoris
	 * @return true or false
	 */
	public boolean isFavoris()
	{
		try {
			Workbook workbook = Workbook.getWorkbook(new File(sourcePath));
			Sheet recipe = workbook.getSheet(this.nameSheetRecipe);
			if (recipe.getCell("D2").getContents().equals("Favoris")) return true;
		} 
		catch (BiffException | IOException e) {
			e.printStackTrace();
		} 
		return false;
	}
}
