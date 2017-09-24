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

public class RecentTrade extends Trade {
    public String side;

    private RecentTrade(long tid, long date, double price, double amount) {
        super(tid, date, price, amount);
    }
}