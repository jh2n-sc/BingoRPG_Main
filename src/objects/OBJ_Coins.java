package objects;
import interact.Interactable;
import entity.Player;
public class OBJ_Coins extends ParentObject implements Interactable {

    public OBJ_Coins() {
        name = "OBJ_Coins";
    }

    @Override
    public void interact(Player p) {
        p.coins++;
        p.gp.ui.showMessage("You've obtained a coin!");
    }
}
