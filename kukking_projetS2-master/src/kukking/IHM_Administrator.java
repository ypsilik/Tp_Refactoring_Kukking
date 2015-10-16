package kukking;

import java.io.IOException;


/** Administrator interface **/
interface IHM_Administrator {
	boolean validateDeletionRecipe(Recipe recipeToDelete) throws IOException ;

	/** give form to permit to add recipe data **/
	Recipe formAddRecipe() throws IOException;
}
