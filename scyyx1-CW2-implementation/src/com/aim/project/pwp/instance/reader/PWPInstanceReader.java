package com.aim.project.pwp.instance.reader;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;

import com.aim.project.pwp.instance.Location;
import com.aim.project.pwp.instance.PWPInstance;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.PWPInstanceReaderInterface;


public class PWPInstanceReader implements PWPInstanceReaderInterface {

	@Override
	public PWPInstanceInterface readPWPInstance(Path path, Random random) {

		BufferedReader bfr;
		try {
			bfr = Files.newBufferedReader(path);
			ArrayList<Location> locations = new ArrayList<Location>();
			Location home = null;
			Location depot = null;
			String str = bfr.readLine();
			boolean keepReading = false;

			while(str!= null) {
				double x = 0;
				double y = 0;

				if(str.contains("EOF")) {
					break;
				}
				if(str.contains("POSTAL_OFFICE")) {
					str = bfr.readLine();

					String[] substrs = str.split("\\s+");

					x = Double.parseDouble(substrs[0]);
					y = Double.parseDouble(substrs[1]);
					depot = new Location(x, y);

					
					
				}
				if(str.contains("WORKER_ADDRESS")) {
					str = bfr.readLine();
					
					String[] substrs = str.split("\\s+");
					x = Double.parseDouble(substrs[0]);
					y = Double.parseDouble(substrs[1]);
					home = new Location(x, y);
				}
				if(str.contains("POSTAL_ADDRESSES")) {
					keepReading = true;
					str = bfr.readLine();

				}
				if(keepReading) {
					String[] substrs = str.split("\\s+");
					x = Double.parseDouble(substrs[0]);
					y = Double.parseDouble(substrs[1]);
					locations.add(new Location(x, y));
				}
				str = bfr.readLine();
			}
			
			Location[] arr = locations.toArray(new Location[0]);

			return new PWPInstance(arr.length+2, arr, depot, home, random);
			// TODO read in the PWP instance and create and return a new 'PWPInstance'
			
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}
	}
}
