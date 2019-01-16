package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import model.Cartoon;
import repositories.CartoonRepository;

@Service
public class CartoonService {
    @Autowired
    public CartoonRepository cartoonRepository;

    public List<Cartoon> getAllCartoons(){return cartoonRepository.findAll();}

    public void addCartoon(Cartoon cartoon){
        cartoonRepository.save(cartoon);
    }

    public List<Cartoon> getCartoonsPaginated(Integer page_nr, Integer size)
    {
        Page<Cartoon> page = cartoonRepository.findAll(new PageRequest(page_nr, size));
        return page.getContent();
    }

    public boolean existCartoon(List<Cartoon> cartoons, Cartoon item)
    {
        for (Cartoon cartoon: cartoons)
            if (cartoon.getTitle().equals(item.getTitle()))
                return true;
        return false;
    }
    public void addCartoons(List<Cartoon> cartoons)
    {
        List<Cartoon> existing_cartoons = this.getAllCartoons();
        for (Cartoon cartoon: cartoons
             ) {
            if (!existCartoon(existing_cartoons, cartoon))
                this.addCartoon(cartoon);
        }
    }
}