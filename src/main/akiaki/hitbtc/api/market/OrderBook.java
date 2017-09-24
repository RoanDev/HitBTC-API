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

package main.akiaki.hitbtc.api.market;

import java.util.ArrayList;

public class OrderBook {
    private double[][] asks, bids;
    private transient ArrayList<MarketOrder> buy, sell;

    public ArrayList<MarketOrder> getBuyOrders() {
        if (buy == null) {
            buy = new ArrayList<>();
            for (double[] a : bids) {
                buy.add(new MarketOrder(a[0], a[1]));
            }
        }
        return buy;
    }

    public ArrayList<MarketOrder> getSellOrders() {
        if (sell == null) {
            sell = new ArrayList<>();
            for (double[] b : asks) {
                sell.add(new MarketOrder(b[0], b[1]));
            }
        }
        return sell;
    }
}
