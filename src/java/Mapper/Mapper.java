/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapper;

import dto.CartDto;
import dto.UserDto;
import java.util.ArrayList;
import java.util.List;
import model.Cart;
import model.Product;
import model.Users;

/**
 *
 * @author Lenovo
 */
public class Mapper 
{
    public static UserDto fromUserToUserDto(Users user) {
        UserDto dto = new UserDto();
        dto.setUserid(user.getUserid());
        dto.setUsername(user.getUsername());
        dto.setFullname(user.getFullname());
        dto.setEmail(user.getEmail());
        dto.setContact(user.getContact());
        dto.setDob(user.getDob());
        dto.setAddress(user.getAddress());
        dto.setAccountstatus(user.getAccountstatus());
        return dto;
    }
    
    public static List<UserDto> fromUserListToUserDtoList(List<Users> userList) 
    {
        List<UserDto> userDtoList = new ArrayList<>();
        for (Users user : userList) {
            UserDto userDto = fromUserToUserDto(user);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }
    
    public static CartDto fromCartToCartDto(Cart cart, Product product) 
    {
        CartDto dto = new CartDto();
        dto.setCartid(cart.getCartid());
        dto.setUserid(cart.getUserid());
        dto.setProductid(cart.getProductid());
        dto.setQuantity(cart.getQuantity());
        dto.setTotal(cart.getTotal());
        dto.setIspaid(cart.getIspaid());
        dto.setInstock(product.getQuantity());
        dto.setProductImg(product.getProductimg());
        dto.setProductName(product.getProductname());
        dto.setProductdesc(product.getProductdesc());
        dto.setProductPrice(product.getPrice());
        return dto;
    }
    
    public static List<CartDto> fromCartListToCartDtoList(List<Cart> carts, List<Product> products) 
    {
        List<CartDto> cartDtoList = new ArrayList<>();
        int i = 0;
        for (Cart cart : carts) 
        {
            CartDto dto = fromCartToCartDto(cart, products.get(i));
            cartDtoList.add(dto);
            i++;
        }
        return cartDtoList;
    }
    
    public static Cart fromCartDtoToCart(CartDto cartDto) 
    {
        Cart dto = new Cart(cartDto.getUserid(), cartDto.getProductid(), cartDto.getQuantity(), cartDto.getTotal());
        dto.setCartid(cartDto.getCartid());
        dto.setIspaid(cartDto.getIspaid());
        return dto;
    }
    
    public static List<Cart> fromCartDtoListToCartList(List<CartDto> cartsDto) 
    {
        List<Cart> cartList = new ArrayList<>();
        for (CartDto c : cartsDto) 
        {
            Cart cart = fromCartDtoToCart(c);
            cartList.add(cart);
        }
        return cartList;
    }
    
    public static Product fromCartDtoToProduct(CartDto cartDto) 
    {
        Product dto = new Product(cartDto.getProductid(), cartDto.getProductName(), cartDto.getProductdesc(), cartDto.getProductPrice(), cartDto.getInstock(), cartDto.getProductImg());
        return dto;
    }
    
    public static List<Product> fromCartDtoListToProductList(List<CartDto> cartsDto) 
    {
        List<Product> productList = new ArrayList<>();
        for (CartDto c : cartsDto) 
        {
            Product p = fromCartDtoToProduct(c);
            productList.add(p);
        }
        return productList;
    }
}
