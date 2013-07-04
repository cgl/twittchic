package net.twittchic.graph;

import net.twittchic.Tweet;
import net.twittchic.constants.Constants;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import static net.twittchic.Baseline.deserializeTweets;


/**
 * Created with IntelliJ IDEA.
 * User: cagil
 * Date: 7/3/13
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class CoGraph {

    public static void main(String [] args)
    {
        List<Tweet> tweets = deserializeTweets(Constants.tweetsFile);
        populateConfusionSet(tweets);
    }
    public static void populateConfusionSet(List<Tweet> tweets){
        SimpleWeightedGraph<String, DefaultWeightedEdge> graph = deserialize(Constants.graphFile);
        HashMap<String,Double> all;
        //Set<DefaultWeightedEdge> intersection;
        double edgeWeight;
        for (Tweet tweet : tweets) {
            TreeMap<Integer, String> ivs = tweet.getIvs();
            all = new HashMap<String,Double> ();
            for (Integer ind : ivs.keySet()) {
                String iv = ivs.get(ind).toLowerCase(Constants.locale);
                if(graph.containsVertex(iv)){
                Set<DefaultWeightedEdge> edges =  graph.edgesOf(iv);
                for (DefaultWeightedEdge edge : edges) {
                    String edgeTarget = graph.getEdgeTarget(edge);
                    edgeWeight = graph.getEdgeWeight(edge);
                    if(all.containsKey(edgeTarget)){
                        all.put(edgeTarget,all.get(edgeTarget)+edgeWeight);
                    }
                    else
                        all.put(edgeTarget,edgeWeight);
                }

                System.out.println(iv+" : ");
                }
                else
                    System.out.println(iv+" No such Vertex");
            }
            System.out.println(all.toString());


        }
    }

    public static void readGraphFromFile(){
        SimpleWeightedGraph<String, DefaultWeightedEdge> stringGraph = deserialize(Constants.graphFile);
    }
    public static void create(){
        List<Tweet> tweets = deserializeTweets(Constants.allTweetsFile);
        SimpleWeightedGraph<String, DefaultWeightedEdge> stringGraph = createStringGraph(tweets);
        serialize(stringGraph);
    }

    protected static SimpleWeightedGraph<String, DefaultWeightedEdge> createStringGraph(List<Tweet> tweets)
    {
        SimpleWeightedGraph <String, DefaultWeightedEdge> g =
                new SimpleWeightedGraph <String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        String [] ivsWords;
        for (Tweet tweet : tweets) {
            final TreeMap<Integer,String> ivs = tweet.getIvs();
            if(ivs.size()>0){
                ivsWords = new String[ivs.size()];
                ivs.values().toArray(ivsWords);
                addVertex(g,ivsWords[0]);
                for(int i = 0 ; i < ivsWords.length-2; i++) {
                    for(int j = i + 1 ; j < ivsWords.length-1; j++){
                        if(i==0)
                            addVertex(g,ivsWords[j]);
                        addEdge(g,ivsWords[i], ivsWords[j]);
                    }
                }
            }
        }
        return g;
    }
    public static void addEdge(SimpleWeightedGraph<String, DefaultWeightedEdge> g, String v1, String v2){
        v1 = v1.toLowerCase(Constants.locale);
        v2 = v2.toLowerCase(Constants.locale);
        if(v1.equals(v2))
            return;
        DefaultWeightedEdge edge;
        if(g.containsEdge(v1,v2)){
            edge = g.getEdge(v1, v2);
        }
        else
            edge = g.addEdge(v1, v2);
        g.setEdgeWeight(edge,g.getEdgeWeight(edge)+1);
    }

    public static SimpleWeightedGraph <String, DefaultWeightedEdge> addVertex(SimpleWeightedGraph <String, DefaultWeightedEdge> g,String e){
        e = e.toLowerCase(Constants.locale);
        if(!g.containsVertex(e)){
            g.addVertex(e);
        }
        return g;
    }

    public static void serialize(SimpleWeightedGraph<String, DefaultWeightedEdge> g){
        if(g != null){
            try
            {
            OutputStream file = new FileOutputStream( Constants.graphFile );
            OutputStream buffer = new BufferedOutputStream( file );
            ObjectOutput output = new ObjectOutputStream( buffer );

            output.writeObject(g);
            output.close();
            }catch (IOException ex)
            {
                System.out.println("Serialize edilirken hata gerçekleşti 2 !!! : "+ex.getMessage());
            }
        }
    }

    public static SimpleWeightedGraph<String, DefaultWeightedEdge> deserialize(String filename) {
        try {
            InputStream file = new FileInputStream(filename);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            SimpleWeightedGraph<String, DefaultWeightedEdge> g = (SimpleWeightedGraph<String, DefaultWeightedEdge>) input.readObject();
            input.close();
            return g;
        }
        catch (ClassNotFoundException ex) {
            System.out.println("Deserialize edilirken hata gerçekleşti 1 !!! : " + ex.getMessage());
        }
        catch (IOException ex) {
            System.out.println("Deserialize edilirken hata gerçekleşti 2 !!! : " + ex.getMessage());
        }
        return null;
    }
}
