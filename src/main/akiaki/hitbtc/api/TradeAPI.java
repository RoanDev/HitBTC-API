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

import main.akiaki.hitbtc.api.trading.*;
import main.akiaki.hitbtc.exceptions.HitBTCAccessDenied;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TradeAPI extends SignaturedAPIInstrumentary {
    public TradeAPI(String apiKey, String apiSecret, String httpEndPoint) {
        super(apiKey, apiSecret, "/api/1/trading/", httpEndPoint);
    }

    public ArrayList<TradingBalance> getAllBalances() throws IOException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, HitBTCAccessDenied {
        return this.loadGETURL("balance", TradingBalances.class).balance;
    }

    public ArrayList<TradeOrder> getActiveOrders() throws IOException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, HitBTCAccessDenied {
        return this.loadGETURL("tradeOrders/active", ActiveOrders.class).tradeOrders;
    }

    public ArrayList<TradeOrder> getActiveOrdersWithParams(Map<String, String> args) throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, HitBTCAccessDenied {
        return this.loadGETURL("tradeOrders/active", args, ActiveOrders.class).tradeOrders;
    }

    public ArrayList<TradeOrder> getRecentOrders(int maxresults) throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, HitBTCAccessDenied {
        HashMap<String, String> args = new HashMap<>();
        args.put("maxresults", maxresults + "");
        return this.loadGETURL("tradeOrders/recent", args, RecentOrders.class).tradeOrders;
    }

    public ArrayList<TradeOrder> getRecentOrdersWithParams(int maxresults, Map<String, String> args) throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, HitBTCAccessDenied {
        args.put("maxresults", maxresults + "");
        return this.loadGETURL("tradeOrders/recent", args, RecentOrders.class).tradeOrders;
    }

    public ArrayList<TradeOrder> getOrders() throws IOException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, HitBTCAccessDenied {
        return this.loadGETURL("order", Orders.class).tradeOrders;
    }

    public ArrayList<Trade> getTrades(Map<String, String> args) throws IOException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, HitBTCAccessDenied {
        return this.loadGETURL("trades", Trades.class).trades;
    }

    public TradeOrder getOrder(String clientOrderId) throws IOException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, HitBTCAccessDenied {
        HashMap<String, String> args = new HashMap<>();
        args.put("client_order_id", clientOrderId);
        return this.loadGETURL("order", Orders.class).tradeOrders.get(0);
    }

    public ExecutionReport createNewOrder(Map<String, String> args) throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, HitBTCAccessDenied {
        return this.loadPOSTURL("new_order", args, POSTAPIResult.class).ExecutionReport.get(0);
    }

    public ExecutionReport cancelOrder(String clientOrderId) throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, HitBTCAccessDenied {
        HashMap<String, String> args = new HashMap<>();
        args.put("clientOrderId", clientOrderId);
        return this.loadPOSTURL("cancel_order", args, POSTAPIResult.class).ExecutionReport.get(0);
    }

    public ExecutionReport cancelOrderWithParams(String clientOrderId, Map<String, String> args) throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, HitBTCAccessDenied {
        args.put("clientOrderId", clientOrderId);
        return this.loadPOSTURL("cancel_order", args, POSTAPIResult.class).ExecutionReport.get(0);
    }

    public ArrayList<ExecutionReport> cancelAllOrders(Map<String, String> args) throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, HitBTCAccessDenied {
        return this.loadPOSTURL("cancel_orders", args, POSTAPIResult.class).ExecutionReport;
    }

    public ArrayList<Trade> getAllTradesByOrder(String clientOrderId) throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, HitBTCAccessDenied {
        HashMap<String, String> args = new HashMap<>();
        args.put("clientOrderId", clientOrderId);
        return this.loadGETURL("trades/by/order", args, OrderTrades.class).trades;
    }
}
