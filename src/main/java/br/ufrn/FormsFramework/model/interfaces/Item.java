package br.ufrn.FormsFramework.model.interfaces;

import java.util.List;

public interface Item {
    Integer getOrdem();
    List<Item> getSubItens();
} 
