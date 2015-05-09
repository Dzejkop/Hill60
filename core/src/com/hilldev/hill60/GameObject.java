package com.hilldev.hill60;

import java.util.Set;

/**
 * Created by Jakub on 2015-05-09.
 *
 * Każdy obiekt w grze dziedziczy po tej klasie
 * Wszystkie obiekty tego typu znajdują się jakiejś liście w głównej klasie
 */
public class GameObject {

    private static int ID = 0;
    private int objectID;

    public GameObject() {
        objectID = ID++;    // Unikalne ID
    }

    // Zestaw komponentów, tylko jeden komponent danego typu
    Set<Component> componentSet;

    /**
     * Dodaje nowy komponent,
     * @param component
     */
    public void addComponent(Component component) {
        component.setParent(this);
        componentSet.add(component);
    }

    /**
     * Zwraca komponent danego typu,
     * jeżeli obiekt nie posiada takiego komponentu zwraca null
     * @param type
     * @return
     */
    public Component getComponent(Class type) {

        for(Component c : componentSet) {
            if(c.getClass().equals(type)) return c;
        }

        return null;
    }
}
