package com.hilldev.hill60.Scripts;

import com.hilldev.hill60.components.Behaviour;
import com.hilldev.hill60.components.BehaviourComponent;
import com.hilldev.hill60.objects.HUD.HudManager;
import com.hilldev.hill60.objects.HUD.ItemDisplay;
import com.hilldev.hill60.objects.HUD.ItemIcon;
import com.hilldev.hill60.objects.Player;

public class HudManagerScript implements Behaviour {

    public ItemDisplay itemDisplay;
    public ItemIcon shovelIcon;
    public ItemIcon bigBombIcon;
    public ItemIcon smallBombIcon;
    public ItemIcon mediumBombIcon;
    public Player player;

    HudManager parent;

    @Override
    public void create(BehaviourComponent parentComponent) {
        parent = (HudManager)parentComponent.getParent();

        itemDisplay = parent.itemDisplay;
        shovelIcon = parent.shovelIcon;
        bigBombIcon = parent.bigBombIcon;
        mediumBombIcon = parent.mediumBombIcon;
        smallBombIcon = parent.smallBombIcon;
        player = parent.player;

        setAllInactive();
        shovelIcon.isActive=true;
    }

    public void setAllInactive() {
        shovelIcon.isActive=false;
        bigBombIcon.isActive=false;
        smallBombIcon.isActive=false;
        mediumBombIcon.isActive=false;
    }

    @Override
    public void run() {
        switch (player.playerScript.getCurrentItem()){
            case "Shovel":
                setAllInactive();
                shovelIcon.isActive=true;
                break;
            case "SmallBomb":
                setAllInactive();
                smallBombIcon.isActive=true;
                break;
            case "MediumBomb":
                setAllInactive();
                mediumBombIcon.isActive=true;
                break;
            case "BigBomb":
                setAllInactive();
                bigBombIcon.isActive=true;
                break;
            default:
                break;
        }
    }
}
