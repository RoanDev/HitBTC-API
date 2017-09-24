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

public class Trade {
    public long tid, date;
    public double price, amount;

    public Trade(long tid, long date, double price, double amount) {
        this.tid = tid;
        this.date = date;
        this.price = price;
        this.amount = amount;
    }
}
