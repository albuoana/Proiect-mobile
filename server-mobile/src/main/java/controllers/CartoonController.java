package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import controllers.config.EndpointUtils;
import model.Cartoon;
import services.CartoonService;

@RestController
@RequestMapping(path = EndpointUtils.API_VERSION + "/cartoons")
public class CartoonController {
    @Autowired
    private CartoonService cartoonService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cartoon> addCartoon(@RequestBody Cartoon cartoon){
        System.out.println("Enter Add Cartoon");
        cartoonService.addCartoon(cartoon);
        return new ResponseEntity<>(cartoon, null, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cartoon>> getCartoons(){
        System.out.println("Enter Get Cartoons");
        return new ResponseEntity<>(cartoonService.getAllCartoons(), null, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value="/saveAll",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cartoon>> saveAllCartoons(@RequestBody List<Cartoon> cartoons){
        System.out.println("Enter Save all Cartoons");
        cartoonService.addCartoons(cartoons);
        return new ResponseEntity<>(new ArrayList<>(), null, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value="/cartoonsPaginated",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cartoon>> getCartoonsPaginated(@RequestParam Integer page_nr, @RequestParam Integer size){
        try {
            return new ResponseEntity<>(cartoonService.getCartoonsPaginated(page_nr/size, size), null, HttpStatus.OK);
        }
        catch(IndexOutOfBoundsException ex){
            return new ResponseEntity<>(new ArrayList<>(), null, HttpStatus.OK);
        }
    }

}

