package com.softserv.process;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import com.bas.basserver.executionengine.ExecutionException;
import com.bas.basserver.executionengine.IExecutionContext;
import com.bas.basserver.executionengine.IExecutionEngine;
import com.bas.basserver.executionengine.IProcess;
import com.bas.basserver.executionengine.ServerTimeOutException;
import com.bas.basserver.executionengine.SuspendProcessException;
import com.bas.connectionserver.server.AccessDeniedException;
import com.bas.shared.domain.configuration.elements.DomainFactory;
import com.bas.shared.domain.configuration.elements.IDomainVersion;
import com.bas.shared.domain.configuration.elements.IEntityDefinition;
import com.bas.shared.domain.operation.IEntity;
import com.bas.shared.domain.operation.IEntityTemplate;
import com.bas.shared.utils.Utils;

public class FindInWhichZone implements IProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String zonePolygonVerticesArray="";
	
	String numberOfVerticesArray="";
	
	String zoneIDArray="";
	
	String geofenceID="";
	
	String latitude="";
	
	String longitude="";
	
	String boid = "";
	
	String boName = "";
	
	String companyID = "";
	
	String fieldName = "";
	
	String vehicleID = "";
		
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bas.basserver.executionengine.IProcess#cancel()
	 */
	@Override
	public boolean cancel() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bas.basserver.executionengine.IProcess#execute(com.bas.basserver.
	 * executionengine.IExecutionEngine, java.lang.Object[])
	 */
	@Override
	public Object execute(IExecutionEngine engine, Object[] params)
			throws SuspendProcessException, ExecutionException, AccessDeniedException {
		// TODO Auto-generated method stub
		// Initialize tagValueMap for inserting all tags with key and value
		// pair.
		
		try {
			
            System.out.println("Called FindInWhichZone TEST-->>>>>>>>>>");
			// iterate all params
			for (int i = 0; i < params.length; i++) {
				if (params[i] instanceof IEntity) {

					// cast each param to IEntity
					IEntity value = (IEntity) params[i];
					
					if (value.getName().toString().equals("Geofence")) {
						 System.out.println("In this Geofence==>");	
						// get value of EmailBody attribute from object Email
						// Templates
						 geofenceID = (value.getAttributeValue("ID") == null) ? "NULL"
								: value.getAttributeValue("ID").toString();
						 System.out.println("geofenceID:"+geofenceID);
						 
						 
						 
						IEntity value_temp = null;
						IEntity[] temp = engine.getAllReferences(this, value, "Location");

						if (!(temp == null)) {
							System.out.println("In Location:");
							value_temp = temp[0];
							
							if (value_temp.getAvailableAttributes().toString().contains("Latitude")) {
								latitude = ((value_temp.getAttributeValue("Latitude") == null) ? ""
										: value_temp.getAttributeValue("Latitude").toString());

							}
							if (value_temp.getAvailableAttributes().toString().contains("Longitude")) {

								longitude = ((value_temp.getAttributeValue("Longitude") == null) ? ""
										: value_temp.getAttributeValue("Longitude").toString());

							}
						}
						
						System.out.println("latitude:"+latitude);
						System.out.println("longitude:"+longitude);
						

					}

					// Object of Job
					else if (value.getName().toString().equals("TempBO")) {
						System.out.println("In this TempBO==>");	
						
						zonePolygonVerticesArray = (value.getAttributeValue("ZonePolygonVerticesArray") == null) ? "NULL"
								: value.getAttributeValue("ZonePolygonVerticesArray").toString();
						System.out.println("zonePolygonVerticesArray:"+zonePolygonVerticesArray);
						
						numberOfVerticesArray = (value.getAttributeValue("NumberOfVerticesArray") == null) ? "NULL"
								: value.getAttributeValue("NumberOfVerticesArray").toString();
						System.out.println("numberOfVerticesArray:"+numberOfVerticesArray);
						
						zoneIDArray = (value.getAttributeValue("ZoneIDArray") == null) ? "NULL"
								: value.getAttributeValue("ZoneIDArray").toString();
						System.out.println("zoneIDArray:"+zoneIDArray);
						
						boid = (value.getAttributeValue("BOID") == null) ? "NULL"
								: value.getAttributeValue("BOID").toString();
						System.out.println("boid"+boid);
						
						boName = (value.getAttributeValue("BOName") == null) ? "NULL"
								: value.getAttributeValue("BOName").toString();
						System.out.println("boName:"+boName);
						
						companyID = (value.getAttributeValue("CompanyID") == null) ? "NULL"
								: value.getAttributeValue("CompanyID").toString();
						System.out.println("companyID:"+companyID);
						
						fieldName = (value.getAttributeValue("FieldName") == null) ? "NULL"
								: value.getAttributeValue("FieldName").toString();
						System.out.println("fieldName:"+fieldName);
						
						vehicleID = (value.getAttributeValue("VehicleID") == null) ? "NULL"
								: value.getAttributeValue("VehicleID").toString();
						System.out.println("vehicleID:"+vehicleID);
						
						
					}
					

					}

				}
						
			//String zonePolygonVerticesArray = "(-37.809473643688484, 144.99140528237487),(-37.805493073696304, 144.9559363989491),(-37.8009493620992, 144.95722385927624),(-37.787757451630526, 144.9408087401051),(-37.78663823515832, 144.94083019777722),(-37.78502721199251, 144.9404654173512),(-37.784074680118685, 144.93977877184338),(-37.78263318999251, 144.93977877184338),(-37.77521675488105, 144.93672759073468),(-37.77409734850357, 144.9369850828001),(-37.78012979635541, 144.98655354901513),(-37.77638169459388, 144.9896362313498),(-37.754941349034965, 144.99417756116304),(-37.73676503086335, 144.99963694042492),(-37.722220947163855, 145.00209629774008),(-37.715146313685885, 145.0081909866298),(-37.718275669156924, 145.02126366282886),(-37.72526847414014, 145.02328068400806),(-37.71802106428795, 145.04169136668628),(-37.70077289947341, 145.05835155103517),(-37.70568073311365, 145.08906492840904),(-37.71418103086311, 145.1186765374038),(-37.72711551268049, 145.11172960782656),(-37.73857245507524, 145.10214864456228),(-37.74490161305488, 145.1167398616033),(-37.721569988441075, 145.18671412464835),(-37.710934983934706, 145.19945018310082),(-37.66194369905368, 145.19950689626194),(-37.68653611511297, 145.30027212453342),(-37.70446577576852, 145.36945165944553),(-37.72497090360453, 145.40344061208225),(-37.76464908893149, 145.47544845855126),(-37.76641321971742, 145.51252731597313),(-37.790157037832195, 145.5075491360415),(-37.81430006833346, 145.47956833159813),(-37.9436966854111, 145.3760565212954),(-37.966030132391396, 145.38172134673485),(-38.00665789032995, 145.54832764663269),(-38.103257172141625, 145.5388028236968),(-38.11109141108445, 145.46498843160697),(-38.1070393234211, 145.41074343648978),(-38.04879952339047, 145.35272189107963),(-38.04149938823269, 145.33435412374564),(-38.03271122316305, 145.32577105489798),(-38.021758341668814, 145.31392641988822),(-38.01283256092914, 145.30379839864798),(-38.00147090424252, 145.2957303139312),(-37.99822439323457, 145.28388567892142),(-37.99619525087233, 145.27135439840384),(-37.98983690794391, 145.25367327657767),(-37.978201066395656, 145.2421719643218),(-37.974784782767344, 145.23854643230138),(-37.971215696706146, 145.23743063335118),(-37.96903356335311, 145.23648649577794),(-37.966380363317896, 145.23456772252132),(-37.963216886272804, 145.23098429127742),(-37.961237529183954, 145.2280445901971),(-37.958987425990365, 145.22461136265804),(-37.95382970210704, 145.21741921945696),(-37.9511225296084, 145.2134280924428),(-37.94880443387367, 145.20993049188738),(-37.94283234043486, 145.20240459797026),(-37.94029398923505, 145.19929323551298),(-37.92747924193711, 145.1828620671158),(-37.91689999104266, 145.17051116546446),(-37.91129660336859, 145.1645030172711),(-37.9081138245448, 145.16010419448668),(-37.900899493978585, 145.15175535566092),(-37.89700509980399, 145.14819338208915),(-37.89493929403943, 145.14506056195975),(-37.8942619680539, 145.14267876035453),(-37.89275489537654, 145.13218595868827),(-37.89198905148859, 145.12514410802322),(-37.891345566163004, 145.1202946741243),(-37.89029565697849, 145.11733351537185),(-37.88924573282189, 145.11540232488113),(-37.888043381930295, 145.1134496767183),(-37.88726438354766, 145.11175452062088),(-37.88648537692359, 145.1082998354097),(-37.88614667582082, 145.10557471105056),(-37.884278651507906, 145.0948769260597),(-37.882991540459535, 145.091508071537),(-37.88182295962991, 145.08957688104627),(-37.87823572847313, 145.08303996124374),(-37.87698238622541, 145.07995005645859),(-37.87669445323719, 145.0787484268199),(-37.87671139050296, 145.07718201675522),(-37.877033197812366, 145.07297631301986),(-37.876000021988084, 145.06913538971054),(-37.87335773593053, 145.06608840026962),(-37.86920780050298, 145.06424304046737),(-37.866187921032186, 145.06149178681207),(-37.86256273965587, 145.06016141114068),(-37.85672359498159, 145.05868342864656),(-37.85438558141153, 145.05743888366365),(-37.8527760362228, 145.0555935238614),(-37.85213220830809, 145.0542416905179),(-37.85087682128604, 145.0506054603042),(-37.84906387634793, 145.04773013224025),(-37.84864028465772, 145.0456487380447),(-37.84770837437361, 145.0426232062759),(-37.846352847481214, 145.04088513483424),(-37.845082018389185, 145.04011265863795),(-37.841902210510646, 145.03985903574392),(-37.83922481012514, 145.03891489817067),(-37.83778439629331, 145.037713268532),(-37.83702181287734, 145.0355889589922),(-37.837513256426384, 145.02891562296315),(-37.835106846434954, 145.02573988748952),(-37.83175129864791, 145.02243540598317),(-37.83061580094843, 145.01917383982106),(-37.829853143429816, 145.01548312021657),(-37.82988180747585, 145.01280650817603),(-37.830864786428144, 145.01061782561987),(-37.83083089082021, 145.0071845980808),(-37.830796995196685, 145.00405177795142),(-37.83215280799143, 145.0026784869358),(-37.83374585618898, 144.99881610595435),(-37.833610278958155, 144.99503955566138),(-37.8326273365835, 144.99160632812232),(-37.83157659059789, 144.99031886779517),(-37.82913609056963, 144.9882160159275),(26.83427316932259, 75.96172027587889),(26.822631181020387, 75.94901733398436),(26.83335410848263, 75.92567138671873),(26.846526601142788, 75.95622711181639),(26.856328391708306, 75.85975341796873),(26.846220281509392, 75.85563354492186),(26.849896062400198, 75.86730651855467),(26.80437966880019, 75.71727447509764),(26.818474719289625, 75.68431549072264),(26.840533018509106, 75.76499633789061),(26.788137547056575, 75.76636962890623),(26.780781859175192, 75.72242431640623)"; // Your corrected polygon data
	        //String numberOfVerticesArray = "122,4,3,5"; // Number of vertices for each polygon
	        //String zoneIDArray = "1152,1372,1545,2870"; // Zone IDs corresponding to each polygon
	        
	        // Parse polygon data into separate arrays
	        String[] polygonArray = zonePolygonVerticesArray.split("\\),\\s*\\(");
	        						
	        String[] numberOfVertices = numberOfVerticesArray.split(",");
	        
	        String[] zoneIDs = zoneIDArray.split(",");
	       
	        
	          	// Create a GeometryFactory
	            GeometryFactory geometryFactory = new GeometryFactory();
	           
	            // Parse each polygon and perform checks
	            int currentIndex = 0;
	            for (int i = 0; i < numberOfVertices.length; i++) {
	            	
	                int vertices = Integer.parseInt(numberOfVertices[i]);
	                String[] currentPolygonVertices = new String[vertices];

	                System.out.println("vertices===>"+vertices);
	                
	                // Extract the vertices for the current polygon
	                for (int j = 0; j < vertices; j++) {
	                	
	                    currentPolygonVertices[j] = polygonArray[currentIndex++].replaceAll("\\(", "").replaceAll("\\,", "").replaceAll("\\)", "").trim();
	                  
	                }

	                
	                
	                // Ensure closure by adding the first coordinate at the end
	                currentPolygonVertices[vertices - 1] += ", " + currentPolygonVertices[0];
	                
	               /* for (int j = 0; j < currentPolygonVertices.length ; j++) {
	                	
	                	System.out.println(currentPolygonVertices[j]);
	                }*/
	                              
	                // Construct a polygon
	                String polygonWKTPart = "POLYGON((" + String.join(", ", currentPolygonVertices) + "))";
	                
	                System.out.println(polygonWKTPart);
	                
	                WKTReader wktReader = new WKTReader(geometryFactory);
	                
	                Geometry polygonGeometry = wktReader.read(polygonWKTPart);
	                
	                
	                //double latitude = -37.7829949;
	                //double longitude = 145.1018489;

	                // Create a Coordinate for the point
	                Coordinate tempPointCoordinate = new Coordinate(Double.parseDouble(longitude), Double.parseDouble(latitude));    
	                
	                Coordinate pointCoordinate = new Coordinate(tempPointCoordinate.y, tempPointCoordinate.x);
	                
	                //System.out.println("Test Point Coordinate: " + pointCoordinate);
	                               
	                 
	                //System.out.println("Is Polygon Valid? " + polygonGeometry.isValid());
	                
	                //System.out.println("Geometry Type: " + polygonGeometry.getGeometryType());
	                
	                
	                boolean isInside = polygonGeometry.contains(geometryFactory.createPoint(pointCoordinate));
	              
	              
	                System.out.println("isInside==>"+isInside);	
	                // Print the result
	                if (isInside) {
	                    System.out.println("Point is inside Zone ID " + zoneIDs[i]);
	                    
	                    IExecutionContext context = engine.getExecutionContext(this);
	        			IDomainVersion domainVersion = context.getDomainVersion();
	        			IEntityDefinition defPerson = domainVersion.getEntityDefinition("TempZoneBO");
	        			IEntityTemplate tempPerson = DomainFactory.createEntityTemplate(defPerson);
	        			tempPerson.setAttributeValue("GeofenceID", geofenceID);
	        			tempPerson.setAttributeValue("ZoneID", zoneIDs[i]);	 		
	        		   	tempPerson.setAttributeValue("BOID", boid);
	        			tempPerson.setAttributeValue("BOName", boName);
	        			tempPerson.setAttributeValue("CompanyID", companyID);
	        			tempPerson.setAttributeValue("FieldName", fieldName);
	        			tempPerson.setAttributeValue("VehicleID", vehicleID);
	        			
	        			engine.createEntityFromTemplate(this, tempPerson, null);
	                    break;
	                } /*else {
	                    System.out.println("Point is outside Zone ID " + zoneIDs[i]);
	                }*/
	            }
	        
			
			}catch(Exception e){
				e.printStackTrace();
			}
				
		//IEntity value_ = (IEntity) params[2];
		//IEntity[] value_arr = new IEntity[1];
		//value_arr[0] = value_;

		return null;

	}
	// New code added here to show the sent emails on 1and1.com on 15/01/2018 up
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bas.basserver.executionengine.IProcess#resume(com.bas.basserver.
	 * executionengine.IExecutionEngine, java.lang.Object)
	 */
	@Override
	public Object resume(IExecutionEngine arg0, Object arg1)
			throws SuspendProcessException, ExecutionException, AccessDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

}