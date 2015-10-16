package kukking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * old class (when the beginning of application was test in console
 * @author RO
 *
 */
public class AdministratorConsole implements IHM_Administrator {

	@Override
	public boolean ok(Recipe recipeToDelete) throws IOException {
		System.out.println("Etes vous s�r de vouloir supprimer la recette :"+ recipeToDelete.getNameRecipe());
		BufferedReader entree = new BufferedReader (new InputStreamReader(System.in));
	    if (entree.readLine()=="0") return true;
		return false;
	}

	@Override
	public Recipe formAddRecipe() throws IOException {
		System.out.println("Titre de la recette ?");
		BufferedReader entree = new BufferedReader (new InputStreamReader(System.in));
		String title = entree.readLine();
		
		System.out.println("Nombre de personnes ?");
		int nbPers = Integer.parseInt(entree.readLine());
		
		System.out.println("Temps de pr�paration (en min) ?");
		int preparationTime = Integer.parseInt(entree.readLine());
		
		System.out.println("Temps de cuisson total (en min) ?");
		int cookingTime = Integer.parseInt(entree.readLine());
		
		System.out.println("Co�t ?");
		String cost = entree.readLine();
		
		System.out.println("Cat�gories ? (entrez une cat�gorie � la fois, puis rappuyez quand vous avez fini)");
		ArrayList <String> categories = new ArrayList <String>();
		String category = entree.readLine();
		while (!category.equals(""))
		{
			categories.add(category);
			category = entree.readLine();			
		}
		
		System.out.println("Ingr�dients ? (De m�me que pour les cat�gories, en renseignant la quantit�)\nNom :");
		ArrayList <String> ingredients = new ArrayList <String>();
		String ingredient = entree.readLine();
		System.out.println("Quantit� :");
		ArrayList <String> quantities = new ArrayList <String>();
		String quantity = entree.readLine();
		System.out.println("Unit� :");
		ArrayList <String> units = new ArrayList <String>();
		String unit = entree.readLine();
		while (!ingredient.equals(""))
		{
			ingredients.add(ingredient);
			quantities.add(quantity);
			units.add(unit);
			System.out.println("Nom :");
			ingredient = entree.readLine();
			System.out.println("Quantit� :");
			quantity = entree.readLine();
			System.out.println("Unit� :");
			unit = entree.readLine();
		}
		
		System.out.println("Pr�paration ? (De m�me que pour les cat�gories, entrez �tape par �tape)");
		ArrayList <String> preparation = new ArrayList <String>();
		String step = entree.readLine();
		while (!step.equals(""))
		{
			preparation.add(step);
			step = entree.readLine();
		}
		
		return new Recipe(title, nbPers, preparationTime, cookingTime, cost, categories, ingredients, quantities, units, preparation);
	}

}
