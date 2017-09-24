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

import com.google.gson.reflect.TypeToken;
import main.akiaki.hitbtc.api.market.*;
import main.akiaki.hitbtc.exceptions.HitBTCAccessDenied;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

public class MarketAPI extends APIInstrumentary {
    public MarketAPI(String httpEndPoint) {
        super("/api/1/public/", httpEndPoint);
    }

    public Timestamp getTimestamp() throws IOException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, HitBTCAccessDenied {
        return this.loadGETURL("time", Timestamp.class);
    }

    public ArrayList<Symbol> getAllSymbols() throws IOException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, HitBTCAccessDenied {
        return this.loadGETURL("symbols", Symbols.class).symbols;
    }

    public Ticker getTickerFor(String symbol) throws IOException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, HitBTCAccessDenied {
        return this.loadGETURL(symbol + "/ticker", Ticker.class);
    }

    public ArrayList<Ticker> getTickersForAll() throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, HitBTCAccessDenied {
        return this.loadGETURL("ticker", new TypeToken<ArrayList<Ticker>>() {
        }.getType());
    }

    public ArrayList<Trade> getTradesWithParams(String symbol, HashMap<String, String> params) throws IOException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, HitBTCAccessDenied {
        params.put("format_item", "object");
        return this.loadGETURL(symbol + "/trading", params, new TypeToken<ArrayList<Trade>>() {
        }.getType());
    }

    public ArrayList<RecentTrade> getRecentTradesWithParams(String symbol, HashMap<String, String> params) throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, HitBTCAccessDenied {
        params.put("format_item", "object");
        return this.loadGETURL(symbol + "/trading/recent", params, RecentTrades.class).trades;
    }
}
