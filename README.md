# HitBTC API v0.3

Usually nobody reads this text but I'll still try. Hey everybody, this is my Java lib for interaction with HitBTC API. Lets be honest, this API is dumb and not well structured (`clientOrderId` & `client_order_id`, string arguments used as integers, etc) but anyway in order to make this lib as much easy as possible I followed doc for 100%. It means if doc says you should use `client_order_id` key even if all other keys are CamelCased, you should follow the doc. Same with returned values, you are getting all as it wrote on https://hitbtc.com/api.

This API separates to 3 parts: REST API, Socket.io API, Streaming API. The only REST API is done. Thats enough for trading, others will be implemented later.

Dont forget to star this repo if you found this work useful.

## Creating instance
for accessing public market data
```java
HitBTC service = new HitBTC(APIMode.PRODUCTION); // or APIMode.DEMO
```

to access market, trading and payment api

```java
HitBTC service = new HitBTC(APIMode.PRODUCTION, apiKey, apiSecret);
```
If u'll try access trading or payment api without providing `apiKey` and `apiSecret` or api keys are invalid u'll get **HitBTCAccessDenied** exception on calling tradeAPI() and paymentAPI() methods that are described below.

# Market API

## Market time
https://hitbtc.com/api#time

Gets current market time in UTC with milliseconds
```java
long time = service.marketAPI().getTimestamp(); // 1393492619000
```

## Market tradepairs (Symbols)
https://hitbtc.com/api#symbols

```java
ArrayList<Symbol> symbols = service.marketAPI().getAllSymbols();
for (Symbol sym : symbols) {
  System.out.println(sym.symbol);
}
```


Prints:
```
SCBTC
XMRBTC
BTCEUR
ZECETH
...
```

## Tradepairs market data
https://hitbtc.com/api#ticker

It's not a ticker you are thinking about. This is REST API. Means it named "ticker" but __it doesnt updates itself__. You have to call this method each time you want to receive current market rates.


```java
Ticker ticker = service.marketAPI().getTickerFor("BTCUSD");
```
Gives you an object with fields:
```
{
    "last": "550.73",
    "bid": "549.56",
    "ask": "554.12",
    "high": "600.1",
    "low": "400.7",
    "volume": "567.9",
    "open": "449.73",
    "volume_quote": "289002.81",
    "timestamp": 1393492619000

}
```

## All tradepairs market data
```java
ArrayList<Ticker> tickers = service.marketAPI().getTickersForAll();
```
Gives a list of objects with structure described above

//readme not finished
