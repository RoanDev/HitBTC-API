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

package main.akiaki.hitbtc.api;

import com.google.gson.JsonSyntaxException;
import main.akiaki.hitbtc.api.payment.*;
import main.akiaki.hitbtc.exceptions.ErrorOnTransferBalances;
import main.akiaki.hitbtc.exceptions.HitBTCAccessDenied;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

public class PaymentAPI extends SignaturedAPIInstrumentary {
    public PaymentAPI(String apiKey, String apiSecret, String httpEndPoint) {
        super(apiKey, apiSecret, "/api/1/payment/", httpEndPoint);
    }

    public ArrayList<Balance> getBalances() throws IOException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, HitBTCAccessDenied {
        return this.loadGETURL("balance", Balances.class).balance;
    }

    public String transferBalanceToTrading(String currency_code, double amount) throws IOException, URISyntaxException, ErrorOnTransferBalances, NoSuchAlgorithmException, InvalidKeyException, HitBTCAccessDenied {
        HashMap<String, String> args = new HashMap<>();
        args.put("currency_code", currency_code);
        args.put("amount", amount + "");
        String result = this.loadPOSTURLAsString("transfer_to_trading", args);
        try {
            TransactionResult tr = this.getGson().fromJson(result, TransactionResult.class);
            if (tr.transaction != null && !tr.transaction.isEmpty()) {
                return tr.transaction;
            } else {
                throw new JsonSyntaxException("");
            }
        } catch (JsonSyntaxException e) {
            ErrorResult er = this.getGson().fromJson(result, ErrorResult.class);
            throw new ErrorOnTransferBalances(er.body);
        }
    }

    public String transferBalanceToMain(String currency_code, double amount) throws IOException, URISyntaxException, ErrorOnTransferBalances, NoSuchAlgorithmException, InvalidKeyException, HitBTCAccessDenied {
        HashMap<String, String> args = new HashMap<>();
        args.put("currency_code", currency_code);
        args.put("amount", amount + "");
        String result = this.loadPOSTURLAsString("transfer_to_main", args);
        try {
            TransactionResult tr = this.getGson().fromJson(result, TransactionResult.class);
            if (tr.transaction != null && !tr.transaction.isEmpty()) {
                return tr.transaction;
            } else {
                throw new JsonSyntaxException("");
            }
        } catch (JsonSyntaxException e) {
            ErrorResult er = this.getGson().fromJson(result, ErrorResult.class);
            throw new ErrorOnTransferBalances(er.body);
        }
    }

    public String getDepositAddress(String currency_code) throws IOException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, HitBTCAccessDenied {
        return this.loadGETURL("address/" + currency_code, WalletResult.class).address;
    }

    public String createNewDepositAddress(String currency_code) throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, HitBTCAccessDenied {
        return this.loadPOSTURL("address/" + currency_code, new HashMap<>(), WalletResult.class).address;
    }

    public String createPayout(String currency_code, String address, double amount) throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, ErrorOnTransferBalances, HitBTCAccessDenied {
        HashMap<String, String> args = new HashMap<>();
        args.put("currency_code", currency_code);
        args.put("address", address);
        args.put("amount", amount + "");
        String result = this.loadPOSTURLAsString("payout", args);
        try {
            TransactionResult tr = this.getGson().fromJson(result, TransactionResult.class);
            if (tr.transaction != null && !tr.transaction.isEmpty()) {
                return tr.transaction;
            } else {
                throw new JsonSyntaxException("");
            }
        } catch (JsonSyntaxException e) {
            ErrorResult er = this.getGson().fromJson(result, ErrorResult.class);
            throw new ErrorOnTransferBalances(er.message);
        }
    }

    public String createPayoutWithParams(String currency_code, String address, double amount, HashMap<String, String> args) throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, ErrorOnTransferBalances, HitBTCAccessDenied {
        args.put("currency_code", currency_code);
        args.put("address", address);
        args.put("amount", amount + "");
        String result = this.loadPOSTURLAsString("payout", args);
        try {
            TransactionResult tr = this.getGson().fromJson(result, TransactionResult.class);
            if (tr.transaction != null && !tr.transaction.isEmpty()) {
                return tr.transaction;
            } else {
                throw new JsonSyntaxException("");
            }
        } catch (JsonSyntaxException e) {
            ErrorResult er = this.getGson().fromJson(result, ErrorResult.class);
            throw new ErrorOnTransferBalances(er.body);
        }
    }

    public ArrayList<Transaction> getTransactions(int limit) throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, HitBTCAccessDenied {
        HashMap<String, String> args = new HashMap<>();
        args.put("limit", limit + "");
        return this.loadGETURL("transactions", args, TransactionsResult.class).transactions;
    }

    public ArrayList<Transaction> getTransactionsWithParams(int limit, HashMap<String, String> args) throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, HitBTCAccessDenied {
        args.put("limit", limit + "");
        return this.loadGETURL("transactions", args, TransactionsResult.class).transactions;
    }

    public ArrayList<Transaction> getTransaction(String transactionId) throws IOException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, HitBTCAccessDenied {
        return this.loadGETURL("transactions/" + transactionId, TransactionsResult.class).transactions;
    }
}
