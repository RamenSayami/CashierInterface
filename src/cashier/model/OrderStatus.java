/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cashier.model;

/**
 *Orders have standard statuses, define them.. later use them from here.. 
 * Also good example of java ENUM
 * @author Ramen
 */
public enum OrderStatus {
    ORDER_IN, CANCELLED,UNCOOKED, COOKING, COOKED, DELIVERED, ONBILL, PAID
}