/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cashier.model;

/**
 *
 * @author Ramen
 * Simple POJO= Plain Old Java Object
 * For storing each order. These are one column for each table, that fit in both kitchen and cashier
 * 
 * Android App sends values( TableNo, DishName, Quantity, Status[Uncooked], UnitPrice)
 * Kitchen see values ( TableNo, DishName, Quantity and Status)
 * Cashier see values ( TableNo, DishName, Quantity and UnitPrice) 
 * 
 * Session ID to keep track of one session.
 * 1Session = order in, uncooked, cooking, cooked, add on bill, and pay bill, after printout, one session ends, 
 * Also record sessions in database, for business reference.
 * Take care of session Id later.
 */
public class Order {
	
	public enum OrderStatus {
	    ORDER_IN, CANCELLED,UNCOOKED, COOKING, COOKED, DELIVERED, ONBILL, PAID
	}
    
    //private String SessionId;
    private Integer TableNo;
    private String DishName;
    private Integer Quantity;
    private OrderStatus Status;
    private Integer UnitPrice;
    
    public Order(Integer TableNo, String DishName, Integer Quantity, OrderStatus Status, Integer UnitPrice) {
        this.TableNo = TableNo;
        this.DishName = DishName;
        this.Quantity = Quantity;
        this.Status = Status;
        this.UnitPrice = UnitPrice;
    }

    public Integer getTableNo() {
        return TableNo;
    }

    public void setTableNo(Integer TableNo) {
        this.TableNo = TableNo;
    }

    public String getDishName() {
        return DishName;
    }

    public void setDishName(String DishName) {
        this.DishName = DishName;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer Quantity) {
        this.Quantity = Quantity;
    }

    public OrderStatus getStatus() {
        return Status;
    }

    public void setStatus(OrderStatus Status) {
        this.Status = Status;
    }

    public Integer getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(Integer UnitPrice) {
        this.UnitPrice = UnitPrice;
    }
    
    
}
