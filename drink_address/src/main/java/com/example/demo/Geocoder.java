//package com.example.demo;
//
//import java.io.IOException;
//
//import com.google.maps.GeoApiContext;
//import com.google.maps.GeocodingApi;
//import com.google.maps.GeocodingApiRequest;
//import com.google.maps.errors.ApiException;
//import com.google.maps.model.GeocodingResult;
//import com.google.maps.model.LatLng;
//
//public class Geocoder {
//	private static GeoApiContext context = new GeoApiContext.Builder()
//            .apiKey("YOUR_API_KEY") // さっき取得したAPIキー
//            .build();
//
//    public static void main(String[] args) {
//        GeocodingResult[] results = null;
//		try {
//			results = getResults("東京都千代田区神田須田町1-18");
//		} catch (ApiException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//        if (results != null && results.length > 0) {
//            LatLng latLng = results[0].geometry.location; // とりあえず一番上のデータを使う
//            System.out.println("緯度 : " + latLng.lat);
//            System.out.println("経度 : " + latLng.lng);
//        }
//    }
//
//    public static GeocodingResult[] getResults(String address) throws ApiException, InterruptedException, IOException {
//        GeocodingApiRequest req = GeocodingApi.newRequest(context)
//                .address(address)
//                // .components(ComponentFilter.country("JP"))
//                .language("ja");
//
//        try {
//            GeocodingResult[] results = req.await();
//            if (results == null || results.length == 0) {
//                // ZERO_RESULTSはresults.length==0の空配列がsuccessful扱いで返ってくるっぽい
//                System.out.println("zero results.");
//            }
//            return results;
//        } catch (ApiException e) {
//            // ZERO_RESULTS以外のApiExceptionはこっちで
//            System.out.println("geocode failed.");
//            System.out.println(e);
//            return null;
//        } catch (Exception e) {
//            System.out.println("error.");
//            System.out.println(e);
//            return null;
//        }
//    }
//}
