package kukking;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.write.Label;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import kukking.IHM.KukkingDisplay;


/**
 * principal class which init IHM and has list of receipts and favoris
 * @author RO
 *
 */
public class Application {
	
	public final static String[] logins = {"Martin","Laure","Robin","Maud","Alexandra"};
	public final static String[] passwords = {"AM","CA","EX","LA","LI"};
	
	private boolean accesAdmin;
	private IHM_User user;
	private IHM_Administrator admin;
	private KukkingDisplay kukking;
	private RecipesList listFavoris;
	private RecipesList recipesList;
	
	
	/** to get list favoris
	 * @return listFavoris **/
	public RecipesList getListe_Favoris() {
		return listFavoris;
	}
	
	/** to set list favoris
	 * @param listFavoris **/
	public void setListe_Favoris(RecipesList listFavoris) {
		this.listFavoris = listFavoris;
	}
	
	
	/** to get recipeslist
	 * @return recipesList **/
	public RecipesList getRecipesList() {
		return recipesList;
	}


	/** to know if user is connect in mode administrator
	 * @return accesAdmin **/
	public boolean isAccesAdmin() {
		return accesAdmin;
	}

	/**
	 * constructor application which call several init
	 * @throws RowsExceededException
	 * @throws WriteException
	 * @throws IndexOutOfBoundsException
	 */
	public Application() throws RowsExceededException, WriteException, IndexOutOfBoundsException
	{	
		this.recipesList = new RecipesList(this, false);
		this.listFavoris = new RecipesList(this, true);
		this.kukking = new KukkingDisplay(this);
		
		this.kukking.setLocationRelativeTo(null);
		this.kukking.setVisible(true);
		this.initFileRecipes();	
	}

	/**
	 * to avoid bug about get preparation
	 * @throws IndexOutOfBoundsException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private void initFileRecipes() throws RowsExceededException, WriteException, IndexOutOfBoundsException
	{
		WritableWorkbook workbook = null;
		try {
			workbook = Workbook.createWorkbook(new File(Recipe.sourcePath),Workbook.getWorkbook(new File(Recipe.sourcePath)));
			int nbSheet = workbook.getNumberOfSheets();
			for (int numSheet=0; numSheet<nbSheet; numSheet++)
			{
				Label secure = new Label(0,workbook.getSheet(numSheet).getRows(),"");
				workbook.getSheet(numSheet).addCell(secure);
			}
			
			workbook.write(); 
		} 
		
		catch (IOException | BiffException e) {
			e.printStackTrace();
		}
		
		finally {
				/* On ferme le worbook pour lib�rer la m�moire */
			try {
				workbook.close();
			} 
			catch (WriteException | IOException e) {					
				e.printStackTrace();
			}
		}
	}

	/**
	 * to delete a favori receipt and update list of favoris
	 * @param recipeToDelete
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void deleteUpdateListFavori(Recipe recipeToDelete) throws RowsExceededException, WriteException
	{
		recipeToDelete.deleteFavoris();
		this.listFavoris.list.remove(recipeToDelete);
	}

	/**
	 * to add a favori recipe and update list of favoris
	 * @param recipeToAdd
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void addUpdateListFavori(Recipe recipeToAdd) throws RowsExceededException, WriteException
	{
		recipeToAdd.setFavoris();
		this.listFavoris.list.add(recipeToAdd);
	}

	/** display recipe to console
	 * @param recipeToDisplay **/
	public void displayRecipe(Recipe recipeToDisplay) {
		user.displayElementRecipe(recipeToDisplay);
	}

	// ne s'effectue que si on n'est pas � la premi�re page
	public void previousPage() {
	}
	
	// ne s'effectue que si on n'est pas � la derni�re page.
	public void nextPage() {
	}

	
	/**
	 * to search receipts with parameters
	 * @param timePrepaMax
	 * @param typeCook
	 * @param typePlat
	 * @param cout
	 * @return listWellRecipes
	 */
	public ArrayList<Recipe> searchRecipes(int timePrepaMax, String typeCook, String typePlat, String cout)
	{
		ArrayList<Recipe> listWellRecipes = new ArrayList<Recipe>();
		for (Recipe currentRecipe: recipesList.list)
		{
			if (timePrepaMax >= currentRecipe.getPreparationTime())
			{
				boolean typeCookOk = false;
				boolean typePlatOk = false;
				for (String categ: currentRecipe.getCategories())
				{
					if (categ.equals(typeCook)) typeCookOk = true;
					if (categ.equals(typePlat)) typePlatOk = true;
				}
				if (typeCook.equals("Tous type de recettes") || typeCookOk)
				{
					if (typePlat.equals("Tous les plats") || typePlatOk)
					{
						if (cout.equals("Variable") || cout.equals(currentRecipe.getCost()))
						{
							listWellRecipes.add(currentRecipe);
						}			
					}
				}
			}
		}
		return listWellRecipes;
	}

	/**boolean whose return valid or not password give in parameter **/
	public boolean toConnectInAdmin(String login, String password) {
		int numLogin = 0;
		for (String currentLogin:logins)
		{
			if (login.equals(currentLogin)&&password.equals(passwords[numLogin]))
			{
				accesAdmin = true;
				return true;
			}
			numLogin++;
		}
		return false;
	}

	/** to get IHM_Administrator
	 * @return admin **/
	public IHM_Administrator getAdmin() {
		return admin;
	}
}
