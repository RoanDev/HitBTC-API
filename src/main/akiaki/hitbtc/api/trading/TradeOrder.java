/*
 * Mykhailo Pietielin 2017.
 * https://github.com/RoanDev
 * roanworkbox@gmail.com
 * GPL c:
 */

/*
 * Mykhailo Pietielin 2017.
 * https://github.com/RoanDev
 * roanworkbox@gmail.com
 * GPL c:
 */

package main.akiaki.hitbtc.api.trading;

public class TradeOrder {
    public long lastTimestamp;
    public int orderQuantity, quantityLeaves, execQuantity;
    public double avgPrice, orderPrice;
    public String orderId, orderStatus, type, timeInForce, clientOrderId, symbol, side;
}
