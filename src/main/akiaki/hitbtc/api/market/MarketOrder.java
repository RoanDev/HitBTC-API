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

public class MarketOrder {
    public double price, amount;

    protected MarketOrder(double price, double amount) {
        this.price = price;
        this.amount = amount;
    }
}