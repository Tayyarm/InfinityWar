package avengers;
import java.util.*;
public class MapStrToInt {
    HashMap<String,Integer> StrToInt;
    HashMap<Integer,String> IntToStr;

    public MapStrToInt ()
    {
       StrToInt= new HashMap<>();
       IntToStr = new HashMap<>();
    }
    public void Add(String s, int i)
    {
         StrToInt.put(s, i); 
         IntToStr.put(i,s);
    }
    public String get(int n)
    {
        return IntToStr.get(n);
    }
    public int get(String s)
    {
        return StrToInt.get(s).intValue();
    }
}
