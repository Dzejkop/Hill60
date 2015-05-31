package com.hilldev.hill60.Scripts;

import com.hilldev.hill60.components.Behaviour;
import com.hilldev.hill60.components.BehaviourComponent;
import com.hilldev.hill60.components.GuiSprite;
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
    
    private float getProperAlpha(float alpha)
    {
        if (alpha>0)
		return (1-0.2f-(alpha/100*72));
        else
        return 1;
    }

    @Override
    public void run() {
    	float alpha;
        switch (player.playerScript.getCurrentItem()){
            case "Shovel":
            	alpha= player.characterScript.getItem(CharacterScript.ITEM_LIST[0]).getAlpha();
            	alpha=getProperAlpha(alpha);
                setAllInactive();
                shovelIcon.getComponent(GuiSprite.class).alpha= alpha;
                shovelIcon.isActive=true;
                break;
            case "SmallBomb":
            	alpha= player.characterScript.getItem(CharacterScript.ITEM_LIST[1]).getAlpha();
            	alpha=getProperAlpha(alpha);
                setAllInactive();
                smallBombIcon.getComponent(GuiSprite.class).alpha=alpha;
                smallBombIcon.isActive=true;
                break;
            case "MediumBomb":
            	alpha= player.characterScript.getItem(CharacterScript.ITEM_LIST[2]).getAlpha();
            	alpha=getProperAlpha(alpha);
                setAllInactive();
                mediumBombIcon.getComponent(GuiSprite.class).alpha=alpha;
                mediumBombIcon.isActive=true;
                break;
            case "BigBomb":
            	alpha= player.characterScript.getItem(CharacterScript.ITEM_LIST[3]).getAlpha();
            	alpha=getProperAlpha(alpha);
                setAllInactive();
        		bigBombIcon.getComponent(GuiSprite.class).alpha=alpha;
                bigBombIcon.isActive=true;
                break;
            default:
                break;
        }
    }
}
