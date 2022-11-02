package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.CartDTO;
import com.example.demo.DTO.ResponseDTO;
import com.example.demo.Model.Cart;
import com.example.demo.Service.ICartService;

@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {

    @Autowired
    ICartService cartService;
    /**
     * Add to Cart for UserDetails and Book Details
     * @param cartdto
     * @return
     */
    @PostMapping("/addCart")
    public ResponseEntity<ResponseDTO> insertItem(@Valid @RequestBody CartDTO cartdto) {
        Cart newCart = cartService.insertItems(cartdto);
        ResponseDTO responseDTO = new ResponseDTO("User Added to Cart successfully !", newCart);
        return new ResponseEntity(responseDTO, HttpStatus.CREATED);
    }
    /**
     * To Get all Cart Details
     * @return
     */
    @GetMapping("/getAll")
    public ResponseDTO getAllCartDetails() {
        ResponseDTO responseDTO = cartService.getCartDetails();
        return responseDTO;
    }
    /**
     * TO Get Particular Cart Details
     * @param cartId
     * @return
     */

    @GetMapping("/getById/{cartId}")
    public ResponseEntity<ResponseDTO> getCartDetailsById(@PathVariable Integer cartId){
        Optional<Cart> specificCartDetail=cartService.getCartDetailsById(cartId);
        ResponseDTO responseDTO=new ResponseDTO("Cart details retrieved successfully",specificCartDetail);
        return new ResponseEntity(responseDTO,HttpStatus.ACCEPTED);
    }
    /**
     * To Delete Cart Details
     * @param cartId
     * @return
     */
    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<ResponseDTO> deleteCartById(@PathVariable Integer cartId) {
        Optional<Cart> delete = cartService.deleteCartItemById(cartId);
        ResponseDTO responseDTO = new ResponseDTO("Cart delete successfully", delete);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }
    /**
     * To Update the Cart Details
     * @param cartId
     * @param cartDTO
     * @return
     */

    @PutMapping("/updateById/{cartId}")
    public ResponseEntity<ResponseDTO> updateCartById(@PathVariable Integer cartId,@Valid @RequestBody CartDTO cartDTO){
        Cart updateRecord = cartService.updateRecordById(cartId,cartDTO);
        ResponseDTO dto = new ResponseDTO(" Cart Record updated successfully by Id",updateRecord);
        return new ResponseEntity(dto,HttpStatus.ACCEPTED);
    }
    /**
     * To Update the Quantity using user token and cartId
     * @param token
     * @param cartId
     * @param quantity
     * @return
     */

    @PutMapping("/updateQuantity/{token}/{cartId}")
    public ResponseEntity<ResponseDTO> updateQuantity(@PathVariable String token,@PathVariable Integer cartId,@RequestParam int quantity){
        Cart updateCartQuantity= cartService.updateQuantity(token,cartId,quantity);
        ResponseDTO dto = new ResponseDTO("Quantity for book record updated successfully !",updateCartQuantity);
        return new ResponseEntity(dto,HttpStatus.OK);
    }
    /**
     * Get UserCartDetails using user Token
     * @param token
     * @return
     */
    @GetMapping("/userCart/{token}")
    public ResponseEntity<ResponseDTO> getCartDataByUserID(@PathVariable String token){
        List<Cart> userCartDetails = cartService.getCartDetailsByUser(token);
        ResponseDTO responseDTO = new ResponseDTO("Cart Details of Given ID are Retrieved Successfully", userCartDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
