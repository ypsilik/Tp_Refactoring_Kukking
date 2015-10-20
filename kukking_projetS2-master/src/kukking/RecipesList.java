package kukking;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.Sheet;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * allow to manage list of receipts of application (delete, add ...)
 * @author RO
 *
 */
public class RecipesList {
	
	private Application application;
	
	public ArrayList <Recipe> list;

	/**
	 * Ne s'effectue que si l'utilisateur a valid� la suppression
	 * @throws IOException 
	 */
	public void deleteRecipe(Recipe recipeToDelete) throws IOException {
		if (application.getAdmin().validateDeletionRecipe(recipeToDelete))
			permanentlyDeleteRecipe(recipeToDelete);	
	}

	/**
	 * to delete recipe
	 * @param recipeToDelete
	 */
	public void permanentlyDeleteRecipe(Recipe recipeToDelete) {
		WritableWorkbook workbook = null;
		try {
			workbook = Workbook.createWorkbook(new File(Recipe.sourcePath),Workbook.getWorkbook(new File(Recipe.sourcePath)));
			
			for (int indexSheet=0;indexSheet<workbook.getNumberOfSheets();indexSheet++)
				{
					if (workbook.getSheet(recipeToDelete.getNameSheetRecipe())==workbook.getSheet(indexSheet))
					{
						workbook.removeSheet(indexSheet);
						break;
					}
				}
			workbook.write(); 
		}
		catch (BiffException | IOException e) {
			e.printStackTrace();
		}
		finally {
				closeWorkbook(workbook); 
		}
		this.list.remove(recipeToDelete);
	}

	private void closeWorkbook(WritableWorkbook workbook) {
		try {
			workbook.close();
		} 
		catch (WriteException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * to add recipe
	 * @param recipeToAdd
	 * @throws IOException
	 */
	public void addRecipe(Recipe recipeToAdd) throws IOException
	{
		this.list.add(recipeToAdd);
	}

	/**
	 * constructor to init receipts list or favoris list
	 * @param application
	 * @param favoris
	 */
	public RecipesList(Application application, boolean favoris) {
		this.application=application;
		this.list = new ArrayList<Recipe>();
		try {
			Workbook workbook = Workbook.getWorkbook(new File(Recipe.sourcePath));
			for (Sheet currentSheet: workbook.getSheets())
			{
				if (!favoris || currentSheet.getCell("D2").getContents().equals("Favoris"))
				{
					this.list.add(new Recipe(currentSheet.getName()));
				}
			}
		} catch (BiffException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * to get name of a recipe with the recipe (class)
	 * @param recipeToSearch
	 * @return
	 */
	public Recipe getRecipeWithName(String recipeToSearch)
	{
		for (Recipe recipe: list)
		{
			if (recipe.getNameRecipe().equals(recipeToSearch)) return recipe;
		}
		return null;
	}
}
