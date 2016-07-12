package com.mirdar.O2O;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mirdar.test.Place;
import com.mirdar.test.RRecord;
import com.mirdar.test.Site;

public class ReadData {

	public Map<String, Place> readPlace(String filename,Map<String, Place> places)
	{
		File file = new File(filename);
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String content = null;
			while ((content = reader.readLine()) != null)
			{
				String[] strings = content.split(",");
				Place place = new Place();
				place.place_id = strings[0];
				place.lon = Double.parseDouble(strings[1]);
				place.lan = Double.parseDouble(strings[2]);
				places.put(place.place_id,place);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		return places;
	}
	
	public Map<String, Spot> readSpot(String filename)
	{
		Map<String, Spot> spots = new HashMap<String, Spot>();
		File file = new File(filename);
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String content = null;
			while ((content = reader.readLine()) != null)
			{
				String[] strings = content.split(",");
				Spot spot = new Spot();
				spot.spot_id = strings[0];
				spot.lon = Double.parseDouble(strings[1]);
				spot.lan = Double.parseDouble(strings[2]);
				spots.put(spot.spot_id,spot);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		return spots;
	}
	
	public Map<String, Site> readSite(String filename)
	{
		Map<String, Site> sites = new HashMap<String, Site>();
		File file = new File(filename);
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String content = null;
			while ((content = reader.readLine()) != null)
			{
				String[] strings = content.split(",");
				Site site = new Site();
				site.site_id = strings[0];
				site.lon = Double.parseDouble(strings[1]);
				site.lan = Double.parseDouble(strings[2]);
				sites.put(site.site_id,site);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		return sites;
	}
	
	public Map<String, Shop> readShop(String filename)
	{
		Map<String, Shop> shops = new HashMap<String, Shop>();
		File file = new File(filename);
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String content = null;
			while ((content = reader.readLine()) != null)
			{
				String[] strings = content.split(",");
				Shop shop = new Shop();
				shop.shop_id = strings[0];
				shop.lon = Double.parseDouble(strings[1]);
				shop.lan = Double.parseDouble(strings[2]);
				shops.put(shop.shop_id,shop);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		return shops;
	}

	public Map<String, ArrayList<Order>> readOrder(String filename)
	{
		Map<String, ArrayList<Order>> map = new HashMap<String, ArrayList<Order>>();
		ArrayList<Order> orders = new ArrayList<Order>();

		File file = new File(filename);
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String content = null;
			while ((content = reader.readLine()) != null)
			{
				String[] strings = content.split(",");
				Order order = new Order();
				order.shop_id = strings[0];
				if (!map.containsKey(order.shop_id))
				{
					orders = new ArrayList<Order>();
					map.put(order.shop_id, orders);
				}
				order.spot_id = strings[1];
				order.order_id = strings[2];
				order.pickup_time = Integer.parseInt(strings[3]);
				order.delivery_time = Integer.parseInt(strings[4]);
				order.num = Integer.parseInt(strings[5]);
				order.time = Integer.parseInt(strings[10]);
				order.stay_time = Integer.parseInt(strings[11]);
				order.last_time = order.delivery_time-order.time;
				orders.add(order);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		return (map);
	}
	
	public ArrayList<RRecord> readRRecord(String filename)
	{
		ArrayList<RRecord> records = new ArrayList<RRecord>();
		File file = new File(filename);
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String content = null;
			while ((content = reader.readLine()) != null)
			{
				String[] strings = content.split(",");
				RRecord record = new RRecord();
				record.courier_id = strings[0];
				record.place_id = strings[1];
				record.arriveTime = Integer.parseInt(strings[2]);
				record.departureTime = Integer.parseInt(strings[3]);
				record.num = Integer.parseInt(strings[4]);
				record.order_id = strings[5];
				records.add(record);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		return records;
	}
	
	public Map<Integer,String> readCourier(String filename)
	{
		Map<Integer,String> couriers = new HashMap<Integer,String>();
		File file = new File(filename);
		BufferedReader reader = null;
		int i=0;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String content = null;
			while ((content = reader.readLine()) != null)
			{
				String[] strings = content.split(",");
				String courier_id = strings[0];
				couriers.put(i, courier_id);
				i++;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		return couriers;
	}
}
