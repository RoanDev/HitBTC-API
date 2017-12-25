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

public class ExecutionReport {
    public String orderId, latestOrderId, clientOrderId, execReportType, orderStatus, orderRejectReason, symbol, side, type, timeInForce, userId;
    public double price, averagePrice;
    public long timestamp, tradeId;
    public int cumQuantity, leavesQuantity, lastQuantity, quantity;
}
