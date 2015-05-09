package com.hilldev.hill60;

/**
 * Created by Jakub on 2015-05-09.
 * Komponenty są elementami znajdującymi się w obiekcie
 * np. Obiekt player będzie miał komponenty: Renderer, InputManager, FreeObject
 * Komponenty mają dostęp do obiektu oraz reszty komponentów w obiekcie
 */
public abstract class Component {
    GameObject parent;

    public void setParent(GameObject parent) {
        this.parent = parent;
    }
}
