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


public class Trade {
    public int execQuantity;
    public double execPrice, fee;
    public String side, symbol, clientOrderId;
    public long timestamp, tradeId, originalOrderId;
}
