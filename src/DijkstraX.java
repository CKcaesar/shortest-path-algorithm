import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class DijkstraX
{
    //generate the tree from source vertex to all the other vertexes by distance 
	public static void computePaths(Vertex source)
    {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
      	vertexQueue.add(source);

      	while (!vertexQueue.isEmpty()) {
      		Vertex u = vertexQueue.poll();

      		// Visit each edge exiting u
      		for (Edge e : u.adjacencies)
      		{
      			Vertex v = e.target;
      			double weight = e.weight;
      			double distanceThroughU = u.minDistance + weight;
      			if (distanceThroughU < v.minDistance) {
      				vertexQueue.remove(v);
      				v.minDistance = distanceThroughU ;
      				v.previous = u;
      				vertexQueue.add(v);
      			}
            }
        }
    }
    
    //generate the tree from start vertex to end vertex by distance
    public static void computePath(Vertex sVertex, Vertex eVertex){
    	sVertex.minDistance=0;
    	sVertex.previous=null;
    	PriorityQueue<Vertex> vertexQueue=new PriorityQueue<Vertex>();
    	vertexQueue.add(sVertex);
    	eVertex.minDistance=Double.POSITIVE_INFINITY;
    	
    	while (!((eVertex.minDistance!=Double.POSITIVE_INFINITY)
    			&&!vertexQueue.contains(eVertex))) {
      		Vertex u = vertexQueue.poll();

      		// Visit each edge exiting u
      		for (Edge e : u.adjacencies)
      		{
      			Vertex v = e.target;
      			double weight = e.weight;
      			double distanceThroughU = u.minDistance + weight;
      			if (distanceThroughU < v.minDistance) {
      				vertexQueue.remove(v);
      				v.minDistance = distanceThroughU ;
      				v.previous = u;
      				vertexQueue.add(v);
      			}
            }
        }
    }
    
    //clear the minDistance of each vertex
    public static void clear(Vertex[] vertices){
    	for(int i=1;i<vertices.length;i++){
    		vertices[i].minDistance=Double.POSITIVE_INFINITY;
    	}
    }
    
    //get the shortest path from source to all the other vertexes
    public static List<Vertex> getShortestPathTo(Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }
    
    //get the shortest path from the start vertex to the end vertex, stop.
    public static List<Vertex> getShortestPathFromTo(Vertex start, Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != start.previous; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }

	public static List<Vertex> findVertexByItemName(Vertex[] list, String[] itemName){
        int num=itemName.length;
        List<Vertex> destinations=new ArrayList<Vertex>();
        for(int i=0;i<num;i++){
        	for (Vertex v : list){
	        	if(v.supply.contains(itemName[i])){
	        		if(destinations.contains(v)){
	        			v.getSth=v.getSth+" "+itemName[i];
	        		}
	        		else{
	        			destinations.add(v);
	        			v.getSth=" "+itemName[i];
	        		}	
	        	}
        	}
        }    	
        return destinations;
    }
	
	//sort the vertex by distance to the source vertex (breadth first traverse)
	public static void sortVertex(List<Vertex> destinations){
		//List<Vertex> destinations=new ArrayList<Vertex>();
		for(int i=destinations.size()-1;i>=0;i--){
			for(int j=0;j<i;j++){
				if(destinations.get(j).minDistance>destinations.get(j+1).minDistance){
					Vertex temp=destinations.get(j);
					destinations.set(j, destinations.get(j+1));
					destinations.set(j+1,temp);
				}
			}	
		}
	}
	
	//get the list of all end Vertex of paths
	public static List<Vertex> getPathEndVertex(Vertex[] vertices){
		List<Vertex> endVertex=new ArrayList<Vertex>();
		for(Vertex v:vertices){
			endVertex.add(v);
		}
		for(Vertex v:vertices){
			endVertex.remove(v.previous);
		}	
		return endVertex;		
	}
	
	//sort the vertex by whether vertexes are in the same path (deep first traverse)
	public static List<Vertex> sortVertex_DF(List<Vertex> destinations, List<Vertex> endVertex){
		List<Vertex> sequence=new ArrayList<Vertex>();
		for(Vertex v: endVertex){
			List<Vertex> sequence_temp=new ArrayList<Vertex>();
			while(v.previous!=null){
				if(destinations.contains(v)&&!sequence.contains(v)){
					sequence_temp.add(v);
				}
				v=v.previous;
			}
			Collections.reverse(sequence_temp);
			sequence.addAll(sequence_temp);
		}
		return sequence;
	}
	
	//combine the paths
	public static void combinePath(){
		
	}

    public static void main(String[] args) throws IOException
    {
        Vertex v0 = new Vertex("frontgate","");
        Vertex vBK = new Vertex("Bakery","breadcake");
        Vertex vGR = new Vertex("Grocery","sugarsaltsacks");
        Vertex vFD_s1 = new Vertex("FrozenDairy_s1","milk");
        Vertex vFD_s2 = new Vertex("FrozenDairy_s2","seabass");
        Vertex vSF = new Vertex("Seafood","fishshrimpcrab");
        Vertex vMT = new Vertex("Meat","porkbeeflamp");
        Vertex vPD = new Vertex("Produce","spinachtomatoapplepeach"); 

        v0.adjacencies = new Edge[]{ new Edge(vBK, 30),
	                             	new Edge(vGR, 20),
	                             	new Edge(vFD_s1, 25)};
        vBK.adjacencies = new Edge[]{ new Edge(vFD_s1, 10),
	                             	new Edge(vSF, 30)};
        vFD_s1.adjacencies = new Edge[]{ new Edge(vBK, 10),
        							new Edge(vSF, 20),
                               		new Edge(vGR, 20)};
        vGR.adjacencies = new Edge[]{ new Edge(vSF, 10),
	                             	new Edge(vFD_s1, 20),
	                             	new Edge(vFD_s2, 10),
	                             	new Edge(vPD, 15)};
        vFD_s2.adjacencies = new Edge[]{ new Edge(vSF, 10),
                               		new Edge(vGR, 10),
                               		new Edge(vMT, 10),
                               		new Edge(vPD, 10)};
        vSF.adjacencies = new Edge[]{ new Edge(vBK, 30),
        							new Edge(vFD_s1, 20),
        							new Edge(vGR, 10),
        							new Edge(vMT, 10)};
        vMT.adjacencies = new Edge[]{ new Edge(vSF, 10),
									new Edge(vFD_s2, 10),
									new Edge(vPD, 20)};
        vPD.adjacencies = new Edge[]{ new Edge(vMT, 20),
									new Edge(vFD_s2, 10),
									new Edge(vGR, 15)};

        Vertex[] vertices = { v0, vBK, vFD_s1, vFD_s2, vGR, vSF, vMT, vPD };
        computePaths(v0);
        
        //find Vertex(es) by item name.
        //String itemNames="milk,coffee";
        //String itemNames="milk,spinach,bread,cake";
        //String itemNames="milk,spinach,sugar";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
        System.out.println("Please input items you want. Split with ','");
        System.out.println("format like: milk,spinach,sugar");
        String itemNames=br.readLine();
        br.close();
        String[] itemName=itemNames.split(",");
        List<Vertex> destinations=findVertexByItemName(vertices, itemName);
        
        //start breadth first traverse
        //sort the vertex(es) by priority
        sortVertex(destinations);
        double distance_BF_total=0;
        List<Vertex> path = getShortestPathTo(destinations.get(0));
        System.out.println("Approach one Result:"); 
	    System.out.println("Path: " + path);
	    System.out.println("Get: "+destinations.get(0).getSth);
	    System.out.println(destinations.get(0).minDistance);
	    distance_BF_total=distance_BF_total+destinations.get(0).minDistance;
	    
	    //System.out.println(destinations.get(0).name+destinations.get(1).name);
	    for(int i=0;i<destinations.size()-1;i++){
	    	clear(vertices);
	    	computePath(destinations.get(i),destinations.get(i+1));
		    List<Vertex> pathx = getShortestPathFromTo(destinations.get(i),destinations.get(i+1));
		    System.out.println("Path: " + pathx);
		    System.out.println("Get: "+destinations.get(i+1).getSth);
		    System.out.println(destinations.get(i+1).minDistance);
		    distance_BF_total=distance_BF_total+destinations.get(i+1).minDistance;
	    }
	    System.out.println("Total Distance: "+distance_BF_total+'\n');
        //end breadth first traverse
	    
	    //start deep first traverse
	    clear(vertices);
	    computePaths(v0);
        double distance_DF_total=0;
        List<Vertex> endVertex=getPathEndVertex(vertices);
        //System.out.println("end vertex: " + endVertex);
        List<Vertex> destinations_DF=sortVertex_DF(destinations, endVertex);
        //System.out.println("destinations_DF: " + destinations_DF);
        List<Vertex> path_DF = getShortestPathTo(destinations_DF.get(0)); 
        System.out.println("Approach two Result:");
	    System.out.println("Path: " + path_DF);
	    System.out.println("Get: "+destinations_DF.get(0).getSth);
	    System.out.println("Distance: "+destinations_DF.get(0).minDistance);
	    distance_DF_total=distance_DF_total+destinations_DF.get(0).minDistance;

	    for(int i=0;i<destinations_DF.size()-1;i++){
	    	clear(vertices);
	    	computePath(destinations_DF.get(i),destinations_DF.get(i+1));
		    List<Vertex> pathx = getShortestPathFromTo(destinations_DF.get(i),destinations_DF.get(i+1));
		    System.out.println("Path: " + pathx);
		    System.out.println("Get: "+destinations_DF.get(i+1).getSth);
		    System.out.println("Distance: "+destinations_DF.get(i+1).minDistance);
		    distance_DF_total=distance_DF_total+destinations_DF.get(i+1).minDistance;
	    }
	    
	    System.out.println("Total Distance: "+distance_DF_total);
        //try to process the path String to generate the combined path(s). in vain
        /*List<List<Vertex>> combinePath = new ArrayList<List<Vertex>>();
        //int i=0;
	    for(Vertex v: destinations){
	    	//System.out.println("Distance to " + v + ": " + v.minDistance);
	    	if(!combinePath.toString().contains(getShortestPathTo(v).toString())){
	    		combinePath.add(getShortestPathTo(v));
	    	}
	    }
	    System.out.println("Path: " + combinePath.toString());*/
	    
	    /*computePath(destinations.get(0),destinations.get(1));
	    List<Vertex> path1 = getShortestPathFromTo(destinations.get(0),destinations.get(1));
	    System.out.println("Path: " + path1);
	    System.out.println(destinations.get(1).minDistance);*/
        
        //print all paths
	    /*for (Vertex v : vertices)
	    {
		    System.out.println("Distance to " + v + ": " + v.minDistance);
		    List<Vertex> path = getShortestPathTo(v);
		    System.out.println("Path: " + path);
		}*/
        
        //System.out.println("Distance to "+ itemNames+ " takes " + distination.minDistance);
	    /*for(int i=0; i<destinations.size(); i++){
		    List<Vertex> path = getShortestPathTo(destinations.get(i)); 
		    System.out.println("Path: " + path);
	    }*/
        //List<Vertex> path = getShortestPathTo(distination);
	    //System.out.println("Path: " + path);
    }
}

