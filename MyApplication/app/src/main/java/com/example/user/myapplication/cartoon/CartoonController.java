package com.example.user.myapplication.cartoon;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

public class CartoonController extends ViewModel {
    private CartoonRepository cartoonRepository;

    public CartoonController(Context context) {
        cartoonRepository = CartoonRepository.getInstance(CartoonDatabase.getAppDatabase(context).cartoonDAO());
    }

    public void createCartoon(Cartoon cartoon){
        cartoonRepository.addCartoon(cartoon);
    }

    public List<Cartoon> getCartoons(int size, int page_nr){
        return cartoonRepository.getCartoons(size, page_nr);
    }

    public List<Cartoon> getAllCartoons(){
        return cartoonRepository.getAllCartoons();
    }

    public void updateCartoon(Integer id, Boolean added){
        cartoonRepository.updateCartoon(id, added);
    }

    //todo understand what happening here
    public static class Factory implements ViewModelProvider.Factory{
        private final Context ctxt;

        public Factory(Context ctxt) {
            this.ctxt = ctxt.getApplicationContext();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new CartoonController(ctxt);
        }
    }
}
