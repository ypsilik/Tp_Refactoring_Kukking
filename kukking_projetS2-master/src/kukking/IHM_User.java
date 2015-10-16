package kukking;

import java.io.IOException;


/**
 * User interface
 */
interface IHM_User {
  /**
   * Ne s'effectue seulement si c'est un utilisateur qui est � la base de la recherche
   */
  int demandeNombrePersonne() ;

  String requestPassword() throws IOException;

  /**
   * Est r�alis�e � condition que le mot de passe renvoy� par l'utilisateur soit valide
   */
  void displayAdministrativePart() ;

  /**
   * Display all recipe's elements
 * @param RecipeToDisplay
 */
void displayElementRecipe(Recipe RecipeToDisplay) ;

  /**
   * affiche la liste des recettes (seulement les noms)
   */
  void afficheListeRecettes(Recipe listeRecettes) ;
}
