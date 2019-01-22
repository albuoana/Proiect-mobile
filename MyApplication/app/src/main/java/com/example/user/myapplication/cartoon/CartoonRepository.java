package com.example.user.myapplication.cartoon;

import android.arch.lifecycle.LiveData;

import java.util.List;

public class CartoonRepository {
    private final CartoonDAO cartoonDAO;
    private static CartoonRepository instance;
    private LiveData<Cartoon> cartoonLiveData;

    private CartoonRepository(CartoonDAO cartoonDAO) {
        this.cartoonDAO = cartoonDAO;
    }

    public static CartoonRepository getInstance(CartoonDAO cartoonDAO)
    {
        if (instance == null)
            instance = new CartoonRepository(cartoonDAO);
        return instance;
    }

    public List<Cartoon> getAllCartoons(){
        return cartoonDAO.getAllCartoons();
    }

    public void updateCartoon(Integer id, Boolean added)
    {
        cartoonDAO.updateCartoon(id, added);
    }

    public void addCartoon(Cartoon cartoon){
        cartoonDAO.insert(cartoon);
    }

    public List<Cartoon> getCartoons(int size, int page_nr)
    {
        return cartoonDAO.getCartoons(page_nr, size);
    }
}
