package kukking;

import java.io.IOException;


/**
 * Administrator interface
 */
interface IHM_Administrator {
  boolean ok(Recipe recipeToDelete) throws IOException ;

  /**
   * donne le formulaire qui permet d'ajouter les donn�es de la recette
   */
  Recipe formAddRecipe() throws IOException;
}
