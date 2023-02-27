package avengers;
import java.util.*;
public class Graph {

private ArrayList<Integer>[] m_graph;
private boolean m_IsDirected;
private int m_numVertices;
private boolean m_IsAdjMatrix;
/*
 *1. To create a weighted graph pass true for IsAdjMatrix
  2. To represent a graph using an adjacency matrix pass IsAdjMAtrix equals to true if graph is weighted or not
  3. To represent graph in AdjList pass false for ISAdjMAtrix
 */
public Graph(int numVertices, boolean IsAdjMatrix, boolean IsDirected)
{
   m_IsAdjMatrix = IsAdjMatrix; 
   m_numVertices =numVertices;
   m_IsDirected = IsDirected;
   m_graph = new ArrayList[numVertices];
   for(int i =0; i<numVertices; i++)
   {
     if(IsAdjMatrix) //AdjMatrix
     {
        m_graph[i] = new ArrayList<Integer>(numVertices);
        for(int j=0; j<numVertices; j++)
        m_graph[i].add(0);
     }
     else // AdjLIst
     {
        m_graph[i] = new ArrayList<Integer>();
     }
   } 
 }
 //Use this method for weighted graph
public void AddEdge (int SrcVertex, int TargetVertex, int Weight)
{  
    if(m_IsAdjMatrix)
        m_graph[SrcVertex].add(TargetVertex,Weight);
    else
    {
      System.out.println("Error: Use other AddEdge method which add to AdjList");
    }           
}
// Use this method for unweighted method
public void AddEdge(int SrcVertex, int TargetVertex)
{
  if(m_IsAdjMatrix)
  {
    m_graph[SrcVertex].set(TargetVertex, 1);   
  }
  else
    m_graph[SrcVertex].add(TargetVertex);
}
// Find the vertex with OutDegree = 0 or No OutGoing Edge
 public int findMindStone() 
 {
    int target = -1;
   for(int i =0; i<m_graph.length;i++)
   {
     if(m_graph[i].size()==0)
     {
        target =i;
        break;
     }  
   }
   return target;
 }
 public ArrayList<Integer> findDirectMindStConnects()
 {
    int Target = findMindStone();
    ArrayList<Integer> directConn = new ArrayList<>();
    for(int i=0; i<m_graph.length; i++) 
    {
       for(int j=0; j<m_graph[i].size(); j++)
       {
          if(m_graph[i].get(j)==Target)
          {
              directConn.add(i);
          }
       }
    }
    return directConn;
 }
 public void deleteEdge(int src, int target)
 {
     m_graph[src].set(target, 0);
 }
 public void deleteVertice(int vertice)
 {
    for(int i=0; i<m_graph[vertice].size();i++)
    {     
       if(m_graph[vertice].get(i)!=0)
       {
        deleteEdge(vertice, i);
        deleteEdge(i, vertice);
       }
    }
    if(!m_IsAdjMatrix)
    {
      m_graph[vertice].clear();
    }
 }
 public boolean isConnected(boolean[]delVertex)
 {
  Queue<Integer> q = new LinkedList<Integer>();
  boolean[]visited = new boolean[m_numVertices];
  boolean startV = false;
  for(int i=0; i<delVertex.length; i++)
  {
    if(delVertex[i]==true)
      visited[i]=true;
    else if(!startV)
    {
      startV=true;
      q.add(i);
      visited[i]=true;
    }   
  }
  while(q.peek()!=null)
  {
    int v = q.remove();
    ArrayList<Integer> Edges = m_graph[v];
    for(int e=0;e<Edges.size();e++)
    {
      if(visited[e]==false && Edges.get(e)==1)
      { 
        q.add(e);
        visited[e]=true;
      }  
    }
  }
   for(int i=0;i<visited.length;i++)
   {
     if(visited[i]==false)
     return false;
   }
   return true;
 }
 public void FindNummPaths(int v, ArrayList<Integer>PathBuilder , ArrayList<ArrayList<Integer>>Path)
 {     
    PathBuilder.add(v);
    Path.add((ArrayList<Integer>)PathBuilder.clone());    
    for(int i=0; i<m_graph[v].size();i++)
    {  
       if(m_graph[v].get(i)==1)
       {      
        FindNummPaths(i,PathBuilder,Path);
        if(PathBuilder.size()>0)
        {
          PathBuilder.remove(PathBuilder.size()-1);
        }        
       }
    }
 }

private int CalculateMinCostIdx(int[]minCost, boolean[] ds)
{
  int min=Integer.MAX_VALUE;
  int minVertex = -1;
  for(int i=0; i<minCost.length;i++)
  {
    if(minCost[i]<=min && ds[i] == false)  
    {
     min=minCost[i];
     minVertex=i; 
    }
  }
  return minVertex;
}
public int DijkstraAlgo()
{    
   int[]mincost=new int[m_numVertices];
   boolean[]DijkastraSet = new boolean[m_numVertices];
   mincost[0]=0;
   for(int i=1; i<mincost.length;i++)
   {
       mincost[i] = Integer.MAX_VALUE; 
   }
   for(int i=0; i<m_numVertices; i++)
   {
    int currentSource= CalculateMinCostIdx(mincost, DijkastraSet);
    DijkastraSet[currentSource]=true;
    ArrayList <Integer> Neighbor = m_graph[currentSource]; 
   for(int j=0; j<Neighbor.size(); j++) 
   {
    if(Neighbor.get(j)==0 || DijkastraSet[j]==true)
     continue;
     if(mincost[currentSource]!=Integer.MAX_VALUE && 
       (mincost[currentSource]+Neighbor.get(j)<mincost[j]))
     {
         mincost[j]= mincost[currentSource] + Neighbor.get(j);
     }
    }
   }  
   return mincost[m_numVertices-1];
}
  private String toString(ArrayList<Integer> aRow)
  {
    String s = "";
    for(int i=0; i<aRow.size(); i++)
    {
       s += aRow.get(i).toString() + " ";
    }
     s=s+"\n";
     return s;
  }
  public String toString(){
    String s = "";
    for(int i =0; i<m_numVertices; i++)
    {
        s=s+toString(m_graph[i]); 
    }
    return s;
  }
}