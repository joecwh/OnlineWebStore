/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import java.util.List;
import model.Card;

/**
 *
 * @author Lenovo
 */
public interface ICreditDebitCardRepository 
{
    public List<Card> GetAllCard();
    public Card GetCard(String cardid);
    public boolean CardExistByCardNumber(String cardNumber);
    public boolean AddCard(Card card);
    public boolean UpdateCard(Card card);
    public boolean DeleteCard(String cardid);
}
