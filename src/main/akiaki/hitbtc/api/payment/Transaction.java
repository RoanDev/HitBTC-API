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

package main.akiaki.hitbtc.api.payment;

public class Transaction {
    public String id, type, status, currency_code_from, currency_code_to, bitcoin_address, bitcoin_return_address, external_data, destination_data;
    public long created, finished;
    public double amount_from, amount_to, commission_percent;
}
