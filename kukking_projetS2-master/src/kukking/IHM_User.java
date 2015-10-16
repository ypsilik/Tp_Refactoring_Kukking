package kukking;

import java.io.IOException;


/** User interface **/
interface IHM_User {
  
  // Ne s'effectue seulement si c'est un utilisateur qui est � la base de la recherche
  int demandeNombrePersonne() ;
  String requestPassword() throws IOException;

  /** Use if password OK **/
  void displayAdministrativePart() ;

  /** Display all recipe's elements
   * @param RecipeToDisplay **/
  void displayElementRecipe(Recipe RecipeToDisplay) ;

  /** Display recipe list (just name) **/
  void displayListRecipes(Recipe listRecipes) ;
}
