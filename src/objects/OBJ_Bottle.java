package objects;

import entity.Player;
import interact.Interactable;

public class OBJ_Bottle extends ParentObject implements Interactable {

    public OBJ_Bottle() {
        name = "OBJ_Key";
    }

    @Override
    public void interact(Player p) {
        p.luck++;
        p.gp.ui.showMessage("Luck+");
    }
}
